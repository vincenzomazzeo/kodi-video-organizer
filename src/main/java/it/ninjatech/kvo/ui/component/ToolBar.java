package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.exception.ExceptionView;
import it.ninjatech.kvo.ui.settings.ScrapersSettingsController;
import it.ninjatech.kvo.ui.settings.ScrapersSettingsView;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import com.alee.extended.panel.WebOverlay;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.NotificationOption;
import com.alee.managers.tooltip.TooltipManager;

public class ToolBar extends WebToolBar implements ActionListener {

	private static final long serialVersionUID = -2693914047879971469L;

	private final ExceptionView exceptionView;
	private WebButton scrapersSettings;
	private WebButton showExceptionsView;
	private WebLabel exceptionsToReadLabel;

	public ToolBar(ExceptionView exceptionView) {
		super(WebToolBar.HORIZONTAL);

		this.exceptionView = exceptionView;

		init();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source == this.scrapersSettings) {
			ScrapersSettingsView view = new ScrapersSettingsView();
			new ScrapersSettingsController(view);
			view.setVisible(true);
		}
		else if (source == this.showExceptionsView) {
			this.exceptionView.setVisible(true);
		}
	}

	public void showNotificationForScraperSettings(String notification) {
		NotificationManager.showNotification(this.scrapersSettings, notification, NotificationOption.discard);
	}
	
	public void notifyExceptionsToRead(int exceptionsToRead) {
		this.exceptionsToReadLabel.setText(exceptionsToRead == 0 ? null : String.valueOf(exceptionsToRead));
	}

	private void init() {
		setToolbarStyle(ToolbarStyle.attached);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setFloatable(false);

		WebOverlay showExceptionsOverlayPanel = new WebOverlay();
		add(showExceptionsOverlayPanel);
		showExceptionsOverlayPanel.setOpaque(false);

		this.showExceptionsView = WebButton.createIconWebButton(ImageRetriever.retrieveMenuBarScrapersSettings(), StyleConstants.smallRound, true);
		showExceptionsOverlayPanel.setComponent(this.showExceptionsView);
		TooltipManager.setTooltip(this.showExceptionsView, "Show Exceptions");
		this.showExceptionsView.addActionListener(this);

		this.exceptionsToReadLabel = new WebLabel();
		this.exceptionsToReadLabel.setBoldFont().setForeground(Color.RED);
		this.exceptionsToReadLabel.setBorder(getBorder());
		this.exceptionsToReadLabel.setFontSize(15);
		showExceptionsOverlayPanel.addOverlay(this.exceptionsToReadLabel, SwingConstants.TRAILING, SwingConstants.TOP);
		showExceptionsOverlayPanel.setComponentMargin(0, 0, 0, this.exceptionsToReadLabel.getPreferredSize().width);

		addSeparator();
		addSpacing(40);

		this.scrapersSettings = WebButton.createIconWebButton(ImageRetriever.retrieveMenuBarScrapersSettings(), StyleConstants.smallRound, true);
		add(this.scrapersSettings);
		TooltipManager.setTooltip(this.scrapersSettings, "Scrapers Settings");
		this.scrapersSettings.addActionListener(this);
	}

}
