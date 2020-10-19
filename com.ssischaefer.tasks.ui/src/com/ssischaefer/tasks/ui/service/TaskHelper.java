package com.ssischaefer.tasks.ui.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jface.viewers.TableViewer;

import com.ssischaefer.tasks.ui.parts.DetailsPart;
import com.ssischaefer.tasks.ui.parts.TaskEditorPart;

public class TaskHelper {
	private static TaskHelper model_instance = null;
	private List<Task> tasks;
	private int currentSelectedRow;
	private TableViewer currentTableViewer;
	private DetailsPart detailsPartInstance;
	private TaskEditorPart taskEditorPartInstance;

	public TaskEditorPart getTaskEditorPartInstance() {
		return taskEditorPartInstance;
	}

	public void setTaskEditorPartInstance(TaskEditorPart taskEditorPartInstance) {
		this.taskEditorPartInstance = taskEditorPartInstance;
	}

	public DetailsPart getDetailsPartInstance() {
		return detailsPartInstance;
	}

	public void setDetailsPartInstance(DetailsPart detailsPartInstance) {
		this.detailsPartInstance = detailsPartInstance;
	}

	public TableViewer getCurrentTableViewer() {
		return currentTableViewer;
	}

	public void setCurrentTableViewer(TableViewer currentTableViewer) {
		this.currentTableViewer = currentTableViewer;
	}

	public List<Task> getTasks() {
		if (!tasks.isEmpty()) {
		return tasks;
		} else
			return Collections.emptyList();
	}

	private TaskHelper() {
		
	}

	public static TaskHelper getInstance() {
		if (model_instance == null)
			model_instance = new TaskHelper();

		return model_instance;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public int getCurrentSelectedRow() {
		return currentSelectedRow;
	}

	public void setCurrentSelectedRow(int currentSelectedRow) {
		this.currentSelectedRow = currentSelectedRow;
	}

	public void removeTask(int row) {
		tasks.remove(row -1 );
	}
	
	public static List<Task> createInitialDataModel() {
		TaskHelper model = TaskHelper.getInstance();
		List<Task> taskList = new ArrayList<>();
		taskList.add(new Task(1, "Dish Washer", "Fix the broken dish wascher", false));
		taskList.add(new Task(2, "SSI Schäfer tool", "Finish the tutorial tool for SSI Schäfer", false));
		taskList.add(new Task(3, "Prime Day!", "Check the current prime day offers", true));
		taskList.add(new Task(4, "Salt", "Add salt to the next shopping list", false));
		model.setTasks(taskList);
		return taskList;
	}

}
