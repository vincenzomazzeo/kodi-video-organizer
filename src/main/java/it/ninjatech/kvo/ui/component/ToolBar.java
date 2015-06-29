package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.settings.ScrapersSettingsController;
import it.ninjatech.kvo.ui.settings.ScrapersSettingsView;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.NotificationOption;
import com.alee.managers.tooltip.TooltipManager;

public class ToolBar extends WebToolBar implements ActionListener {

	private static final long serialVersionUID = -2693914047879971469L;

	private WebButton scrapersSettings;

	public ToolBar() {
		super(WebToolBar.HORIZONTAL);

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
	}
	
	public void showNotificationForScraperSettings(String notification) {
		NotificationManager.showNotification(this.scrapersSettings, notification, NotificationOption.discard);
	}

	private void init() {
		setToolbarStyle(ToolbarStyle.attached);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setFloatable(false);

		this.scrapersSettings = WebButton.createIconWebButton(ImageRetriever.retrieveMenuBarScrapersSettings(), StyleConstants.smallRound, true);
		add(this.scrapersSettings);
		TooltipManager.setTooltip(this.scrapersSettings, "Scrapers Settings");
		this.scrapersSettings.addActionListener(this);
	}

}
