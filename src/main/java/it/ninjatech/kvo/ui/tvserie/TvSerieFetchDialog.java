package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.EnhancedLocaleLanguageComboBox;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.Labels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang3.StringUtils;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.list.WebListCellRenderer;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.table.renderers.WebTableCellRenderer;
import com.alee.laf.text.WebTextField;

public class TvSerieFetchDialog extends WebDialog implements ActionListener, ListSelectionListener {

    private static final long serialVersionUID = 5589915260921364947L;
    private static TvSerieFetchDialog self;

    public static TvSerieFetchDialog getInstance(TvSerieFetchController controller) {
        if (self == null) {
            boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
            WebLookAndFeel.setDecorateDialogs(true);
            self = new TvSerieFetchDialog(controller);
            self.setShowCloseButton(false);
            WebLookAndFeel.setDecorateDialogs(decorateFrames);
        }
        else {
            self.controller = controller;
        }

        return self;
    }

    private final Map<String, Search> searchMap;
    private final Set<String> missing;
    private TvSerieFetchController controller;
    private WebList list;
    private WebPanel tvSerie;
    private WebButton ok;
    private WebButton cancel;

    private TvSerieFetchDialog(TvSerieFetchController controller) {
        super(UI.get(), Labels.SEARCH_FOR_TV_SERIE, true);

        this.searchMap = new HashMap<>();
        this.missing = new HashSet<>();
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
        if (event.getSource() == this.ok) {
            Map<String, Object> data = new HashMap<>();

            for (Search search : this.searchMap.values()) {
                if (search.ignore.isSelected()) {
                    this.missing.remove(search.id);
                    data.put(search.id, null);
                }
                else {
                    if (search.getClass().equals(FreeTextSearch.class)) {
                        FreeTextSearch freeTextSearch = (FreeTextSearch)search;
                        String searchText = freeTextSearch.search.getText();
                        if (StringUtils.isBlank(searchText)) {
                            this.missing.add(search.id);
                        }
                        else {
                            this.missing.remove(search.id);
                            data.put(search.id, new Object[] {searchText, freeTextSearch.language.getLanguage()});
                        }
                    }
                    else if (search.getClass().equals(MultiResultSearch.class)) {
                        MultiResultSearch multiResultSearch = (MultiResultSearch)search;
                        int selectedRow = multiResultSearch.table.getSelectedRow();
                        if (selectedRow == -1) {
                            this.missing.add(search.id);
                        }
                        else {
                            this.missing.remove(search.id);
                            data.put(search.id, multiResultSearch.tvSeries.get(selectedRow));
                        }
                    }
                }
            }

            if (this.missing.isEmpty()) {
                this.controller.notifyConfirm(data);
            }
            else {
                this.list.revalidate();
                this.list.repaint();
            }
        }
        else if (event.getSource() == this.cancel) {
            this.controller.notifyCancel();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting() && this.list.getSelectedIndex() >= 0) {
            String name = (String)this.list.getModel().getElementAt(this.list.getSelectedIndex());
            this.tvSerie.removeAll();
            this.tvSerie.add(this.searchMap.get(name));
            this.tvSerie.revalidate();
            this.tvSerie.repaint();
        }
    }
    
