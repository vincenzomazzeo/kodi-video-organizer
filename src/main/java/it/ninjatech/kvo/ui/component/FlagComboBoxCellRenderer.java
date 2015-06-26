package it.ninjatech.kvo.ui.component;

import java.awt.Component;

import javax.swing.JList;

import com.alee.laf.combobox.WebComboBoxCellRenderer;
import com.alee.laf.combobox.WebComboBoxElement;

public class FlagComboBoxCellRenderer extends WebComboBoxCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		WebComboBoxElement result = (WebComboBoxElement)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		// value è il tipo che mi serve
		
//		result.setIcon(null);
//		result.setText(null);
		
		return result;
	}

}
