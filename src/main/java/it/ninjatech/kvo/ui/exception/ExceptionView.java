package it.ninjatech.kvo.ui.exception;

import it.ninjatech.kvo.ui.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.CollapsiblePaneListener;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;

public class ExceptionView extends WebDialog implements CollapsiblePaneListener {

	private static final long serialVersionUID = 4772626517954754101L;

	private final Map<String, WebCollapsiblePane> panes;
	private ExceptionController controller;
	private WebPanel container;
	private boolean adding;
	
	public ExceptionView() {
		super(UI.get(), "Exceptions", true);

		this.panes = new HashMap<>();
		this.adding = false;
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		init();
		setPreferredSize(new Dimension(600, 500));
		setResizable(false);
		pack();
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void expanding(WebCollapsiblePane pane) {
	}

	@Override
	public void expanded(WebCollapsiblePane pane) {
		if (!this.adding) {
			this.controller.notifyRead(pane.getTitle());
		}
	}

	@Override
	public void collapsing(WebCollapsiblePane pane) {
	}

	@Override
	public void collapsed(WebCollapsiblePane pane) {
		if (!this.adding) {
			this.controller.notifyRead(pane.getTitle());
		}
	}

	protected void setController(ExceptionController controller) {
		this.controller = controller;
	}
	
	protected void addException(String id, Throwable exception) {
		this.adding = true;
		
		StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));
		
		WebTextArea textArea = new WebTextArea(writer.toString());
		textArea.setLineWrap(false);
		textArea.setEditable(false);

		WebScrollPane scrollPane = new WebScrollPane(textArea, false);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		scrollPane.getVerticalScrollBar().setBlockIncrement(30);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(30);
		scrollPane.getHorizontalScrollBar().setBlockIncrement(30);
		
		WebCollapsiblePane collapsiblePane = new WebCollapsiblePane(id, scrollPane);
		this.container.add(collapsiblePane, 0);
		collapsiblePane.setExpanded(false, false);
		collapsiblePane.addCollapsiblePaneListener(this);
		this.panes.put(id, collapsiblePane);
		
		this.adding = false;
	}

	protected void setToRead(Set<String> toRead) {
		for (String paneId : this.panes.keySet()) {
			this.panes.get(paneId).getTitleComponent().setForeground(toRead.contains(paneId) ? Color.RED : Color.BLACK);
		}
	}
	
	private void init() {
		this.container = new WebPanel(new VerticalFlowLayout());
		
		WebScrollPane scrollPane = new WebScrollPane(this.container, false);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		scrollPane.getVerticalScrollBar().setBlockIncrement(30);

		add(scrollPane);
	}

}