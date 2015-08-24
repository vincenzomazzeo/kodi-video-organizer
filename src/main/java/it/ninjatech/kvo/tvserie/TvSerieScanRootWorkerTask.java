package it.ninjatech.kvo.tvserie;

import java.io.File;

import com.alee.utils.filefilter.DirectoriesFilter;

public class TvSerieScanRootWorkerTask implements TvSerieWorkerTask<TvSeriesPathEntity> {

	@Override
	public boolean doTask(TvSeriesPathEntity tvSeriesPathEntity) throws Exception {
		boolean result = false;
		
		File root = new File(tvSeriesPathEntity.getPath());
		if (root.exists() && root.isDirectory()) {
			File[] directories = root.listFiles(new DirectoriesFilter());
			for (int i = 0; i < directories.length; i++) {
				File directory = directories[i];
				tvSeriesPathEntity.addTvSerie(directory);
			}
			
			result = true;
		}
		
		return result;
	}

}
