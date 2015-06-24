package edu.itba.hci.dto;

import java.io.Serializable;

public class Attribute implements Serializable {

	private String id;
	private String name;
	private String[] values;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
}
