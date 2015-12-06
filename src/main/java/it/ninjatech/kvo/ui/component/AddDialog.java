package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.Labels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;

public class AddDialog extends WebDialog implements ActionListener {

    private static final long serialVersionUID = 2412260839876302709L;
    private static AddDialog self;

    public static AddDialog getInstance(String title) {
        if (self == null) {
            boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
            WebLookAndFeel.setDecorateDialogs(true);
            self = new AddDialog();
            WebLookAndFeel.setDecorateDialogs(decorateFrames);
            self.setShowCloseButton(false);
            self.setShowTitleComponent(false);
        }

        self.set(title);

        return self;
    }

    private boolean confirmed;

    private WebLabel title;
    private WebTextField search;
    private EnhancedLocaleLanguageComboBox language;
    private WebButton ok;
    private WebButton cancel;

    private AddDialog() {
        super(UI.get(), true);

        init();

        setDefaultCloseOperation(WebDialog.HIDE_ON_CLOSE);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == this.cancel) {
            this.confirmed = false;
        }
        else if (event.getSource() == this.ok && StringUtils.isNotBlank(this.search.getText())) {
            this.confirmed = true;
        }
        setVisible(false);
    }

    public boolean isConfirmed() {
        return this.confirmed;
    }

    public String getSearchString() {
        return this.search.getText();
    }
    
    public EnhancedLocale getLanguage() {
        return this.language.getLanguage();
    }

    private void set(String title) {
        this.title.setText(title);
        this.search.clear();
        this.language.setLanguage(EnhancedLocaleMap.getEmptyLocale());

        pack();
        setLocationRelativeTo(UI.get());
    }

    private void init() {
        WebPanel content = UIUtils.makeStandardPane(new BorderLayout());
        setContentPane(content);
        content.setMargin(new Insets(10, 10, 5, 10));
        content.setOpaque(true);
        content.setBackground(Colors.BACKGROUND_INFO);

        this.title = UIUtils.makeStandardLabel("", 20, new Insets(0, 0, 15, 0), SwingConstants.CENTER);
        content.add(this.title, BorderLayout.NORTH);

        this.search = new WebTextField(40);
        this.search.setBackground(Colors.BACKGROUND_INFO);
        this.search.setForeground(Colors.FOREGROUND_STANDARD);

        this.language = new EnhancedLocaleLanguageComboBox(EnhancedLocaleMap.getEmptyLocale());

        content.add(UIUtils.makeVerticalFlowLayoutPane(UIUtils.makeVerticalFillerPane(20, false), UIUtils.makeStandardLabel(Labels.SEARCH, null, null, null), this.search,
                                                       UIUtils.makeVerticalFillerPane(20, false), UIUtils.makeStandardLabel(Labels.LANGUAGE, null, null, null), this.language),
                    BorderLayout.CENTER);

        this.ok = UIUtils.makeButton(ImageRetriever.retrieveMessageDialogOk(), this);
        this.cancel = UIUtils.makeButton(ImageRetriever.retrieveMessageDialogCancel(), this);
        WebPanel buttonPane = UIUtils.makeFlowLayoutPane(FlowLayout.RIGHT, 5, 0, this.ok, this.cancel);
        buttonPane.setMargin(new Insets(15, 0, 0, 0));

        content.add(buttonPane, BorderLayout.SOUTH);
    }

}
