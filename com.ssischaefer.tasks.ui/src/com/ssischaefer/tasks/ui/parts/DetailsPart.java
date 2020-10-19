package com.ssischaefer.tasks.ui.parts;

import static org.eclipse.jface.widgets.WidgetFactory.button;
import javax.annotation.PostConstruct;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ssischaefer.tasks.ui.service.Task;
import com.ssischaefer.tasks.ui.service.TaskHelper;

import org.eclipse.e4.core.di.annotations.Creatable;

@Creatable
public class DetailsPart {
	private static final String STANDARD_LABEL_TEXT = "Task";
	private static final String STANDARD_DETAILS_TEXT = "No task selected";
	private Group group;
	private Text detailsText;
	private Button editButton;
	private Button toggleTasksButton;
	private TaskHelper model = TaskHelper.getInstance();

	public DetailsPart() {
	}

	@PostConstruct
	public void createControls(Shell shell, Composite parent) {
		model.setDetailsPartInstance(this);
		parent.setLayout(new GridLayout(2, false));

		group = new Group(parent, SWT.NONE);
		updateLabel();
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		group.setLayoutData(gridData);
		group.setLayout(new FillLayout(SWT.VERTICAL));
		detailsText = new Text(group, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		detailsText.setEditable(false);
		updateDetailsText();
		toggleTasksButton = button(SWT.PUSH).text("Toggle done").onSelect(e -> toggleTaskDone()).create(parent);
		disableToggleTasksButton();
		editButton = button(SWT.PUSH).text("Edit task").onSelect(e -> editTaskDetails(shell)).create(parent);
		disableEditButton();
	}

	public void loadTaskDetails() {
		if (model.getCurrentSelectedRow() <= 0 || model.getCurrentSelectedRow() > model.getTasks().size()) {
			updateLabel();
			updateDetailsText();
		} else {
			String label = model.getTasks().get(model.getCurrentSelectedRow() - 1).getLabel();
			updateLabel(label);
			String detailedText = model.getTasks().get(model.getCurrentSelectedRow() - 1).getText();
			updateDetailsText(detailedText);
			enableEditButton();
			enableToggleTasksButton();
		}
		detailsText.redraw();
	}

	private void editTaskDetails(Shell shell) {
		model.getTaskEditorPartInstance().loadTask();
	}

	private void toggleTaskDone() {
		Task task = model.getTasks().get(model.getCurrentSelectedRow() - 1);
		task.setDone(!task.isDone());
		refreshTasksTableViewer();
	}

	private void refreshTasksTableViewer() {
		model.getCurrentTableViewer().setInput(model.getTasks());
		model.getCurrentTableViewer().refresh();
	}

	public void updateLabel() {
		group.setText(STANDARD_LABEL_TEXT);
	}

	public void updateDetailsText() {
		detailsText.setText(STANDARD_DETAILS_TEXT);
	}

	void updateLabel(String text) {
		group.setText(text);
	}

	void updateDetailsText(String text) {
		detailsText.setText(text);
	}

	void enableEditButton() {
		this.editButton.setEnabled(true);
	}

	public void disableEditButton() {
		this.editButton.setEnabled(false);
	}

	void enableToggleTasksButton() {
		this.toggleTasksButton.setEnabled(true);
	}

	public void disableToggleTasksButton() {
		this.toggleTasksButton.setEnabled(false);
	}

}