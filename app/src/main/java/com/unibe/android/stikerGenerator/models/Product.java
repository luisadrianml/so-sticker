package com.unibe.android.stikerGenerator.models;

import java.io.Serializable;

public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5912709934201685067L;
	
	private Long id;
	private String name;
	
	public Product(Long id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
