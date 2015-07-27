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

public class TvSerieSearchDialog extends WebDialog implements ActionListener {

	private static final long serialVersionUID = 5517804638406906373L;
	
	private final TvSerieSearchController controller;
	private final WebPanel container;
	private WebTextField search;
	private WebComboBox language;
	private WebButton startSearch;

	protected TvSerieSearchDialog(TvSerieSearchController controller) {
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
		return this.search.getText();
	}
	
	protected EnhancedLocale getLanguage() {
		return (EnhancedLocale)this.language.getSelectedItem();
	}
	
	@SuppressWarnings("unchecked")
	protected void setLanguages(List<EnhancedLocale> languages) {
		this.language.removeAllItems();
		for (EnhancedLocale language : languages) {
			this.language.addItem(language);
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

		this.search = new WebTextField(40);

		WebLabel languageL = new WebLabel("Language");
		languageL.setDrawShade(true);

		this.language = new WebComboBox();
		this.language.setRenderer(new EnhancedLocaleLanguageComboBoxCellRenderer());

		result = new GroupPanel(false, UIUtils.makeVerticalFillerPane(20, true), searchL, this.search, UIUtils.makeVerticalFillerPane(20, true), languageL, this.language, UIUtils.makeVerticalFillerPane(20, true));
		result.setMargin(10);

		return result;
	}
	
	private WebPanel makeButtonPane() {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.RIGHT));
		
		this.startSearch = WebButton.createWebButton(StyleConstants.smallRound, StyleConstants.shadeWidth, StyleConstants.innerShadeWidth, 0, false, false, false);
		this.startSearch.setText("Confirm");
		this.startSearch.addActionListener(this);
		result.add(this.startSearch);
		
		return result;
	}
	
}
