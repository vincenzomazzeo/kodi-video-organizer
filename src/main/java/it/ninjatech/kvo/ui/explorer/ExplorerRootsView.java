package it.ninjatech.kvo.ui.explorer;

import it.ninjatech.kvo.ui.ImagesRetriever;

import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.alee.extended.menu.DynamicMenuType;
import com.alee.extended.menu.WebDynamicMenu;
import com.alee.extended.menu.WebDynamicMenuItem;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tree.WebTree;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

public class ExplorerRootsView extends WebScrollPane implements MouseListener, ActionListener {

	private static final long serialVersionUID = -4394641156105182581L;

	private static final String TOOLTIP = "<html><div align='center'>Here you can add your video root folders<br />Just <i>CTRL + click</i> to add a new root!</div></html>";

	private static WebTree<DefaultMutableTreeNode> makeTree(ExplorerRootsModel model) {
		WebTree<DefaultMutableTreeNode> result = new WebTree<>(model);

		result.setEditable(false);
		result.setSelectionMode(WebTree.SINGLE_TREE_SELECTION);
		TooltipManager.setTooltip(result, TOOLTIP, TooltipWay.up, 0);

		return result;
	}

	private ExplorerRootsController controller;
	private final WebDynamicMenu addRootMenu;
	private final WebDynamicMenuItem addTvShowsRootMenuItem;
	private final WebDynamicMenuItem addMoviesRootMenuItem;

	protected ExplorerRootsView(ExplorerRootsModel model) {
		super(makeTree(model), true, false);

		this.addRootMenu = new WebDynamicMenu();
		this.addTvShowsRootMenuItem = new WebDynamicMenuItem();
		this.addMoviesRootMenuItem = new WebDynamicMenuItem();

		getVerticalScrollBar().setUnitIncrement(30);
		
		((WebTree<?>)this.getViewport().getView()).addMouseListener(this);

		initAddRootMenu();
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		int mask = KeyEvent.BUTTON1_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK;
		if (mouseEvent.getModifiersEx() == mask) {
			this.controller.notifyAddRoot(mouseEvent.getX(), mouseEvent.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
	}

	public void setController(ExplorerRootsController controller) {
		this.controller = controller;
	}

	protected void showAddRootMenu(int x, int y) {
		this.addRootMenu.showMenu(this, new Point(x, y));
	}

	private void initAddRootMenu() {
		this.addRootMenu.setType(DynamicMenuType.shutter);
		this.addRootMenu.setHideType(DynamicMenuType.shutter);
		this.addRootMenu.setRadius(100);
		this.addRootMenu.setStepProgress(0.06f);

		this.addTvShowsRootMenuItem.setMargin(new Insets(8, 8, 8, 8));
		this.addTvShowsRootMenuItem.setDrawBorder(true);
		this.addTvShowsRootMenuItem.setIcon(new ImageIcon(ImagesRetriever.retrieveImage(ImagesRetriever.Image.ExplorerRootIconTvShowMenu)));
		this.addTvShowsRootMenuItem.setAction(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				addRootMenu.hideMenu();
				controller.notifyAddTvShowsRoot();
			}
		});
		this.addRootMenu.addItem(this.addTvShowsRootMenuItem);

		this.addMoviesRootMenuItem.setMargin(new Insets(8, 8, 8, 8));
		this.addMoviesRootMenuItem.setDrawBorder(true);
		this.addMoviesRootMenuItem.setIcon(new ImageIcon(ImagesRetriever.retrieveImage(ImagesRetriever.Image.ExplorerRootIconMovieMenu)));
		this.addMoviesRootMenuItem.setAction(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				addRootMenu.hideMenu();
				controller.notifyAddMoviesRoot();
			}
		});
		this.addRootMenu.addItem(this.addMoviesRootMenuItem);
	}

}
