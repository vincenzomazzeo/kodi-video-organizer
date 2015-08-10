package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.exceptionconsole.ExceptionConsoleView;
import it.ninjatech.kvo.ui.settings.ScrapersSettingsController;
import it.ninjatech.kvo.util.Labels;

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

	private final ExceptionConsoleView exceptionConsoleView;
	private WebButton scrapersSettings;
	private WebButton showExceptionConsole;
	private WebLabel exceptionsToReadLabel;

	public ToolBar(ExceptionConsoleView exceptionConsoleView) {
		super(WebToolBar.HORIZONTAL);

		this.exceptionConsoleView = exceptionConsoleView;

		init();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source == this.scrapersSettings) {
			ScrapersSettingsController controller = new ScrapersSettingsController();
			controller.getView().setVisible(true);
		}
		else if (source == this.showExceptionConsole) {
			this.exceptionConsoleView.setVisible(true);
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

		WebOverlay showExceptionsConsoleOverlayPanel = new WebOverlay();
		add(showExceptionsConsoleOverlayPanel);
		showExceptionsConsoleOverlayPanel.setOpaque(false);

		this.showExceptionConsole = WebButton.createIconWebButton(ImageRetriever.retrieveToolBarExceptionConsole(), StyleConstants.smallRound, true);
		showExceptionsConsoleOverlayPanel.setComponent(this.showExceptionConsole);
		TooltipManager.setTooltip(this.showExceptionConsole, Labels.EXCEPTION_CONSOLE);
		this.showExceptionConsole.addActionListener(this);

		this.exceptionsToReadLabel = new WebLabel();
		this.exceptionsToReadLabel.setBoldFont().setForeground(Color.RED);
		this.exceptionsToReadLabel.setBorder(getBorder());
		this.exceptionsToReadLabel.setFontSize(15);
		showExceptionsConsoleOverlayPanel.addOverlay(this.exceptionsToReadLabel, SwingConstants.TRAILING, SwingConstants.TOP);
		showExceptionsConsoleOverlayPanel.setComponentMargin(0, 0, 0, this.exceptionsToReadLabel.getPreferredSize().width);

		addSeparator();
		addSpacing(40);

		this.scrapersSettings = WebButton.createIconWebButton(ImageRetriever.retrieveToolBarScrapersSettings(), StyleConstants.smallRound, true);
		add(this.scrapersSettings);
		TooltipManager.setTooltip(this.scrapersSettings, Labels.SCRAPERS_SETTINGS);
		this.scrapersSettings.addActionListener(this);
	}

}
