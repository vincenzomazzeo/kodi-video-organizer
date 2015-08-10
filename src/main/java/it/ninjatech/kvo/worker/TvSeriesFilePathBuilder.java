package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.util.Labels;

import java.io.File;

import com.alee.utils.filefilter.DirectoriesFilter;

public class TvSeriesFilePathBuilder extends AbstractPathBuilder {

	private final File root;

	public TvSeriesFilePathBuilder(File root) {
		super();

		this.root = root;
	}

	@Override
	public AbstractPathEntity work() throws Exception {
		TvSeriesPathEntity result = new TvSeriesPathEntity(this.root);

		File[] directories = this.root.listFiles(new DirectoriesFilter());
		notifyInit(Labels.START_SCANNING, directories.length);
		for (int i = 0; i < directories.length; i++) {
			File directory = directories[i];
			notifyUpdate(directory.getName(), null);
			result.addTvSerie(directory);
			notifyUpdate(null, i + 1);
		}

		return result;
	}

}
