package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.EnhancedLocaleLanguageComboBox;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
//TODO UIUtils
public class TvSerieEpisodeSubtitleDialog extends WebDialog implements ActionListener {

	public static TvSerieEpisodeSubtitleDialog make(TvSerieEpisode episode, String filename) {
		TvSerieEpisodeSubtitleDialog result = null;
		
		boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
		WebLookAndFeel.setDecorateDialogs(true);
		result = new TvSerieEpisodeSubtitleDialog(episode, filename);
		result.setVisible(true);
		WebLookAndFeel.setDecorateDialogs(decorateFrames);
		
		return result;
	}
	
	private static final long serialVersionUID = -5542722467275102344L;

	private EnhancedLocaleLanguageComboBox language;
	private WebButton confirm;
	private WebButton cancel;
	private boolean confirmed;

	private TvSerieEpisodeSubtitleDialog(TvSerieEpisode episode, String filename) {
		super(UI.get(), "Episode Subtitle", true);

		setIconImage(ImageRetriever.retrieveExplorerTreeTvSerie().getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		init(episode, filename);
		setResizable(false);
		pack();
		setLocationRelativeTo(getOwner());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.confirm) {
			this.confirmed = true;
			setVisible(false);
		}
		else if (event.getSource() == this.cancel) {
			setVisible(false);
		}
	}

	protected boolean isConfirmed() {
		return this.confirmed;
	}

	protected EnhancedLocale getLanguage() {
		return this.language.getLanguage();
	}

	private void init(TvSerieEpisode episode, String filename) {
		WebPanel contentPane = new WebPanel(new BorderLayout());

		setContentPane(contentPane);
		contentPane.setBackground(Colors.BACKGROUND_INFO);

		contentPane.add(makeBodyPane(episode, filename), BorderLayout.CENTER);
		contentPane.add(makeButtonPane(), BorderLayout.SOUTH);
	}

	private WebPanel makeBodyPane(TvSerieEpisode episode, String filename) {
		WebPanel result = null;

		WebLabel episodeL = new WebLabel(TvSerieUtils.getEpisodeName(episode));
		episodeL.setForeground(Colors.FOREGROUND_TITLE);
		episodeL.setShadeColor(Colors.FOREGROUND_SHADE_TITLE);
		episodeL.setDrawShade(true);
		episodeL.setFontSize(16);
		episodeL.setHorizontalAlignment(SwingConstants.CENTER);

		WebLabel filenameLabel = new WebLabel("Filename");
		filenameLabel.setForeground(Colors.FOREGROUND_STANDARD);
		filenameLabel.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		filenameLabel.setDrawShade(true);
		WebLabel filenameL = new WebLabel(filename);
		filenameL.setForeground(Colors.FOREGROUND_STANDARD);
		filenameL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		filenameL.setDrawShade(true);
		filenameL.setFontSize(14);

		WebLabel languageL = new WebLabel("Language");
		languageL.setForeground(Colors.FOREGROUND_STANDARD);
		languageL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		languageL.setDrawShade(true);

		this.language = new EnhancedLocaleLanguageComboBox();
		this.language.setPreferredWidth(300);

		result = new GroupPanel(false,
								episodeL,
								UIUtils.makeVerticalFillerPane(10, false), filenameLabel, filenameL,
								UIUtils.makeVerticalFillerPane(10, false), languageL, this.language,
								UIUtils.makeVerticalFillerPane(10, false));
		result.setMargin(10);
		result.setOpaque(false);

		return result;
	}

	private WebPanel makeButtonPane() {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.RIGHT));

		result.setOpaque(false);

		this.confirm = WebButton.createIconWebButton(ImageRetriever.retrieveDialogOk(), StyleConstants.smallRound, true);
		this.confirm.addActionListener(this);
		result.add(this.confirm);
		
		result.add(UIUtils.makeHorizontalFillerPane(5, false));
		
		this.cancel = WebButton.createIconWebButton(ImageRetriever.retrieveDialogCancel(), StyleConstants.smallRound, true);
		this.cancel.addActionListener(this);
		result.add(this.cancel);

		return result;
	}

}
