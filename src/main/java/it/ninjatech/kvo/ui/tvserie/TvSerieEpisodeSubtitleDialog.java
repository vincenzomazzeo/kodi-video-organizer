package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.EnhancedLocaleLanguageComboBoxCellRenderer;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;

public class TvSerieEpisodeSubtitleDialog extends WebDialog implements ActionListener {

	private static final long serialVersionUID = -5542722467275102344L;
	
	private WebComboBox language;
	private WebButton startSearch;
	private boolean confirmed;

	@SuppressWarnings("unchecked")
	protected TvSerieEpisodeSubtitleDialog() {
		super(UI.get(), "Episode Subtitle", true);
		
		setIconImage(ImageRetriever.retrieveExplorerTreeTvSerie().getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		init();
		pack();
		setResizable(false);
		setLocationRelativeTo(getOwner());
		
		for (EnhancedLocale language : EnhancedLocaleMap.getLanguages()) {
			this.language.addItem(language);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		this.confirmed = true;
		setVisible(false);
	}
	
	protected boolean isConfirmed() {
		return this.confirmed;
	}

	protected EnhancedLocale getLanguage() {
		return (EnhancedLocale)this.language.getSelectedItem();
	}
	
	private void init() {
		WebPanel contentPane = new WebPanel(new BorderLayout());
		
		setContentPane(contentPane);
		
		contentPane.add(makeBodyPane(), BorderLayout.CENTER);
		contentPane.add(makeButtonPane(), BorderLayout.SOUTH);
	}
	
	@SuppressWarnings("unchecked")
	private WebPanel makeBodyPane() {
		WebPanel result = null;

		WebLabel languageL = new WebLabel("Language");
		languageL.setDrawShade(true);

		this.language = new WebComboBox();
		this.language.setRenderer(new EnhancedLocaleLanguageComboBoxCellRenderer());

		result = new GroupPanel(false, UIUtils.makeVerticalFillerPane(20, true), languageL, this.language, UIUtils.makeVerticalFillerPane(20, true));
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
