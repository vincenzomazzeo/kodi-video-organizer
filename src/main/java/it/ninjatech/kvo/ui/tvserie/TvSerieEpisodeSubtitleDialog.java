package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.Labels;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.EnhancedLocaleLanguageComboBox;
import it.ninjatech.kvo.util.MemoryUtils;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;

public class TvSerieEpisodeSubtitleDialog extends WebDialog implements ActionListener {

	private static TvSerieEpisodeSubtitleDialog self;
	
	public static TvSerieEpisodeSubtitleDialog getInstance(TvSerieEpisode episode, String filename) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new TvSerieEpisodeSubtitleDialog();
			self.setShowCloseButton(false);
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
		}
		
		self.set(episode, filename);

		return self;
	}
	
	private static final long serialVersionUID = -5542722467275102344L;

	private EnhancedLocaleLanguageComboBox language;
	private WebButton confirm;
	private WebButton cancel;
	private boolean confirmed;

	private TvSerieEpisodeSubtitleDialog() {
		super(UI.get(), "Episode Subtitle", true);

		setIconImage(ImageRetriever.retrieveExplorerTreeTvSerie().getImage());
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		setResizable(false);
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

	public void release() {
		System.out.println("*** TvSerieEpisodeSubtitleDialog -> release ***");
		MemoryUtils.printMemory("Before TvSerieEpisodeSubtitleDialog release");
		setContentPane(new WebPanel());
		MemoryUtils.printMemory("After TvSerieEpisodeSubtitleDialog release");
	}
	
	protected boolean isConfirmed() {
		return this.confirmed;
	}

	protected EnhancedLocale getLanguage() {
		return this.language.getLanguage();
	}
	
	private void set(TvSerieEpisode episode, String filename) {
		init(episode, filename);
		pack();
		setLocationRelativeTo(getOwner());
	}

	private void init(TvSerieEpisode episode, String filename) {
		WebPanel contentPane = new WebPanel(new BorderLayout());

		setContentPane(contentPane);
		contentPane.setBackground(Colors.BACKGROUND_INFO);

		this.confirm = new WebButton();
		this.cancel = new WebButton();
		
		contentPane.add(makeBodyPane(episode, filename), BorderLayout.CENTER);
		contentPane.add(UIUtils.makeConfirmCancelButtonPane(this.confirm, this.cancel, this), BorderLayout.SOUTH);
	}

	private WebPanel makeBodyPane(TvSerieEpisode episode, String filename) {
		WebPanel result = null;

		WebLabel episodeL = UIUtils.makeTitleLabel(TvSerieUtils.getEpisodeName(episode), 16, null);
		episodeL.setHorizontalAlignment(SwingConstants.CENTER);

		WebLabel filenameLabel = UIUtils.makeStandardLabel(Labels.FILENAME, null, null);
		WebLabel filenameL = UIUtils.makeStandardLabel(filename, 14, null);

		WebLabel languageL = UIUtils.makeStandardLabel(Labels.LANGUAGE, null, null);

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

//	private WebPanel makeButtonPane() {
//		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.RIGHT));
//
//		result.setOpaque(false);
//
//		this.confirm = WebButton.createIconWebButton(ImageRetriever.retrieveDialogOk(), StyleConstants.smallRound, true);
//		this.confirm.addActionListener(this);
//		result.add(this.confirm);
//		
//		result.add(UIUtils.makeHorizontalFillerPane(5, false));
//		
//		this.cancel = WebButton.createIconWebButton(ImageRetriever.retrieveDialogCancel(), StyleConstants.smallRound, true);
//		this.cancel.addActionListener(this);
//		result.add(this.cancel);
//
//		return result;
//	}

}
