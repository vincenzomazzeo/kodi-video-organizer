package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.BorderLayout;
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

	public static ProgressDialog getInstance(String title, boolean showTextSouth) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new ProgressDialog();
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
			self.setShowCloseButton(false);
			self.setShowTitleComponent(false);
		}

		self.set(title, showTextSouth);

		return self;
	}

	private WebLabel title;
	private WebLabel textNorth;
	private WebLabel textSouth;
	private WebProgressBar progressBar;

	private ProgressDialog() {
		super();

		setModal(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		init();
	}
	
	public WebProgressBar getProgressBar() {
		return this.progressBar;
	}
	
	public void setTextNorth(String text) {
		this.textNorth.setText(text);
	}
	
	public void setTextSouth(String text) {
		this.textSouth.setText(text);
	}
	
	public void setMaximum(int maximum) {
		this.progressBar.setMaximum(maximum);
	}
	
	public void setProgress(int progress) {
		this.progressBar.setValue(progress);
	}

	private void set(String title, boolean showTextSouth) {
		this.title.setText(title);
		this.textNorth.setText(" ");
		this.textSouth.setText(" ");
		
		this.textSouth.setVisible(showTextSouth);

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

		WebPanel textPane = UIUtils.makeStandardPane(new BorderLayout(0, 4));
		container.add(textPane, BorderLayout.CENTER);
		
		this.textNorth = UIUtils.makeStandardLabel("", 16, null, SwingConstants.CENTER);
		textPane.add(this.textNorth, BorderLayout.NORTH);
		this.textSouth = UIUtils.makeStandardLabel("", 16, null, SwingConstants.CENTER);
		textPane.add(this.textSouth, BorderLayout.SOUTH);

		this.progressBar = new WebProgressBar(WebProgressBar.HORIZONTAL, 0, 100);
		this.progressBar.setPreferredWidth(500);
		this.progressBar.setStringPainted(false);
		container.add(this.progressBar, BorderLayout.SOUTH);
	}

}
