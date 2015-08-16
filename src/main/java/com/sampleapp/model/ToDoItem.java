/****************************************************************
 * Copyright (c) 2015 Health Innovation Technologies, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/

package com.sampleapp.model;

import java.util.Date;

public class ToDoItem {

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date date) {
		dueDate = date;
	}

	public ToDoItem() {
	}

	public ToDoItem(long id, String description, Date dueDate) {
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
	}

	private long id;
	private String description;
	private Date dueDate;
}
