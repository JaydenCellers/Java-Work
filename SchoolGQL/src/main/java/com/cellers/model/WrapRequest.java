package com.cellers.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WrapRequest {
	
	private String type;
	
	private String queryName;
	
	private Object payload;
	
	private ArrayList<Object> responseAttributes;
}
