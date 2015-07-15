package it.ninjatech.kvo.ui.transictioneffect;

import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.alee.utils.ThreadUtils;

public class HorizontalScrollTransictionEffect implements TransictionEffect {

	private static final int DURATION = 100;

	private final JScrollPane pane;
	private final int amount;

	public HorizontalScrollTransictionEffect(JScrollPane pane, int amount) {
		this.pane = pane;
		this.amount = amount;
	}

	@Override
	public void run() {
		int tick = 0;
		double block = 0d;
		
		if (this.amount >= DURATION) {
			tick = 1;
			block = (double)this.amount / (double)DURATION;
		}
		else {
			tick = Math.abs(DURATION / this.amount);
			block = this.amount < 0 ? -1 : 1;
		}
		
		int currentAmount = 0;
		final double adder = block;
		while (Math.abs(currentAmount) <= Math.abs(this.amount)) {
			currentAmount += adder;
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					Rectangle viewRect = pane.getViewport().getViewRect();
					viewRect.x = (int)adder;
					pane.getViewport().scrollRectToVisible(viewRect);
				}
			});

			ThreadUtils.sleepSafely(tick);
		}
	}
}
