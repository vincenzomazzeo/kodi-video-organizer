package it.ninjatech.kvo.ui.settings;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.alee.extended.image.WebImage;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;

public class ScrapersSettingsApiKeyDialog extends WebDialog implements ActionListener {

	private static final long serialVersionUID = 2057021738923899478L;
	private static ScrapersSettingsApiKeyDialog self;
	
	public static ScrapersSettingsApiKeyDialog getInstance(String title, String message) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new ScrapersSettingsApiKeyDialog();
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
			self.setShowCloseButton(false);
		}
		
		self.set(title, message);

		return self;
	}
	
	private WebLabel title;
	private WebImage icon;
	private WebLabel message;
	private WebTextField apiKey;
	private WebButton ok;
	private WebButton cancel;

	private String value;
	
	private ScrapersSettingsApiKeyDialog() {
		super(UI.get(), true);

		init();
		
		setDefaultCloseOperation(WebDialog.HIDE_ON_CLOSE);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.cancel) {
			this.value = null;
		}
		else if (event.getSource() == this.ok) {
			this.value = this.apiKey.getText();
		}
		setVisible(false);
	}
	
	public String getValue() {
		return this.value;
	}
	
	private void set(String title, String message) {
		this.title.setText(title);
		this.message.setText(message);
		
		pack();
		setLocationRelativeTo(UI.get());
	}

	private void init() {
		WebPanel content = UIUtils.makeStandardPane(new BorderLayout());
		setContentPane(content);
		content.setMargin(new Insets(10, 10, 5, 10));
		content.setOpaque(true);
		content.setBackground(Colors.BACKGROUND_INFO);
		
		this.title = UIUtils.makeStandardLabel("", 20, new Insets(0, 0, 15, 0), SwingConstants.CENTER);
		content.add(this.title, BorderLayout.NORTH);
		
		WebPanel messagePane = UIUtils.makeStandardPane(new BorderLayout(10, 5));
		content.add(messagePane, BorderLayout.CENTER);
		messagePane.setMargin(new Insets(0, 10, 0, 10));
		
		ImageIcon icon = ImageRetriever.retrieveApikey();
		this.icon = new WebImage(icon);
		messagePane.add(this.icon, BorderLayout.WEST);
		messagePane.add(UIUtils.makeHorizontalFillerPane(icon.getIconWidth(), false), BorderLayout.EAST);
		
		WebPanel inputPane = UIUtils.makeStandardPane(new BorderLayout());
		messagePane.add(inputPane, BorderLayout.CENTER);
		this.message = UIUtils.makeStandardLabel("", 16, null, SwingConstants.LEFT);
		inputPane.add(this.message, BorderLayout.NORTH);
		this.message.setMinimumWidth(200);
		
		this.apiKey = new WebTextField();
		inputPane.add(this.apiKey, BorderLayout.CENTER);
		this.apiKey.setBackground(Colors.BACKGROUND_INFO);
		this.apiKey.setForeground(Colors.FOREGROUND_STANDARD);
		
		this.ok = UIUtils.makeButton(ImageRetriever.retrieveMessageDialogOk(), this);
		this.cancel = UIUtils.makeButton(ImageRetriever.retrieveMessageDialogCancel(), this);
		WebPanel buttonPane = UIUtils.makeFlowLayoutPane(FlowLayout.RIGHT, 5, 0, this.ok, this.cancel);
		buttonPane.setMargin(new Insets(15, 0, 0, 0));
		
		content.add(buttonPane, BorderLayout.SOUTH);
	}

}
