package com.ssischaefer.tasks.ui.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class WriterReader {

	public static void saveTasksToDisk (Shell shell, String path) {
	
		try {
			FileOutputStream f = new FileOutputStream(path);
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(TaskHelper.getInstance().getTasks());

			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("Can not write to the file. Please check if you have write access to the file location.");
			messageBox.open();
			//logger.error(String.format("FileNotFoundException thrown. Used path: %s", path));
		} catch (IOException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("Can not write to the file. Please check if you have write access to the file location.");
			messageBox.open();
			//logger.error(String.format("IOException thrown. Used path: %s", path));
		} 
	}
	
	@SuppressWarnings("unchecked")
	public static List<Task> readTasksToDisk (Shell shell, String path) {
		List<Task> tasks = Collections.emptyList();
		try {
			FileInputStream fi = new FileInputStream(new File(path));
			ObjectInputStream oi = new ObjectInputStream(fi);
			Object taskObjects = oi.readObject();
			tasks = (List<Task>) taskObjects;

			oi.close();
			fi.close();
			
			return tasks;
		} catch (FileNotFoundException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("Can not find the file specified");
			messageBox.open();
			//logger.error(String.format("FileNotFoundException thrown. Used path: %s", path));
		} catch (IOException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("File format seems not to be correct. Please make sure you open the correct file.");
			messageBox.open();
			//logger.error(String.format("IOException thrown. Used path: %s", path));
		} catch (ClassNotFoundException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("File format seems not to be correct. Please make sure you open the correct file.");
			messageBox.open();
			//logger.error(String.format("ClassNotFoundException thrown. Used path: %s", path));
		} catch (ClassCastException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("File format seems not to be correct. Please make sure you open the correct file.");
			messageBox.open();
			//logger.error(String.format("ClassCastException thrown. Object from file can not be cast to target tasks list. Used path: %s", path));
		}
		//logger.info("Loading tasks from object file finished.");
		return tasks;
	}

}