package it.ninjatech.kvo.ui;

import java.net.URL;

public final class ImagesRetriever {

	public enum Image {
		
		ExplorerRootIconMovieMenu("root_icon_movie_menu.png"),
		ExplorerRootIconMovie("root_icon_movie.png"),
		ExplorerRootIconTvShowMenu("root_icon_tvshow_menu.png"),
		ExplorerRootIconTvShow("root_icon_tvshow.png"),
		ExplorerRootIcon("root_icon.png");
		
		private final String value; 
		
		private Image(String name) {
			this.value = String.format("/images/%s", name);
		}
		
	}
	
	public static URL retrieveImage(Image image) {
		return ImagesRetriever.class.getResource(image.value);
	}
	
	private ImagesRetriever() {}
	
}
