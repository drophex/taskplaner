package com.ssischaefer.tasks.ui.handlers;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.ssischaefer.tasks.ui.service.Task;
import com.ssischaefer.tasks.ui.service.TaskHelper;
import com.ssischaefer.tasks.ui.service.WriterReader;

public class OpenHandler {
	@Execute
    public void execute(Shell shell) {
		FileDialog dialog = new FileDialog(shell);
		dialog.setText("Please select your tasks savefile");
		dialog.open();
		String path = dialog.getFilterPath();
		String filename = dialog.getFileName();
		TaskHelper model = TaskHelper.getInstance();
		List<Task> loadedTasks = WriterReader.readTasksToDisk(shell, path + "\\" + filename);
		if (!loadedTasks.isEmpty()) {
		model.getCurrentTableViewer().setInput(loadedTasks);
		model.getCurrentTableViewer().refresh();
		}
	}
}
