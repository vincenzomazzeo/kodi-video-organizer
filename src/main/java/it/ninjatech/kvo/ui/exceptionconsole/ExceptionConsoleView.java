package it.ninjatech.kvo.ui.exceptionconsole;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.Labels;

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
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;

public class ExceptionConsoleView extends WebDialog implements CollapsiblePaneListener {

	private static final long serialVersionUID = 4772626517954754101L;
	private static ExceptionConsoleView self;

	public static ExceptionConsoleView getInstance(ExceptionConsoleController controller) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new ExceptionConsoleView(controller);
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
		}
		
		return self;
	}
	
	private final ExceptionConsoleController controller;
	private final Map<String, WebCollapsiblePane> panes;
	private WebPanel container;
	private boolean adding;
	
	private ExceptionConsoleView(ExceptionConsoleController controller) {
		super(UI.get(), Labels.EXCEPTION_CONSOLE, true);

		this.controller = controller;
		this.panes = new HashMap<>();
		this.adding = false;
		
		setIconImage(ImageRetriever.retrieveToolBarExceptionConsole().getImage());
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

	protected void addException(String id, Throwable exception) {
		this.adding = true;
		
		StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));
		
		WebTextArea textArea = new WebTextArea(writer.toString());
		textArea.setLineWrap(false);
		textArea.setEditable(false);
		textArea.setBackground(Colors.BACKGROUND_INFO);
		textArea.setForeground(Colors.FOREGROUND_STANDARD);

		WebScrollPane scrollPane = UIUtils.makeScrollPane(textArea, WebScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, WebScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		
		WebCollapsiblePane collapsiblePane = new WebCollapsiblePane(id, scrollPane);
		this.container.add(collapsiblePane, 0);
		collapsiblePane.setExpanded(false, false);
		collapsiblePane.addCollapsiblePaneListener(this);
		collapsiblePane.setBackground(Colors.BACKGROUND_INFO);
		this.panes.put(id, collapsiblePane);
		
		this.adding = false;
	}

	protected void setToRead(Set<String> toRead) {
		for (String paneId : this.panes.keySet()) {
			this.panes.get(paneId).getTitleComponent().setForeground(toRead.contains(paneId) ? Color.RED : Color.BLACK);
		}
	}
	
	private void init() {
		this.container = UIUtils.makeStandardPane(new VerticalFlowLayout());
		this.container.setOpaque(true);
		this.container.setBackground(Colors.BACKGROUND_INFO);
		
		add(UIUtils.makeScrollPane(this.container, WebScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, WebScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}

}
