package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.util.TvSerieUtils;

import java.io.File;

public class TvSerieSeasonCreator extends AbstractWorker<Boolean> {

	private final TvSerieSeason season;
	
	public TvSerieSeasonCreator(TvSerieSeason season) {
		this.season = season;
	}
	
	@Override
	public Boolean work() throws Exception {
		boolean result = false;
		
		File seasonPath = TvSerieUtils.getLocalSeasonPath(this.season);
		result = seasonPath.mkdir();
		
		return result;
	}

}