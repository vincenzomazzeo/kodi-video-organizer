package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.util.Logger;

public class SettingsStorer extends AbstractWorker<Void> {

	@Override
	public Void work() throws Exception {
		Logger.log("-> storing settings\n");
		
		SettingsHandler.getInstance().store();
		
		return null;
	}

}
