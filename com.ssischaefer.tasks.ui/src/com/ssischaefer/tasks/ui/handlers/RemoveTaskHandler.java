package com.ssischaefer.tasks.ui.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;

import com.ssischaefer.tasks.ui.service.Task;
import com.ssischaefer.tasks.ui.service.TaskHelper;

public class RemoveTaskHandler {
	@Execute
    public void execute() {
      System.out.println((this.getClass().getSimpleName() + " called"));
      TaskHelper model = TaskHelper.getInstance();
      model.removeTask(model.getCurrentSelectedRow());
      
      
      List<Task> editedTaskList = new ArrayList<>();
      
      for (Task task : TaskHelper.getInstance().getTasks()) {
    	  task.setRow(editedTaskList.size() + 1);
    	  editedTaskList.add(task);
      }
      model.setTasks(editedTaskList);
      model.getCurrentTableViewer().setInput(model.getTasks());
      model.getCurrentTableViewer().refresh();
      model.getDetailsPartInstance().updateLabel();
      model.getDetailsPartInstance().updateDetailsText();
      model.getDetailsPartInstance().disableEditButton();
      model.getDetailsPartInstance().disableToggleTasksButton();
    }
}
