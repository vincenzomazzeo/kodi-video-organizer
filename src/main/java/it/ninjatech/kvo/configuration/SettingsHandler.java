package it.ninjatech.kvo.configuration;

import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.Utils;

import java.io.File;

import com.alee.utils.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SettingsHandler {

	private static SettingsHandler self;

	public static void init() throws Exception {
		if (self == null) {
			self = new SettingsHandler();
		}
	}

	public static SettingsHandler getInstance() {
		return self;
	}

	private final File settingsFile;
	private final ObjectMapper objectMapper;
	private final Settings settings;

	private SettingsHandler() throws Exception {
		this.settingsFile = new File(Utils.getWorkingDirectory(), "settings.kvo");
		this.objectMapper = new ObjectMapper();
		this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		if (!this.settingsFile.exists()) {
			if (!this.settingsFile.createNewFile()) {
				throw new Exception(Labels.FAILED_TO_CREATE_SETTINGS_FILE);
			}
			this.settings = new Settings();
			this.settings.setLastMoviesRootParent(FileUtils.getUserHome());
			this.settings.setLastTvSeriesRootParent(FileUtils.getUserHome());
			this.settings.setTheTvDbEnabled(false);
			this.settings.setFanarttvEnabled(false);
			this.settings.setImdbEnabled(true);
			this.settings.setMyApiFilmsEnabled(true);
			store();
		}
		else {
			this.settings = this.objectMapper.readValue(this.settingsFile, Settings.class);
		}
	}

	public void store() {
		try {
			this.objectMapper.writeValue(this.settingsFile, this.settings);
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
	}

	public Settings getSettings() {
		return this.settings;
	}

}
