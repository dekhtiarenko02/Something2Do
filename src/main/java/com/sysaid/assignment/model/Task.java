package com.sysaid.assignment.model;

import lombok.Data;

@Data
public class Task{
	private String id;
	private String name;
	private String type;
	private boolean completed;
	private boolean wishList;
	private int rating;

	public Task(){
		this.completed = false;
		this.wishList = false;
		this.rating = 0;
	}
}

