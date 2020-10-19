package com.ssischaefer.tasks.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.ssischaefer.tasks.ui.service.WriterReader;

public class SaveAllHandler {
	@Execute
	public void execute(Shell shell) {
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFileName("tasks.obj");
		dialog.setText("Please specify where you want to save your current tasks");
		dialog.open();
		String path = dialog.getFilterPath();
		WriterReader.saveTasksToDisk(shell, path + "\\" + dialog.getFileName());
	}
}
