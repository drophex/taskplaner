package com.ssischaefer.tasks.ui.parts;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.ssischaefer.tasks.ui.service.Task;
import com.ssischaefer.tasks.ui.service.TaskHelper;

import org.eclipse.swt.layout.GridData;

public class OverviewPart {
	private TableViewer tableViewer;
	private List<Task> model = TaskHelper.createInitialDataModel();
	private Task selectedTask;

	@PostConstruct
	public void createControls(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		createSearchBar(parent);

		createTableViewer(parent);
	}

	private void createSearchBar(Composite parent) {
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Search: ");

		Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		searchText.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				refreshTableViewer(searchText.getText());

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// Stub
			}
		});

	}

	private void createTableViewer(Composite parent) {
		tableViewer = new TableViewer(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, tableViewer);
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(model);

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		tableViewer.getControl().setLayoutData(gridData);
		TaskHelper.getInstance().setCurrentTableViewer(tableViewer);

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				TaskHelper model = TaskHelper.getInstance();
				IStructuredSelection selection = model.getCurrentTableViewer().getStructuredSelection();
				selectedTask = (selection.getFirstElement()) == null ? new Task() : (Task) selection.getFirstElement();
				model.setCurrentSelectedRow(selectedTask.getRow());
				model.getDetailsPartInstance().loadTaskDetails();
			}
		});

	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Task label", "Details", "Done" };
		int[] bounds = { 150, 280, 40 };

		// First column is for the label
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return task.getLabel();
			}
		});

		// Second column is for the detailed text
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return task.getText();
			}
		});

		// For done/undone checkmarks
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return Boolean.toString(task.isDone());
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;

	}

	private void refreshTableViewer(String text) {
		tableViewer.setInput(TaskHelper.getInstance().getTasks().stream()
				.filter(task -> task.getLabel().toLowerCase().contains(text.toLowerCase()))
				.collect(Collectors.toList()));
		tableViewer.refresh();

	}
}
