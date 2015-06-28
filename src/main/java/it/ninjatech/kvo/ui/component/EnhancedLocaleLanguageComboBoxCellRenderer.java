package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.model.EnhancedLocale;

import java.awt.Component;

import javax.swing.JList;

import com.alee.laf.combobox.WebComboBoxCellRenderer;
import com.alee.laf.combobox.WebComboBoxElement;

public class EnhancedLocaleLanguageComboBoxCellRenderer extends WebComboBoxCellRenderer {

	@Override
	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		WebComboBoxElement result = (WebComboBoxElement)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if (value != null) {
			EnhancedLocale enhancedLocale = (EnhancedLocale)value;

			result.setIcon(enhancedLocale.getLanguageFlag());
			result.setText(enhancedLocale.getLanguage());
		}

		return result;
	}

}
