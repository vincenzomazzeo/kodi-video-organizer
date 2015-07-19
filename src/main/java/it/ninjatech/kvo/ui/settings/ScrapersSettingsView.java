package it.ninjatech.kvo.ui.settings;

import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.EnhancedLocaleLanguageComboBoxCellRenderer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.SwingUtilities;

import com.alee.extended.button.WebSwitch;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebTextField;

public class ScrapersSettingsView extends WebDialog implements ActionListener, MouseListener {

	private static final long serialVersionUID = -5869048947514865726L;

	private final ScrapersSettingsController controller;
	private WebPanel container;
	private WebSwitch theTvDbEnabled;
	private WebTextField theTvDbApiKey;
	private WebComboBox theTvDbPreferredLanguage;
	private WebSwitch fanarttvEnabled;
	private WebTextField fanarttvApiKey;
	private WebSwitch imdbEnabled;
	private WebButton confirm;

	protected ScrapersSettingsView(ScrapersSettingsController controller) {
		super(UI.get(), "Scrapers Settings", true);

		this.controller = controller;

		setIconImage(ImageRetriever.retrieveToolBarScrapersSettings().getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		init();
		pack();
		setResizable(false);
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == confirm) {
			this.controller.notifyConfirm();
		}
		else if (source == this.theTvDbEnabled) {
			handleTheTvDbEnabled();
		}
		else if (source == this.fanarttvEnabled) {
			handleFanarttvEnabled();
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 1) {
			Object source = event.getSource();
			if (source == this.theTvDbApiKey) {
				this.controller.notifyTheTvDbSecret();
			}
			else if (source == this.fanarttvApiKey) {
				this.controller.notifyFanarttvSecret();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

	protected boolean isTheTvDbEnabled() {
		return this.theTvDbEnabled.isSelected();
	}
	
	protected void setTheTvDbEnabled(boolean enabled) {
		this.theTvDbEnabled.setSelected(enabled);
		handleTheTvDbEnabled();
	}
	
	protected String getTheTvDbApiKey() {
		return this.theTvDbApiKey.getText();
	}

	protected void setTheTvDbApiKey(String apikey) {
		this.theTvDbApiKey.setText(apikey);
	}

	protected void clearTheTvDbApiKey() {
		this.theTvDbApiKey.clear();
	}

	protected EnhancedLocale getTheTvDbLanguage() {
		return (EnhancedLocale)this.theTvDbPreferredLanguage.getSelectedItem();
	}

	@SuppressWarnings("unchecked")
	protected void setTheTvDbLanguages(List<EnhancedLocale> languages) {
		this.theTvDbPreferredLanguage.removeAllItems();
		for (EnhancedLocale language : languages) {
			this.theTvDbPreferredLanguage.addItem(language);
		}
	}

	protected void selectTheTvDbLanguage(EnhancedLocale language) {
		this.theTvDbPreferredLanguage.setSelectedItem(language);
	}

	protected boolean isFanarttvEnabled() {
		return this.fanarttvEnabled.isSelected();
	}
	
	protected void setFanarttvEnabled(boolean enabled) {
		this.fanarttvEnabled.setSelected(enabled);
		handleFanarttvEnabled();
	}
	
	protected String getFanarttvApiKey() {
		return this.fanarttvApiKey.getText();
	}

	protected void setFanarttvApiKey(String apikey) {
		this.fanarttvApiKey.setText(apikey);
	}

	protected void clearFanarttvApiKey() {
		this.fanarttvApiKey.clear();
	}
	
	protected boolean isImdbEnabled() {
		return this.imdbEnabled.isSelected();
	}
	
	protected void setImdbEnabled(boolean enabled) {
		this.imdbEnabled.setSelected(true);
	}

	private void init() {
		this.container = new WebPanel(new BorderLayout());
		add(this.container);

		WebTabbedPane tabbedPane = new WebTabbedPane(WebTabbedPane.TOP, TabbedPaneStyle.attached);
		tabbedPane.addTab("TheTVDB", makeTheTvDbPane());
		tabbedPane.addTab("Fanart.tv", makeFanarttvPane());
		tabbedPane.addTab("IMDb", makeImdbPane());

		this.container.add(tabbedPane, BorderLayout.CENTER);
		this.container.add(makeButtonPane(), BorderLayout.SOUTH);
	}

	private WebPanel makeButtonPane() {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.RIGHT));

		result.setOpaque(false);
		
		this.confirm = WebButton.createWebButton(StyleConstants.smallRound, StyleConstants.shadeWidth, StyleConstants.innerShadeWidth, 0, false, false, false);
		this.confirm.setText("Confirm");
		this.confirm.addActionListener(this);
		result.add(this.confirm);

		return result;
	}

	@SuppressWarnings("unchecked")
	private WebPanel makeTheTvDbPane() {
		WebPanel result = null;
		
		WebPanel logoPane = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		WebLinkLabel logo = new WebLinkLabel(ImageRetriever.retrieveTheTvDbLogo());
		logo.setLink(TheTvDbManager.BASE_URL, false);
		logoPane.add(logo);

		WebLabel enabledL = new WebLabel("Enabled");
		enabledL.setDrawShade(true);
		
		WebPanel theTvDbEnabledPane = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		theTvDbEnabledPane.setOpaque(false);
		this.theTvDbEnabled = new WebSwitch();
		theTvDbEnabledPane.add(this.theTvDbEnabled);
		this.theTvDbEnabled.addActionListener(this);
		
		WebLabel theTvDbApiKeyL = new WebLabel("API Key");
		theTvDbApiKeyL.setDrawShade(true);

		this.theTvDbApiKey = new WebTextField(40);
		this.theTvDbApiKey.setEditable(false);
		this.theTvDbApiKey.addMouseListener(this);

		WebLabel preferredLanguageL = new WebLabel("Preferred Language");
		preferredLanguageL.setDrawShade(true);

		this.theTvDbPreferredLanguage = new WebComboBox();
		this.theTvDbPreferredLanguage.setRenderer(new EnhancedLocaleLanguageComboBoxCellRenderer());

		result = new GroupPanel(false, logoPane, UIUtils.makeVerticalFillerPane(20, false), enabledL, theTvDbEnabledPane, UIUtils.makeVerticalFillerPane(20, false), theTvDbApiKeyL, this.theTvDbApiKey, UIUtils.makeVerticalFillerPane(20, false), preferredLanguageL, this.theTvDbPreferredLanguage, UIUtils.makeVerticalFillerPane(20, false));
		result.setMargin(10);
		result.setOpaque(false);

		return result;
	}

	private WebPanel makeFanarttvPane() {
		WebPanel result = null;

		WebPanel logoPane = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		logoPane.setOpaque(false);
		WebLinkLabel logo = new WebLinkLabel(ImageRetriever.retrieveFanarttvLogo());
		logo.setLink(FanarttvManager.BASE_URL, false);
		logoPane.add(logo);

		WebLabel enabledL = new WebLabel("Enabled");
		enabledL.setDrawShade(true);
		
		WebPanel fanarttvEnabledPane = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		fanarttvEnabledPane.setOpaque(false);
		this.fanarttvEnabled = new WebSwitch();
		fanarttvEnabledPane.add(this.fanarttvEnabled);
		this.fanarttvEnabled.addActionListener(this);
		
		WebLabel fanartApiKeyL = new WebLabel("API Key");
		fanartApiKeyL.setDrawShade(true);

		this.fanarttvApiKey = new WebTextField(40);
		this.fanarttvApiKey.setEditable(false);
		this.fanarttvApiKey.addMouseListener(this);
		
		result = new GroupPanel(false, logoPane, UIUtils.makeVerticalFillerPane(20, false), enabledL, fanarttvEnabledPane, UIUtils.makeVerticalFillerPane(20, false), fanartApiKeyL, this.fanarttvApiKey, UIUtils.makeVerticalFillerPane(20, false));
		result.setMargin(10);
		result.setOpaque(false);
		
		return result;
	}

	private WebPanel makeImdbPane() {
		WebPanel result = null;

		WebPanel logoPane = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		logoPane.setOpaque(false);
		WebLinkLabel logo = new WebLinkLabel(ImageRetriever.retrieveImdbLogo());
		logo.setLink(ImdbManager.BASE_URL, false);
		logoPane.add(logo);

		WebLabel enabledL = new WebLabel("Enabled");
		enabledL.setDrawShade(true);
		
		WebPanel imdbEnabledPane = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		imdbEnabledPane.setOpaque(false);
		this.imdbEnabled = new WebSwitch();
		imdbEnabledPane.add(this.imdbEnabled);
		
		result = new GroupPanel(false, logoPane, UIUtils.makeVerticalFillerPane(20, false), enabledL, imdbEnabledPane, UIUtils.makeVerticalFillerPane(20, false));
		result.setMargin(10);
		result.setOpaque(false);

		return result;
	}
	
	private void handleTheTvDbEnabled() {
		this.theTvDbApiKey.setEnabled(this.theTvDbEnabled.isSelected());
		this.theTvDbPreferredLanguage.setEnabled(this.theTvDbEnabled.isSelected());
		if (this.theTvDbEnabled.isSelected()) {
			this.theTvDbApiKey.addMouseListener(this);
		}
		else {
			this.theTvDbApiKey.removeMouseListener(this);
		}
	}
	
	private void handleFanarttvEnabled() {
		this.fanarttvApiKey.setEnabled(this.fanarttvEnabled.isSelected());
		if (this.fanarttvEnabled.isSelected()) {
			this.fanarttvApiKey.addMouseListener(this);
		}
		else {
			this.fanarttvApiKey.removeMouseListener(this);
		}
	}

}
