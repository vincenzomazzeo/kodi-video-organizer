package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.UI;

import javax.swing.JComponent;

import com.alee.laf.rootpane.WebDialog;
//TODO UIUtils
// TODO modificare con nuovi dialog
public class FullImageDialog extends WebDialog {

	private static final long serialVersionUID = 8261555653260314181L;

	public FullImageDialog(JComponent content, String title) {
		super(UI.get(), title, true);

		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		add(content);
		pack();
		setLocationRelativeTo(UI.get());
	}

}
