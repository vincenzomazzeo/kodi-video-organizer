package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.util.MemoryUtils;

import javax.swing.JComponent;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;

public class FullImageDialog extends WebDialog {

	private static final long serialVersionUID = 8261555653260314181L;
	private static FullImageDialog self;
	
	public static FullImageDialog getInstance(JComponent content, String title) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new FullImageDialog();
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
		}
		
		self.set(content, title);

		return self;
	}

	private FullImageDialog() {
		super(UI.get(), true);

		setDefaultCloseOperation(WebDialog.HIDE_ON_CLOSE);
		setResizable(false);
	}
	
	public void release() {
		System.out.println("*** FullImageDialog -> release ***");
		MemoryUtils.printMemory("Before FullImageDialog release");
		setContentPane(new WebPanel());
		MemoryUtils.printMemory("After FullImageDialog release");
	}
	
	private void set(JComponent content, String title) {
		setTitle(title);
		add(content);
		pack();
		setLocationRelativeTo(UI.get());
	}

}
