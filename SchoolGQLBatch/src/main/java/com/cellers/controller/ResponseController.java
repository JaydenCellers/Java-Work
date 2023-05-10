package com.cellers.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cellers.model.WrapRequest;
import com.cellers.response.*;

@RestController
public class ResponseController {

	@Autowired
	private HttpGraphQlClient graphQLWebClient;


	@SuppressWarnings("unchecked")
	@PostMapping(path = "json", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void acceptJson(@RequestBody WrapRequest request) throws Exception {

		QueryResponse[] responseHolder = new QueryResponse[1];

		String type = request.getType();
		String queryName = request.getQueryName();
		Map<String, String> payload = (Map<String, String>) request.getPayload();
		ArrayList<Object> responseAttributes = request.getResponseAttributes();

		/*System.out.println(type);
		System.out.println(queryName);
		for (Map.Entry<String, String> entry : payload.entrySet()) {
			System.out.println(entry.getKey() + "  " + entry.getValue());
		}
		System.out.println(responseAttributes);*/

		String query = type + " { " + queryName;

		String mappedPayload = "";

		if (!payload.isEmpty()) {
			for (Map.Entry<String, String> entry : payload.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (Character.isDigit(val.charAt(0)) && Character.isDigit(val.charAt(val.length()-1))) {
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

		
		//System.out.println(query);

		/*GraphQLRequest gRequest = GraphQLRequest.builder().query(query).build();

		GraphQLResponse graphQLResponse = graphQLWebClient.post(gRequest).block();

		if (queryName.contains("Student")) {
			if (queryName.contains("delete")) {
				responseHolder[0] = graphQLResponse.get(queryName, DeleteStudentResponse.class);
				return responseHolder;
			}
			if (queryName.contains("ById") || type.equals("mutation")) {
				responseHolder[0] = graphQLResponse.get(queryName, StudentResponse.class);
				return responseHolder;
			}
			return graphQLResponse.get(queryName, StudentResponse[].class);
		} else if (queryName.contains("Teacher")) {
			if (queryName.contains("delete")) {
				responseHolder[0] = graphQLResponse.get(queryName, DeleteTeacherResponse.class);
				return responseHolder;
			}
			if (queryName.contains("ById") || type.equals("mutation")) {
				responseHolder[0] = graphQLResponse.get(queryName, TeacherResponse.class);
				return responseHolder;
			}
			return graphQLResponse.get(queryName, TeacherResponse[].class);
		} else {
			if (queryName.contains("delete")) {
				responseHolder[0] = graphQLResponse.get(queryName, DeleteSchoolResponse.class);
				return responseHolder;
			}
			if (queryName.contains("ById") || type.equals("mutation")) {
				responseHolder[0] = graphQLResponse.get(queryName, SchoolResponse.class);
				return responseHolder;
			}
			return graphQLResponse.get(queryName, SchoolResponse[].class);
		}*/
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
