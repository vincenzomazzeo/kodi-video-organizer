package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.tvserie.model.TvSerieFanart;
import it.ninjatech.kvo.util.Utils;

import java.io.File;

import com.alee.utils.FileUtils;

public class CachedFanartCopyWorker extends AbstractWorker<Boolean> {

	private final String name;
	private final String destination;
	private final TvSerieFanart fanart;
	
	public CachedFanartCopyWorker(String name, String destination, TvSerieFanart fanart) {
		this.name = name;
		this.destination = destination;
		this.fanart = fanart;
	}
	
	@Override
	public Boolean work() throws Exception {
		Boolean result = false;
		
		File source = new File(Utils.getCacheDirectory(), this.name);
		File destination = new File(this.destination, this.fanart.getFilename());
		result = FileUtils.copyFile(source, destination);
		
		return result;
	}
	
}
