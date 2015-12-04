package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.Labels;

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
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;

public class RemoveDialog extends WebDialog implements ActionListener {

    private static final long serialVersionUID = -5813444271972837821L;
    private static RemoveDialog self;

	public static RemoveDialog getInstance(String title, String message) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new RemoveDialog();
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
			self.setShowCloseButton(false);
			self.setShowTitleComponent(false);
		}
		
		self.set(title, message);

		return self;
	}
	
	private boolean confirmed;
	
	private WebLabel title;
	private WebImage icon;
	private WebLabel message;
	private WebCheckBox removeFromDisk;
	private WebButton ok;
	private WebButton cancel;
	
	private RemoveDialog() {
		super(UI.get(), true);

		init();
		
		setDefaultCloseOperation(WebDialog.HIDE_ON_CLOSE);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.cancel) {
			this.confirmed = false;
		}
		else if (event.getSource() == this.ok) {
			this.confirmed = true;
		}
		setVisible(false);
	}

	public boolean isConfirmed() {
		return this.confirmed;
	}
	
	public boolean isRemoveFromDisk() {
	    return this.removeFromDisk.isSelected();
	}

	private void set(String title, String message) {
		this.title.setText(title);
		this.message.setText(message);
		this.removeFromDisk.setSelected(false);
		
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
		
		ImageIcon icon = ImageRetriever.retrieveMessageDialogQuestionMark();
		this.icon = new WebImage(icon);
		messagePane.add(this.icon, BorderLayout.WEST);
		messagePane.add(UIUtils.makeHorizontalFillerPane(icon.getIconWidth(), false), BorderLayout.EAST);
		
		this.message = UIUtils.makeStandardLabel("", 16, null, SwingConstants.CENTER);
		messagePane.add(this.message, BorderLayout.CENTER);
		this.message.setMinimumWidth(200);
		
		this.removeFromDisk = UIUtils.makeStandardCheckBox(Labels.REMOVE_FROM_DISK);
		messagePane.add(UIUtils.makeFlowLayoutPane(FlowLayout.LEFT, 5, 5, UIUtils.makeHorizontalFillerPane(icon.getIconWidth(), false), 
		                                           this.removeFromDisk), BorderLayout.SOUTH);
		
		this.ok = UIUtils.makeButton(ImageRetriever.retrieveMessageDialogOk(), this);
		this.cancel = UIUtils.makeButton(ImageRetriever.retrieveMessageDialogCancel(), this);
		WebPanel buttonPane = UIUtils.makeFlowLayoutPane(FlowLayout.RIGHT, 5, 0, this.ok, this.cancel);
		buttonPane.setMargin(new Insets(15, 0, 0, 0));
		
		content.add(buttonPane, BorderLayout.SOUTH);
	}
	
}
