package it.ninjatech.kvo.test;

import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

public class SimpleMemorySpike {

	private static long lastFree = 0l;
	
	public static void main(String[] args) throws Exception {
		WebLookAndFeel.install();
		EnhancedLocaleMap.init();
		
		TvSerie tvSerie = new TvSerie("75682", "Bones", EnhancedLocaleMap.getByLanguage("it"));
//		TheTvDbManager.getInstance().getData(tvSerie);
//		printMemory("After TheTvDb - Before Fanarttv");
//		FanarttvManager.getInstance().getData(tvSerie);
//		printMemory("After Fanarttv - Before TvSeriesPathEntity");
		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("D:/GitHubRepository/Test")) ;
		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/Ciccio"));
		TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
		tvSeriePathEntity.setTvSerie(tvSerie);
		
		Image image = ImageIO.read(new File(tvSeriePathEntity.getPath(), "landscape.jpg"));
		
		for (int i = 0; i < 1000; i++) {
			ImageIcon icon = new ImageIcon(image);
//			JLabel label = new JLabel(new ImageIcon(image));
			WebDecoratedImage wdi = new WebDecoratedImage(icon);
			wdi.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
			wdi.setShadeWidth(5);
			wdi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			wdi.setDrawGlassLayer(false);
			TooltipManager.addTooltip(wdi, null, "Test", TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
			printMemory(i + "");
			TooltipManager.removeTooltips(wdi);
			printMemory(i + "");
		}
		
		System.in.read();
		System.in.read();
		
		WebDialog dialog = new WebDialog();
		WebPanel content = new WebPanel(new VerticalFlowLayout());
		dialog.setContentPane(content);
		
		WebDecoratedImage first = new WebDecoratedImage(image);
		first.setMinimumSize(new Dimension(image.getWidth(null), image.getHeight(null)));
		first.setShadeWidth(5);
		first.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		first.setDrawGlassLayer(false);
		TooltipManager.addTooltip(first, null, "prova", TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
		printMemory("First");
		
		ComponentTransition imageTransition = new ComponentTransition(first, new FadeTransitionEffect());
		content.add(imageTransition);
		imageTransition.setOpaque(false);
		printMemory("Transition");
		
		dialog.setVisible(true);
		System.in.read();
		System.in.read();
		
		WebDecoratedImage second = new WebDecoratedImage(image);
		second.setMinimumSize(new Dimension(image.getWidth(null), image.getHeight(null)));
		second.setShadeWidth(5);
		second.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		second.setDrawGlassLayer(false);
		TooltipManager.addTooltip(second, null, "prova", TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
		printMemory("Second");
		
		imageTransition.performTransition(second);
		
		System.in.read();
		System.in.read();
		System.out.println("Disposing");
		first.setIcon(null);
		first = null;
		second.setIcon(null);
		second = null;
		imageTransition.clearTransitionEffects();
		imageTransition.setContent(new JLabel());
		imageTransition = null;
		image = null;
		dialog.dispose();
		printMemory("Dialog - dispose");
		System.in.read();
		System.in.read();
		System.out.println("End");
		
		System.exit(0);
	}
	
	private static void printMemory(String message) {
		Runtime runtime = Runtime.getRuntime();
		DecimalFormat format = new DecimalFormat("###,###,###");
		long total = runtime.totalMemory();
		long max = runtime.totalMemory();
		long free = runtime.freeMemory();
		System.out.printf("[Total: %s - Max: %s - Occupied: %s - Delta: %s] - %s\n", 
		                  format.format(total), 
		                  format.format(max), 
		                  format.format(total - free), 
		                  format.format(lastFree - free), 
		                  message);
		lastFree = free;
	}
	
}
