package com.cellers.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.cellers.model.Output;
import com.cellers.model.WrapRequest;
import com.cellers.response.*;

import reactor.core.publisher.Mono;

@RestController
public class ResponseController {

	WebClient webClient = WebClient.builder().baseUrl("localhost:8080/graphql").build();
	HttpGraphQlClient graphQlClient = HttpGraphQlClient.create(webClient);

	@SuppressWarnings("unchecked")
	@PostMapping(path = "json", consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Output> acceptJson(@RequestBody WrapRequest request) throws Exception {

		Output out;
		List<Output> outL;

		String type = request.getType();
		String queryName = request.getQueryName();
		Map<String, String> payload = (Map<String, String>) request.getPayload();
		ArrayList<Object> responseAttributes = request.getResponseAttributes();

		String query = type + " { " + queryName;

		String mappedPayload = "";

		if (!payload.isEmpty()) {
			for (Map.Entry<String, String> entry : payload.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (Character.isDigit(val.charAt(0)) && Character.isDigit(val.charAt(val.length() - 1))) {
					mappedPayload += key + ": " + val + ",";
				} else {
					mappedPayload += key + ": \"" + val + "\",";
				}

			}
			mappedPayload = mappedPayload.substring(0, mappedPayload.lastIndexOf(','));
		}

		if (payload.isEmpty() && !responseAttributes.isEmpty()) {
			query += "{";
		} else if (!responseAttributes.isEmpty()) {
			query += "(" + mappedPayload + ") {";
		} else {
			query += "(" + mappedPayload + ")";
		}

		query += unpack(responseAttributes);

		if (responseAttributes.isEmpty()) {
			query += "}";
		} else {
			query += "} }";
		}

		System.out.println("This is the query: \n" + query);
		if (queryName.contains("ById") || queryName.contains("add") || queryName.contains("update")) {
			Mono<Output> mono = graphQlClient.document(query).retrieve(queryName).toEntity(Output.class);
			out = mono.block();
			return Arrays.asList(out);
		} else {
			Mono<List<Output>> mono = graphQlClient.document(query).retrieve(queryName).toEntityList(Output.class);
			outL = mono.block();
			return outL;
		}

	}

	@SuppressWarnings("unchecked")
	public String unpack(ArrayList<Object> arr) {
		StringBuilder sb = new StringBuilder();

		for (Object obj : arr) {
			if (obj instanceof ArrayList) {
				sb.append("{ ");
				sb.append(unpack((ArrayList<Object>) obj));
				sb.append(" }");
			} else {
				sb.append(obj.toString() + " ");
			}
		}

		return sb.toString();
	}
}
