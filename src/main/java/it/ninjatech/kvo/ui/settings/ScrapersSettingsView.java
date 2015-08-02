package it.ninjatech.kvo.ui.settings;

import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.myapifilms.MyApiFilmsManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.EnhancedLocaleLanguageComboBox;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.alee.extended.button.WebSwitch;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebTextField;
//TODO UIUtils - make apikey custom dialog
public class ScrapersSettingsView extends WebDialog implements ActionListener, MouseListener {

	private static WebPanel makeLogoPane(ImageIcon logoImage, String baseUrl) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		
		result.setOpaque(false);
		WebLinkLabel logo = new WebLinkLabel(logoImage);
		logo.setLink(baseUrl, false);
		result.add(logo);
		
		return result;
	}
	
	private static WebPanel makeLogoPane(String text, String baseUrl) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		
		result.setOpaque(false);
		WebLinkLabel logo = new WebLinkLabel(text);
		logo.setLink(baseUrl, false);
		logo.setForeground(Colors.FOREGROUND_STANDARD);
		logo.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		logo.setDrawShade(true);
		logo.setFontSize(20);
		result.add(logo);
		
		return result;
	}
	
	private static WebLabel makeLabel(String label) {
		WebLabel result = new WebLabel(label);
		
		result.setForeground(Colors.FOREGROUND_STANDARD);
		result.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		result.setDrawShade(true);
		
		return result;
	}
	
	private static WebPanel makeEnabledPane(WebSwitch enabledSwitch, ActionListener actionListener) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		
		result.setOpaque(false);
		result.add(enabledSwitch);
		enabledSwitch.addActionListener(actionListener);
		enabledSwitch.getLeftComponent().setBackground(Colors.BACKGROUND_INFO);
		enabledSwitch.getLeftComponent().setOpaque(true);
		enabledSwitch.getLeftComponent().setForeground(Colors.FOREGROUND_STANDARD);
		enabledSwitch.getLeftComponent().setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		enabledSwitch.getRightComponent().setBackground(Colors.BACKGROUND_INFO);
		enabledSwitch.getRightComponent().setOpaque(true);
		enabledSwitch.getRightComponent().setForeground(Colors.FOREGROUND_STANDARD);
		enabledSwitch.getRightComponent().setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		
		return result;
	}
	
	private static void setApiKey(WebTextField apiKey, MouseListener mouseListener) {
		apiKey.setEditable(false);
		apiKey.addMouseListener(mouseListener);
		apiKey.setBackground(Colors.BACKGROUND_INFO);
		apiKey.setForeground(Colors.FOREGROUND_STANDARD);
	}
	
	private static final long serialVersionUID = -5869048947514865726L;

	private final ScrapersSettingsController controller;
	private WebPanel container;
	private WebSwitch theTvDbEnabled;
	private WebTextField theTvDbApiKey;
	private EnhancedLocaleLanguageComboBox theTvDbPreferredLanguage;
	private WebSwitch fanarttvEnabled;
	private WebTextField fanarttvApiKey;
	private WebSwitch myApiFilmsEnabled;
	private WebSwitch imdbEnabled;
	private WebButton confirm;
	private WebButton cancel;

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
		if (event.getSource() == this.confirm) {
			this.controller.notifyConfirm();
		}
		else if (event.getSource() == this.cancel) {
			setVisible(false);
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
		return this.theTvDbPreferredLanguage.getLanguage();
	}

	protected void setTheTvDbLanguages(List<EnhancedLocale> languages) {
		this.theTvDbPreferredLanguage.setLanguages(languages);
	}

	protected void selectTheTvDbLanguage(EnhancedLocale language) {
		this.theTvDbPreferredLanguage.setLanguage(language);
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
	
	public boolean isMyApiFilmsEnabled() {
		return this.myApiFilmsEnabled.isSelected();
	}

	public void setMyApiFilmsEnabled(boolean enabled) {
		this.myApiFilmsEnabled.setSelected(enabled);
	}

	protected boolean isImdbEnabled() {
		return this.imdbEnabled.isSelected();
	}
	
	protected void setImdbEnabled(boolean enabled) {
		this.imdbEnabled.setSelected(enabled);
	}

	private void init() {
		this.container = new WebPanel(new BorderLayout());
		add(this.container);
		
		this.container.setBackground(Colors.BACKGROUND_INFO);

		WebTabbedPane tabbedPane = new WebTabbedPane(WebTabbedPane.TOP, TabbedPaneStyle.attached);
		tabbedPane.addTab("TheTVDB", makeTheTvDbPane());
		tabbedPane.addTab("Fanart.tv", makeFanarttvPane());
		tabbedPane.addTab("MyApiFilms", makeMyApiFilmsPane());
		tabbedPane.addTab("IMDb", makeImdbPane());
		tabbedPane.setBackground(Colors.BACKGROUND_INFO);
		tabbedPane.setForeground(Colors.FOREGROUND_STANDARD_OPPOSITE);

		this.container.add(tabbedPane, BorderLayout.CENTER);
		this.container.add(makeButtonPane(), BorderLayout.SOUTH);
	}

	private WebPanel makeButtonPane() {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.RIGHT));

		result.setOpaque(false);

		this.confirm = WebButton.createIconWebButton(ImageRetriever.retrieveDialogOk(), StyleConstants.smallRound, true);
		this.confirm.addActionListener(this);
		result.add(this.confirm);
		
		result.add(UIUtils.makeHorizontalFillerPane(5, false));
		
		this.cancel = WebButton.createIconWebButton(ImageRetriever.retrieveDialogCancel(), StyleConstants.smallRound, true);
		this.cancel.addActionListener(this);
		result.add(this.cancel);

		return result;
	}

	private WebPanel makeTheTvDbPane() {
		WebPanel result = null;
		
		this.theTvDbEnabled = new WebSwitch();
		this.theTvDbApiKey = new WebTextField(40);
		
		WebPanel logoPane = makeLogoPane(ImageRetriever.retrieveTheTvDbLogo(), TheTvDbManager.BASE_URL);

		WebLabel enabled = makeLabel("Enabled");
		WebPanel enabledPane = makeEnabledPane(this.theTvDbEnabled, this); 
		
		WebLabel apiKey = makeLabel("API Key");
		setApiKey(this.theTvDbApiKey, this);

		WebLabel preferredLanguageL = new WebLabel("Preferred Language");
		preferredLanguageL.setForeground(Colors.FOREGROUND_STANDARD);
		preferredLanguageL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		preferredLanguageL.setDrawShade(true);

		this.theTvDbPreferredLanguage = new EnhancedLocaleLanguageComboBox(EnhancedLocaleMap.getEmptyLocale());

		result = new GroupPanel(false, logoPane, UIUtils.makeVerticalFillerPane(20, false), enabled, enabledPane, UIUtils.makeVerticalFillerPane(20, false), apiKey, this.theTvDbApiKey, UIUtils.makeVerticalFillerPane(20, false), preferredLanguageL, this.theTvDbPreferredLanguage, UIUtils.makeVerticalFillerPane(20, false));
		result.setMargin(10);
		result.setOpaque(false);

		return result;
	}

	private WebPanel makeFanarttvPane() {
		WebPanel result = null;

		this.fanarttvEnabled = new WebSwitch();
		this.fanarttvApiKey = new WebTextField(40);
		
		WebPanel logoPane = makeLogoPane(ImageRetriever.retrieveFanarttvLogo(), FanarttvManager.BASE_URL);

		WebLabel enabled = makeLabel("Enabled");
		WebPanel enabledPane = makeEnabledPane(this.fanarttvEnabled, this);
		
		WebLabel apiKey = makeLabel("API Key");
		setApiKey(this.fanarttvApiKey, this);

		result = new GroupPanel(false, logoPane, UIUtils.makeVerticalFillerPane(20, false), enabled, enabledPane, UIUtils.makeVerticalFillerPane(20, false), apiKey, this.fanarttvApiKey, UIUtils.makeVerticalFillerPane(20, false));
		result.setMargin(10);
		result.setOpaque(false);
		
		return result;
	}
	
	private WebPanel makeMyApiFilmsPane() {
		WebPanel result = null;
		
		this.myApiFilmsEnabled = new WebSwitch();
		
		WebPanel logoPane = makeLogoPane("My Api Films", MyApiFilmsManager.BASE_URL);
		
		WebLabel enabled = makeLabel("Enabled");
		WebPanel enabledPane = makeEnabledPane(this.myApiFilmsEnabled, this);
		
		result = new GroupPanel(false, logoPane, UIUtils.makeVerticalFillerPane(20, false), enabled, enabledPane, UIUtils.makeVerticalFillerPane(20, false));
		result.setMargin(10);
		result.setOpaque(false);
		
		return result;
	}

	private WebPanel makeImdbPane() {
		WebPanel result = null;

		this.imdbEnabled = new WebSwitch();
		
		WebPanel logoPane = makeLogoPane(ImageRetriever.retrieveImdbLogo(), ImdbManager.BASE_URL);

		WebLabel enabled = makeLabel("Enabled");
		WebPanel enabledPane = makeEnabledPane(this.imdbEnabled, this);
		
		result = new GroupPanel(false, logoPane, UIUtils.makeVerticalFillerPane(20, false), enabled, enabledPane, UIUtils.makeVerticalFillerPane(20, false));
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
