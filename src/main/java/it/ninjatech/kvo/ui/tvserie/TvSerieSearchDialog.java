package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.EnhancedLocaleLanguageComboBox;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.Labels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingConstants;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;

public class TvSerieSearchDialog extends WebDialog implements ActionListener {

	private static final long serialVersionUID = 5517804638406906373L;
	private static TvSerieSearchDialog self;
	
	public static TvSerieSearchDialog getInstance(TvSerieSearchController controller) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new TvSerieSearchDialog(controller);
			self.setShowCloseButton(false);
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
		}

		return self;
	}
	
	private final TvSerieSearchController controller;
	private WebTextField search;
	private EnhancedLocaleLanguageComboBox language;
	private WebButton confirm;
	private WebButton cancel;

	private TvSerieSearchDialog(TvSerieSearchController controller) {
		super(UI.get(), Labels.SEARCH_FOR_TV_SERIE, true);

		this.controller = controller;

		setIconImage(ImageRetriever.retrieveExplorerTreeTvSerie().getImage());
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		init();
		pack();
		setResizable(false);
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.confirm) {
			this.controller.notifySearch();
		}
		else if (event.getSource() == this.cancel) {
			setVisible(false);
		}
	}

	protected String getSearch() {
		return this.search.getText();
	}
	
	protected EnhancedLocale getLanguage() {
		return this.language.getLanguage();
	}
	
	protected void setLanguages(List<EnhancedLocale> languages) {
		this.language.setLanguages(languages);
	}
	
	private void init() {
		WebPanel contentPane = new WebPanel(new BorderLayout());

		setContentPane(contentPane);
		contentPane.setBackground(Colors.BACKGROUND_INFO);
		
		this.confirm = new WebButton();
		this.cancel = new WebButton();
		
		contentPane.add(makeBodyPane(), BorderLayout.CENTER);
		contentPane.add(UIUtils.makeConfirmCancelButtonPane(this.confirm, this.cancel, this), BorderLayout.SOUTH);
	}
	
	private WebPanel makeBodyPane() {
		WebPanel result = null;

		WebLabel search = UIUtils.makeStandardLabel(Labels.SEARCH, null, null, null);
		search.setHorizontalAlignment(SwingConstants.CENTER);

		this.search = new WebTextField(40);
		this.search.setBackground(Colors.BACKGROUND_INFO);
		this.search.setForeground(Colors.FOREGROUND_STANDARD);
		
		WebLabel language = UIUtils.makeStandardLabel(Labels.LANGUAGE, null, null, null);

		this.language = new EnhancedLocaleLanguageComboBox(EnhancedLocaleMap.getEmptyLocale());
		this.language.setPreferredWidth(300);

		result = new GroupPanel(false,
		                        UIUtils.makeVerticalFillerPane(20, false), search, this.search,
		                        UIUtils.makeVerticalFillerPane(20, false), language, this.language,
		                        UIUtils.makeVerticalFillerPane(20, false));
		result.setMargin(10);
		result.setOpaque(false);

		return result;
	}
	
}
