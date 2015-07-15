package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.EnhancedLocaleLanguageComboBoxCellRenderer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;

public class TvSerieSearchView extends WebDialog implements ActionListener {

	private static final long serialVersionUID = 5517804638406906373L;
	
	private final TvSerieSearchController controller;
	private final WebPanel container;
	private WebTextField searchF;
	private WebComboBox languageCB;
	private WebButton searchB;

	protected TvSerieSearchView(TvSerieSearchController controller) {
		super(UI.get(), "Search for TV Serie", true);

		this.controller = controller;
		this.container = new WebPanel(new BorderLayout());

		setIconImage(ImageRetriever.retrieveExplorerTreeTvSerie().getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		init();
		pack();
		setResizable(false);
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.controller.notifySearch();
	}

	protected String getSearch() {
		return this.searchF.getText();
	}
	
	protected EnhancedLocale getLanguage() {
		return (EnhancedLocale)this.languageCB.getSelectedItem();
	}
	
	@SuppressWarnings("unchecked")
	protected void setLanguages(List<EnhancedLocale> languages) {
		this.languageCB.removeAllItems();
		for (EnhancedLocale language : languages) {
			this.languageCB.addItem(language);
		}
	}
	
	private void init() {
		add(this.container);
		
		this.container.add(makeBodyPane(), BorderLayout.CENTER);
		this.container.add(makeButtonPane(), BorderLayout.SOUTH);
	}
	
	@SuppressWarnings("unchecked")
	private WebPanel makeBodyPane() {
		WebPanel result = null;

		WebLabel searchL = new WebLabel("Search");
		searchL.setDrawShade(true);

		this.searchF = new WebTextField(40);

		WebLabel languageL = new WebLabel("Language");
		languageL.setDrawShade(true);

		this.languageCB = new WebComboBox();
		this.languageCB.setRenderer(new EnhancedLocaleLanguageComboBoxCellRenderer());

		result = new GroupPanel(false, UIUtils.makeVerticalFillerPane(20, true), searchL, this.searchF, UIUtils.makeVerticalFillerPane(20, true), languageL, this.languageCB, UIUtils.makeVerticalFillerPane(20, true));
		result.setMargin(10);

		return result;
	}
	
	private WebPanel makeButtonPane() {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.RIGHT));
		
		this.searchB = WebButton.createWebButton(StyleConstants.smallRound, StyleConstants.shadeWidth, StyleConstants.innerShadeWidth, 0, false, false, false);
		this.searchB.setText("Confirm");
		this.searchB.addActionListener(this);
		result.add(this.searchB);
		
		return result;
	}
	
}
