package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebDialog;

public class ProgressDialog extends WebDialog {

	private static final long serialVersionUID = 4539429148679067497L;
	private static ProgressDialog self;

	public static ProgressDialog getInstance(String title) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new ProgressDialog();
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
			self.setShowCloseButton(false);
			self.setShowTitleComponent(false);
		}

		self.set(title);

		return self;
	}

	private WebLabel title;
	private WebLabel text;
	private WebProgressBar progressBar;

	private ProgressDialog() {
		super();

		setModal(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		init();
		
		setPreferredSize(new Dimension(500, 150));
	}
	
	public WebProgressBar getProgressBar() {
		return this.progressBar;
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public void setMaximum(int maximum) {
		this.progressBar.setMaximum(maximum);
	}
	
	public void setProgress(int progress) {
		this.progressBar.setValue(progress);
	}

	private void set(String title) {
		this.title.setText(title);

		pack();
		setLocationRelativeTo(UI.get());
	}

	private void init() {
		WebPanel container = UIUtils.makeStandardPane(new BorderLayout(5, 5));
		setContentPane(container);
		container.setOpaque(true);
		container.setBackground(Colors.BACKGROUND_INFO);
		container.setMargin(new Insets(10, 10, 10, 10));

		this.title = UIUtils.makeStandardLabel("", 20, null, SwingConstants.CENTER);
		container.add(this.title, BorderLayout.NORTH);

		this.text = UIUtils.makeStandardLabel("", 16, null, SwingConstants.CENTER);
		container.add(this.text, BorderLayout.CENTER);

		this.progressBar = new WebProgressBar(WebProgressBar.HORIZONTAL, 0, 100);
		this.progressBar.setStringPainted(false);
		container.add(this.progressBar, BorderLayout.SOUTH);
	}

}