    @SuppressWarnings({ "rawtypes" })
    protected void release() {
        this.searchMap.clear();
        this.missing.clear();
        this.controller = null;
        ((DefaultListModel)this.list.getModel()).removeAllElements();
        this.tvSerie.removeAll();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void addFreeText(String id, String name) {
        this.searchMap.put(name, new FreeTextSearch(id));
        ((DefaultListModel)this.list.getModel()).addElement(name);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void addMultiResult(String id, String name, List<TvSerie> tvSeries) {
        this.searchMap.put(name, new MultiResultSearch(id, tvSeries));
        ((DefaultListModel)this.list.getModel()).addElement(name);
    }
    
    @SuppressWarnings("rawtypes")
    protected void remove(String id, String name) {
        ((DefaultListModel)this.list.getModel()).removeElement(name);
        this.searchMap.remove(name);
    }

    @SuppressWarnings("unchecked")
    private void init() {
        WebPanel content = UIUtils.makeStandardPane(new BorderLayout());
        setContentPane(content);
        content.setMargin(new Insets(10, 10, 5, 10));
        content.setOpaque(true);
        content.setBackground(Colors.BACKGROUND_INFO);

        setContentPane(content);

        WebLabel message = UIUtils.makeStandardLabel(Labels.FOLLOWING_TV_SERIES_MISSING, 16, null, SwingConstants.CENTER);
        content.add(message, BorderLayout.NORTH);

        WebPanel centerPane = UIUtils.makeStandardPane(new BorderLayout());
        content.add(centerPane, BorderLayout.CENTER);

        this.list = UIUtils.makeList();
        centerPane.add(this.list, BorderLayout.WEST);
        this.list.setModel(new DefaultListModel<String>());
        this.list.addListSelectionListener(this);
        this.list.setCellRenderer(new ListRenderer(this));
        this.list.setPreferredSize(new Dimension(200, 0));
        this.list.setBorder(BorderFactory.createLineBorder(Colors.BACKGROUND_INFO_ALPHA));

        this.tvSerie = UIUtils.makeStandardPane(new FlowLayout(FlowLayout.CENTER));
        centerPane.add(this.tvSerie, BorderLayout.CENTER);
        this.tvSerie.setPreferredSize(new Dimension(550, 400));

        this.ok = UIUtils.makeButton(ImageRetriever.retrieveMessageDialogOk(), this);
        this.cancel = UIUtils.makeButton(ImageRetriever.retrieveMessageDialogCancel(), this);
        WebPanel buttonPane = UIUtils.makeFlowLayoutPane(FlowLayout.RIGHT, 5, 0, this.ok, this.cancel);
        content.add(buttonPane, BorderLayout.SOUTH);
        buttonPane.setMargin(new Insets(15, 0, 0, 0));
    }

    private static abstract class Search extends WebPanel implements ActionListener {

        private static final long serialVersionUID = -8229493134577767038L;
        private final String id;
        protected WebCheckBox ignore;

        private Search(String id) {
            super(new BorderLayout());

            this.id = id;
        }

        private void init() {
            setBackground(Colors.BACKGROUND_INFO);
            setPreferredWidth(520);

            this.ignore = UIUtils.makeStandardCheckBox(Labels.IGNORE_TV_SERIE);
            this.ignore.addActionListener(this);

            add(UIUtils.makeFlowLayoutPane(FlowLayout.LEFT, 10, 20, this.ignore), BorderLayout.NORTH);
        }

    }

    private static class FreeTextSearch extends Search {

        private static final long serialVersionUID = 6985604522744915514L;
        private WebTextField search;
        private EnhancedLocaleLanguageComboBox language;

        private FreeTextSearch(String id) {
            super(id);

            init();
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            this.search.setEnabled(!this.ignore.isSelected());
            this.language.setEnabled(!this.ignore.isSelected());
        }

        private void init() {
            super.init();

            this.search = new WebTextField(40);
            this.search.setBackground(Colors.BACKGROUND_INFO);
            this.search.setForeground(Colors.FOREGROUND_STANDARD);

            this.language = new EnhancedLocaleLanguageComboBox(EnhancedLocaleMap.getEmptyLocale());

            add(UIUtils.makeVerticalFlowLayoutPane(UIUtils.makeVerticalFillerPane(20, false), UIUtils.makeStandardLabel(Labels.SEARCH, null, null, null), this.search,
                                                   UIUtils.makeVerticalFillerPane(20, false), UIUtils.makeStandardLabel(Labels.LANGUAGE, null, null, null), this.language),
                BorderLayout.CENTER);
        }

    }

    private static class MultiResultSearch extends Search {

        private static final long serialVersionUID = -5291750959562811903L;
        private final List<TvSerie> tvSeries;
        private WebTable table;

        private MultiResultSearch(String id, List<TvSerie> tvSeries) {
            super(id);

            this.tvSeries = tvSeries;

            init();
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            this.table.setEnabled(!this.ignore.isSelected());
        }

        private void init() {
            super.init();

            String[] headers = new String[] { Labels.TITLE, Labels.LANGUAGE, Labels.ID };
            Object[][] rows = new Object[this.tvSeries.size()][headers.length];

            for (int i = 0, n = this.tvSeries.size(); i < n; i++) {
                TvSerie tvSerie = this.tvSeries.get(i);
                rows[i] = new Object[] { tvSerie.getName(), tvSerie.getLanguage().getLanguageFlag(), tvSerie.getProviderId() };
            }

            this.table = new WebTable(rows, headers);
            this.table.setEditable(false);
            this.table.setRowSelectionAllowed(true);
            this.table.setColumnSelectionAllowed(false);
            this.table.setPreferredScrollableViewportSize(new Dimension(520, 317));
            this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            this.table.getColumnModel().getColumn(0).setCellRenderer(new TableTextRenderer(SwingConstants.LEFT));
            this.table.getColumnModel().getColumn(1).setCellRenderer(new TableFlagRenderer());
            this.table.getColumnModel().getColumn(2).setCellRenderer(new TableTextRenderer(SwingConstants.RIGHT));

            this.table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(350);
            this.table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(350);
            this.table.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
            this.table.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);

            WebScrollPane tablePane = UIUtils.makeScrollPane(this.table, WebScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, WebScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            tablePane.getViewport().setBackground(Colors.BACKGROUND_INFO);
            tablePane.setBorder(BorderFactory.createLineBorder(Colors.BACKGROUND_INFO_ALPHA));
            add(tablePane);
        }

    }

    private static class ListRenderer extends WebListCellRenderer {

        private static final long serialVersionUID = 8020508070739678576L;

        private final TvSerieFetchDialog dialog;
        
        private ListRenderer(TvSerieFetchDialog dialog) {
            super();
            
            this.dialog = dialog;
        }

        @Override
        public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            String id = this.dialog.searchMap.get((String)value).id;
            if (this.dialog.missing.contains(id)) {
                ((WebLabel)renderer).setBoldFont();
            }
            
            if (isSelected) {
                renderer.setBackground(Colors.BACKGROUND_INFO_ALPHA);
            }
            else {
                renderer.setBackground(Colors.BACKGROUND_INFO);
                renderer.setForeground(Colors.FOREGROUND_STANDARD);
            }

            return renderer;
        }

    }

    private static class TableFlagRenderer extends WebTableCellRenderer {

        private static final long serialVersionUID = -6827597465922841179L;

        private TableFlagRenderer() {
            super();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            WebTableCellRenderer renderer = (WebTableCellRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            renderer.setIcon((ImageIcon)value);
            renderer.setText(null);
            renderer.setHorizontalAlignment(SwingConstants.CENTER);
            if (isSelected) {
                renderer.setBackground(Colors.BACKGROUND_INFO_ALPHA);
            }
            else {
                renderer.setBackground(Colors.BACKGROUND_INFO);
                renderer.setForeground(Colors.FOREGROUND_STANDARD);
            }

            return renderer;
        }

    }

    private static class TableTextRenderer extends WebTableCellRenderer {

        private static final long serialVersionUID = 5086591492258233337L;

        private final int textPosition;

        private TableTextRenderer(int textPosition) {
            super();

            this.textPosition = textPosition;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            WebTableCellRenderer renderer = (WebTableCellRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            renderer.setHorizontalAlignment(this.textPosition);
            if (isSelected) {
                renderer.setBackground(Colors.BACKGROUND_INFO_ALPHA);
            }
            else {
                renderer.setBackground(Colors.BACKGROUND_INFO);
                renderer.setForeground(Colors.FOREGROUND_STANDARD);
            }

            return renderer;
        }

    }

}
