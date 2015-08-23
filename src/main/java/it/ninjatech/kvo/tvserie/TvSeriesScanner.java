package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.worker.AbstractWorker;

import java.io.File;

import com.alee.utils.filefilter.DirectoriesFilter;

public class TvSeriesScanner extends AbstractWorker<Void> {

	private final TvSeriesPathEntity entity;

	protected TvSeriesScanner(TvSeriesPathEntity entity) {
		this.entity = entity;
	}

	@Override
	public Void work() throws Exception {
		File root = new File(this.entity.getPath());
		
		File[] directories = root.listFiles(new DirectoriesFilter());
		notifyInit(Labels.START_SCANNING, directories.length);
		for (int i = 0; i < directories.length; i++) {
			File directory = directories[i];
			notifyUpdate(directory.getName(), null);
			this.entity.addTvSerie(directory);
			notifyUpdate(null, i + 1);
		}

		return null;
	}

}
