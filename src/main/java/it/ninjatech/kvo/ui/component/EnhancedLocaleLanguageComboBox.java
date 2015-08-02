package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.BorderPainter;
import com.alee.extended.window.WebPopOver;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;

// TODO UIUtils
public class EnhancedLocaleLanguageComboBox extends WebPanel implements ListSelectionListener, FocusListener {

	private static final long serialVersionUID = 1214907204033728747L;

	@SuppressWarnings("unchecked")
	private static WebScrollPane makeList(ListSelectionListener listener, WebList list, Vector<EnhancedLocale> languages, int width, int height) {
		WebScrollPane result = null;

		list.setCellRenderer(new EnhancedLocaleLanguageComboBoxCellRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setListData(languages);
		list.addListSelectionListener(listener);

		result = new WebScrollPane(list, false, false);
		result.setPreferredWidth(width);
		result.setPreferredHeight(height);
		result.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		result.getVerticalScrollBar().setBlockIncrement(30);
		result.getVerticalScrollBar().setUnitIncrement(30);

		return result;
	}

	private EnhancedLocale language;
	private WebLabel value;
	private WebButton expand;
	private WebList popupSelectedLanguages;
	private WebList popupLanguages;
	private WebPopOver popup;
	private boolean firstExpandFocus;
	private List<EnhancedLocale> languages;
	private boolean showEmpty;

	public EnhancedLocaleLanguageComboBox(EnhancedLocale language) {
		super(new BorderLayout());

		init();

		this.language = null;
		this.firstExpandFocus = true;
		this.showEmpty = language != null && EnhancedLocaleMap.isEmptyLocale(language);

		if (language != null) {
			this.language = language;
		}
		else {
			List<String> lastSelectedLanguages = SettingsHandler.getInstance().getSettings().getLastSelectedLanguages();
			if (lastSelectedLanguages.isEmpty()) {
				this.language = EnhancedLocaleMap.getLanguages().iterator().next();
			}
			else {
				this.language = EnhancedLocaleMap.getByLanguage(lastSelectedLanguages.get(0));
			}
		}
		setLanguage();
	}

	public EnhancedLocaleLanguageComboBox() {
		this(null);
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getSource() == this.popupSelectedLanguages) {
			this.language = (EnhancedLocale)this.popupSelectedLanguages.getSelectedValue();
		}
		else if (event.getSource() == this.popupLanguages) {
			this.language = (EnhancedLocale)this.popupLanguages.getSelectedValue();
		}
		if (!EnhancedLocaleMap.isEmptyLocale(this.language)) {
			SettingsHandler.getInstance().getSettings().addLastSelectedLanguage(this.language);
			SettingsHandler.getInstance().store();
		}
		setLanguage();
		this.expand.requestFocus();
	}

	@Override
	public void focusGained(FocusEvent event) {
		if (event.getSource() == this.expand) {
			if (this.firstExpandFocus) {
				this.firstExpandFocus = false;
				this.value.requestFocus();
			}
			else {
				showPopup();
			}
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() == this.popup) {
			final FocusListener focusListener = this;
			this.expand.removeFocusListener(focusListener);
			closePopup();

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					expand.addFocusListener(focusListener);
					value.requestFocus();
				}

			});
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		this.expand.setEnabled(enabled);
		this.value.setEnabled(enabled);
	}

	public void setLanguages(List<EnhancedLocale> languages) {
		this.languages = languages;
	}

	public EnhancedLocale getLanguage() {
		return this.language;
	}

	public void setLanguage(EnhancedLocale language) {
		this.language = language;
		setLanguage();
	}

	private void setLanguage() {
		this.value.setIcon(this.language.getLanguageFlag());
		this.value.setText(this.language.getLanguage());
	}

	private void closePopup() {
		if (this.popup != null) {
			this.popup.removeFocusListener(this);
			this.popup.setVisible(false);
			this.popup.dispose();
			this.popup = null;
			this.popupSelectedLanguages = null;
			this.popupLanguages = null;
		}
	}

	private void init() {
		setOpaque(false);
		BorderPainter<?> borderPainter = new BorderPainter<>();
		borderPainter.setMargin(2, 5, 2, 2);
		setPainter(borderPainter);

		this.value = new WebLabel();
		add(this.value, BorderLayout.CENTER);
		this.value.setForeground(Colors.FOREGROUND_STANDARD);
		this.value.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		this.value.setDrawShade(true);

		this.expand = WebButton.createIconWebButton(ImageRetriever.retrieveComboboxArrow(), StyleConstants.smallRound, true);
		add(this.expand, BorderLayout.EAST);
		this.expand.addFocusListener(this);
	}

	private void showPopup() {
		this.popup = new WebPopOver(this);
		this.popup.setLayout(new VerticalFlowLayout());
		this.popup.setAutoRequestFocus(true);
		this.popup.addFocusListener(this);
		this.popup.setFocusable(true);

		List<String> lastSelectedLanguages = SettingsHandler.getInstance().getSettings().getLastSelectedLanguages();
		if (!lastSelectedLanguages.isEmpty()) {
			this.popupSelectedLanguages = new WebList();

			Vector<EnhancedLocale> selectedLanguagesData = new Vector<>();
			for (String lastSelectedLanguage : lastSelectedLanguages) {
				EnhancedLocale language = EnhancedLocaleMap.getByLanguage(lastSelectedLanguage);
				if (this.languages == null || this.languages.contains(language)) {
					selectedLanguagesData.add(EnhancedLocaleMap.getByLanguage(lastSelectedLanguage));
				}
			}

			if (!selectedLanguagesData.isEmpty()) {
				WebScrollPane selectedLanguagesPane = makeList(this, this.popupSelectedLanguages, selectedLanguagesData, this.getWidth(), selectedLanguagesData.size() > 1 ? 50 : 25);
				this.popup.add(selectedLanguagesPane);

				this.popup.add(WebSeparator.createHorizontal());
			}
		}
		this.popupLanguages = new WebList();
		Vector<EnhancedLocale> languagesData = new Vector<>(this.languages != null ? this.languages : EnhancedLocaleMap.getLanguages());
		if (this.showEmpty) {
			languagesData.insertElementAt(EnhancedLocaleMap.getEmptyLocale(), 0);
		}

		WebScrollPane languagesPane = makeList(this, this.popupLanguages, languagesData, this.getWidth(), 150);
		this.popup.add(languagesPane);

		this.popup.show(this);
	}

}
