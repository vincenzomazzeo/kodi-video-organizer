package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.util.Labels;

import java.io.File;

import com.alee.utils.filefilter.DirectoriesFilter;

public final class TvSerieWorkerTasks {

	protected static boolean check(File path, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
		boolean result = false;
		
		progressNotifier.notifyTaskInit("Checking " + path.getName() + " existence", 1);
		result = path.exists() && path.isDirectory();
		progressNotifier.notifyTaskUpdate(null, 1);
		
		return result;
	}

	protected static TvSeriePathEntity fetch(TvSeriePathEntity tvSeriePathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
		TvSeriePathEntity result = null;
		
		// TODO handle progress notifier
		if (check(new File(tvSeriePathEntity.getPath()), progressNotifier)) {
			if (tvSeriePathEntity.getTvSerie() != null) {
				TheTvDbManager.getInstance().getData(tvSeriePathEntity.getTvSerie());
				FanarttvManager.getInstance().getData(tvSeriePathEntity.getTvSerie());
				// TODO scan
				// TODO update object
				// TODO save
			}
		}
		else {
			if (tvSeriePathEntity.getTvSerie() != null) {
				// TODO delete
			}
		}
		
		return result;
	}
	
	protected static void save(TvSeriesPathEntity tvSeriesPathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
		progressNotifier.notifyTaskInit(Labels.dbSavingEntity(tvSeriesPathEntity.getLabel()), 1);
		TvSeriesPathEntityDbMapper mapper = new TvSeriesPathEntityDbMapper();
		mapper.save(tvSeriesPathEntity);
		progressNotifier.notifyTaskUpdate(null, 1);
	}
	
	protected static void scan(TvSeriesPathEntity tvSeriesPathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
		File root = new File(tvSeriesPathEntity.getPath());
		File[] directories = root.listFiles(new DirectoriesFilter());
		progressNotifier.notifyTaskInit(Labels.START_SCANNING, directories.length);
		for (int i = 0; i < directories.length; i++) {
			File directory = directories[i];
			progressNotifier.notifyTaskUpdate(directory.getName(), null);
			tvSeriesPathEntity.addTvSerie(directory);
			progressNotifier.notifyTaskUpdate(null, i + 1);
		}
	}
	
	protected static TvSeriePathEntity scan(TvSeriePathEntity tvSeriePathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
		TvSeriePathEntity result = null;
		
		// TODO handle progress notifier
		if (check(new File(tvSeriePathEntity.getPath()), progressNotifier)) {
			if (tvSeriePathEntity.getTvSerie() != null) {
				// TODO scan
				// TODO update object
				// TODO save
			}
			else {
				// TODO fs scan
			}
		}
		else {
			if (tvSeriePathEntity.getTvSerie() != null) {
				// TODO delete
			}
		}
		
		return result;
	}
	
	private TvSerieWorkerTasks() {}
	
}
