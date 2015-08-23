package it.ninjatech.kvo.tvserie;

import java.io.File;

public class TvSerieCheckPathWorkerTask implements TvSerieWorkerTask {

	@Override
	public boolean doTask(TvSeriePathEntity tvSeriePathEntity) throws Exception {
		boolean result = false;
		
		File path = new File(tvSeriePathEntity.getPath());
		result = path.exists() && path.isDirectory();
		
		return result;
	}

}
