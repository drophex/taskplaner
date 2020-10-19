package com.ssischaefer.tasks.ui.service;

import java.io.Serializable;
import java.util.List;

public class TasksDto implements Serializable {
	private static final long serialVersionUID = 1971824600853098036L;
	private List<Task> tasks;
	
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
