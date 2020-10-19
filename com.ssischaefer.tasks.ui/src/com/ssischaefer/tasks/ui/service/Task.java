package com.ssischaefer.tasks.ui.service;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Task implements Serializable {
	private static final long serialVersionUID = 8042695738510613472L;
	private int row;
	private String label;
	private String text;
	private boolean done;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public Task() {
	}

	public Task(int row, String label, String text, boolean done) {
		this.row = row;
		this.label = label;
		this.text = text;
		this.done = done;
	}

	public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
	
	public int getRow() {
	return this.row;
	}
	
	public String getLabel() {
	return this.label;
	}
	
	public String getText() {
	return this.text;
	}
	
	public void setRow(int row) {
	this.row = row;
	}
	
	public void setLabel(String label) {
	this.label = label;
	}
	
	public void setText(String text) {
	this.text = text;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isDone() {
		return this.done;
	}
	@Override
	public String toString() {
		return this.label;
	}
	//TODO: implement proper equalTo method
}
