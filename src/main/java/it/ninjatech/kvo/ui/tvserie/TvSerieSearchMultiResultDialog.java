package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.table.renderers.WebTableCellRenderer;

public class TvSerieSearchMultiResultDialog extends WebDialog implements MouseListener {

	private static final long serialVersionUID = -5826369642058899605L;

	private final TvSerieSearchMultiResultController controller;
	private WebTable table;

	protected TvSerieSearchMultiResultDialog(TvSerieSearchMultiResultController controller, List<TvSerie> tvSeries) {
		super(UI.get(), "TV Serie Search Multi Result", true);

		this.controller = controller;

		setIconImage(ImageRetriever.retrieveExplorerTreeTvSerie().getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		init(tvSeries);
		pack();
		setResizable(false);
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (event.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(event)) {
			int row = this.table.rowAtPoint(event.getPoint());
			String providerId = (String)this.table.getModel().getValueAt(row, 2);
			this.controller.notifyTvSerie(providerId);
		}
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

	private void init(List<TvSerie> tvSeries) {
		String[] headers = new String[] { "Title", "Language", "ID" };
		Object[][] rows = new Object[tvSeries.size()][headers.length];

		for (int i = 0, n = tvSeries.size(); i < n; i++) {
			TvSerie tvSerie = tvSeries.get(i);
			rows[i] = new Object[] { tvSerie.getName(), tvSerie.getLanguage().getLanguageFlag(), tvSerie.getProviderId() };
		}

		this.table = new WebTable(rows, headers);
		this.table.setEditable(false);
		this.table.setRowSelectionAllowed(false);
		this.table.setColumnSelectionAllowed(false);
		this.table.setPreferredScrollableViewportSize(new Dimension(520, 300));
		this.table.addMouseListener(this);

		this.table.getColumnModel().getColumn(1).setCellRenderer(new FlagRenderer());
		this.table.getColumnModel().getColumn(2).setCellRenderer(new TextRenderer(SwingConstants.RIGHT));

		this.table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(350);
		this.table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(350);
		this.table.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
		this.table.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
		
		WebScrollPane container = new WebScrollPane(this.table);
		container.getVerticalScrollBar().setUnitIncrement(30);
		container.getVerticalScrollBar().setBlockIncrement(30);
		add(container);
	}
	
	private static class FlagRenderer extends WebTableCellRenderer {

		private static final long serialVersionUID = -6827597465922841179L;

		private FlagRenderer() {
			super();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			WebTableCellRenderer renderer = (WebTableCellRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			renderer.setIcon((ImageIcon)value);
			renderer.setText(null);
			renderer.setHorizontalAlignment(SwingConstants.CENTER);

			return renderer;
		}

	}
	
	private static class TextRenderer extends WebTableCellRenderer {

		private static final long serialVersionUID = 5086591492258233337L;
		
		private final int textPosition;

		private TextRenderer(int textPosition) {
			super();
			
			this.textPosition = textPosition;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			WebTableCellRenderer renderer = (WebTableCellRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			renderer.setHorizontalAlignment(this.textPosition);

			return renderer;
		}

	}

}
