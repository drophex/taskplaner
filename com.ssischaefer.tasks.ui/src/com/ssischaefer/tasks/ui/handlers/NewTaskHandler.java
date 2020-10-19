package com.ssischaefer.tasks.ui.handlers;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;

import com.ssischaefer.tasks.ui.service.Task;
import com.ssischaefer.tasks.ui.service.TaskHelper;

public class NewTaskHandler {
	private TaskHelper model = TaskHelper.getInstance();

	@Execute
	public void execute() {
		List<Task> tasks = model.getTasks();
		Task newTask = new Task(tasks.size() + 1, "New Task", "", false);
		tasks.add(newTask);
		model.setTasks(tasks);
		model.getCurrentTableViewer().setInput(model.getTasks());
		model.getCurrentTableViewer().refresh();
	}
}
