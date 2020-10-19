package com.ssischaefer.tasks.ui.parts;

import static org.eclipse.jface.layout.GridDataFactory.fillDefaults;
import static org.eclipse.jface.widgets.WidgetFactory.button;
import static org.eclipse.jface.widgets.WidgetFactory.text;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ssischaefer.tasks.ui.service.TaskHelper;



public class TaskEditorPart {
    private Text label;
    private Text detailedText;
    private Button button;
    private TaskHelper model = TaskHelper.getInstance();

    @PostConstruct
    public void createControls(Shell shell, Composite parent) {
    	model.setTaskEditorPartInstance(this);
        parent.setLayout(new GridLayout(2, false));

        label = text(SWT.BORDER).message("").layoutData(fillDefaults().grab(true, false).create())
                .create(parent);
        label.setEnabled(false);
        button = button(SWT.PUSH).text("Apply").onSelect(e -> updateTask(shell)).create(parent);
        button.setEnabled(false);

        detailedText = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        detailedText.setLayoutData(fillDefaults().grab(true, true).span(2, 1).create());
        detailedText.setEnabled(false);

    }

    private void updateTask(Shell shell) {
		label.setEnabled(false);
		button.setEnabled(false);
		detailedText.setEnabled(false);
		
		model.getTasks().get(model.getCurrentSelectedRow()-1).setLabel(label.getText());
		model.getTasks().get(model.getCurrentSelectedRow()-1).setText(detailedText.getText());
		model.getCurrentTableViewer().setInput(model.getTasks());
		model.getCurrentTableViewer().refresh();
		model.getDetailsPartInstance().disableEditButton();
		model.getDetailsPartInstance().disableToggleTasksButton();
		label.setText("");
		detailedText.setText("");
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WORKING);
		messageBox.setMessage("Task has been updated");
		messageBox.open();
		if (model.getCurrentSelectedRow() != 0 && model.getCurrentSelectedRow() <= model.getTasks().size()) {
		model.getDetailsPartInstance().updateLabel(model.getTasks().get(model.getCurrentSelectedRow() -1).getLabel());
		model.getDetailsPartInstance().updateDetailsText(model.getTasks().get(model.getCurrentSelectedRow()-1).getText());
		}
	}

	@Focus
    public void onFocus() {
        label.setFocus();
    }

	public void loadTask() {
		label.setText(model.getTasks().get(model.getCurrentSelectedRow() -1).getLabel());
		label.setEnabled(true);
		detailedText.setText(model.getTasks().get(model.getCurrentSelectedRow()-1).getText());
		button.setEnabled(true);
		detailedText.setEnabled(true);
	}
}
