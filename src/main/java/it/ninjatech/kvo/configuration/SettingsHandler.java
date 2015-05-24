package it.ninjatech.kvo.configuration;

import it.ninjatech.kvo.utils.Utils;

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
			this.settings = new Settings();
			this.settings.setLastMoviesRootParent(FileUtils.getUserHome());
			this.settings.setLastTvSeriesRootParent(FileUtils.getUserHome());
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
			// TODO gestire
			e.printStackTrace();
		}
	}

	public Settings getSettings() {
		return this.settings;
	}

}
