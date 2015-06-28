package it.ninjatech.kvo.ui.settings;

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

	private ScrapersSettingsController controller;
	private final WebPanel container;
	private WebTextField theTvDbApikeyF;
	private WebComboBox theTvDbPreferredLanguageCB;
	private WebButton confirmB;

	public ScrapersSettingsView() {
		super(UI.get(), "Scrapers Settings", true);

		this.container = new WebPanel(new BorderLayout());

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		init();
		pack();
		setResizable(false);
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.controller.notifyConfirm();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		Object source = event.getSource();
		if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 1 && source == this.theTvDbApikeyF) {
			this.controller.notifyTheTvDbSecret();
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

	protected void setController(ScrapersSettingsController controller) {
		this.controller = controller;
	}
	
	protected String getTheTvDbApikey() {
		return this.theTvDbApikeyF.getText();
	}
	
	protected void setTheTvDbApikey(String apikey) {
		this.theTvDbApikeyF.setText(apikey);
	}
	
	protected void clearTheTvDbApikey() {
		this.theTvDbApikeyF.clear();
	}
	
	protected EnhancedLocale getTheTvDbLanguage() {
		return (EnhancedLocale)this.theTvDbPreferredLanguageCB.getSelectedItem();
	}
	
	@SuppressWarnings("unchecked")
	protected void setTheTvDbLanguages(List<EnhancedLocale> languages) {
		this.theTvDbPreferredLanguageCB.removeAllItems();
		for (EnhancedLocale language : languages) {
			this.theTvDbPreferredLanguageCB.addItem(language);
		}
	}
	
	protected void selectTheTvDbLanguage(EnhancedLocale language) {
		this.theTvDbPreferredLanguageCB.setSelectedItem(language);
	}

	private void init() {
		add(this.container);
		
		WebTabbedPane tabbedPane = new WebTabbedPane(WebTabbedPane.TOP, TabbedPaneStyle.attached);
		tabbedPane.addTab("TheTVDB", makeTheTvDbPane());
		
		this.container.add(tabbedPane, BorderLayout.CENTER);
		this.container.add(makeButtonPane(), BorderLayout.SOUTH);
	}
	
	private WebPanel makeButtonPane() {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.RIGHT));
		
		this.confirmB = WebButton.createWebButton(StyleConstants.smallRound, StyleConstants.shadeWidth, StyleConstants.innerShadeWidth, 0, false, false, false);
		this.confirmB.setText("Confirm");
		this.confirmB.addActionListener(this);
		result.add(this.confirmB);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	private WebPanel makeTheTvDbPane() {
		WebPanel result = null;

		WebPanel logoPane = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		WebLinkLabel logo = new WebLinkLabel(ImageRetriever.retrieveTheTvDbLogo());
		logo.setLink("http://thetvdb.com", false);
		logoPane.add(logo);
		
		WebLabel theTvDbApiKeyL = new WebLabel("API Key");
		theTvDbApiKeyL.setDrawShade(true);

		this.theTvDbApikeyF = new WebTextField(40);
		this.theTvDbApikeyF.setEditable(false);
		this.theTvDbApikeyF.addMouseListener(this);

		WebLabel preferredLanguageL = new WebLabel("Preferred Language");
		preferredLanguageL.setDrawShade(true);

		this.theTvDbPreferredLanguageCB = new WebComboBox();
		this.theTvDbPreferredLanguageCB.setRenderer(new EnhancedLocaleLanguageComboBoxCellRenderer());

		result = new GroupPanel(false, logoPane, UIUtils.makeSeparatorPane(20), theTvDbApiKeyL, this.theTvDbApikeyF, UIUtils.makeSeparatorPane(20), preferredLanguageL, this.theTvDbPreferredLanguageCB, UIUtils.makeSeparatorPane(20));
		result.setMargin(10);

		return result;
	}
	
}
