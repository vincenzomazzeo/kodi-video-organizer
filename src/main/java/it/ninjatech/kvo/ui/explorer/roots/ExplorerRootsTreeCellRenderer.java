package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class ExplorerRootsTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 3769549120815691524L;
//	private static final int ICON_SIZE = 16;
	
//	private final Icon rootIcon;
//	private final Icon tvSeriesIcon;
//	private final Icon moviesIcon;

	protected ExplorerRootsTreeCellRenderer() {
		super();

//		this.rootIcon = getIcon(IconsRetriever.IconName.ExplorerRootIcon);
//		this.tvSeriesIcon = getIcon(IconsRetriever.IconName.ExplorerRootIconTvShow);
//		this.moviesIcon = getIcon(IconsRetriever.IconName.ExplorerRootIconMovie);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		DefaultTreeCellRenderer result = (DefaultTreeCellRenderer)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		AbstractExplorerRootsTreeNode node = (AbstractExplorerRootsTreeNode)value;
		
		if (node.hasCustomIcon()) {
			result.setIcon(node.getCustomIcon());
		}
		
//		ExplorerRootsItemTreeNode node = (ExplorerRootsItemTreeNode)value;
//
//		result = (DefaultTreeCellRenderer)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
//
//		switch (node.getLevel()) {
//		case 0:
//			result.setIcon(this.rootIcon);
//			break;
//		case 1:
//			handleRootNode(node, result);
//			break;
//		default:
//		}

		return result;
	}
	
//	private void handleRootNode(ExplorerRootsItemTreeNode node, DefaultTreeCellRenderer component) {
//		switch (node.getType()) {
//		case Movie:
//			component.setIcon(this.moviesIcon);
//			break;
//		case TvSerie:
//			component.setIcon(this.tvSeriesIcon);
//			break;
//		}
//	}

//	private Icon getIcon(IconsRetriever.IconName image) {
//		Icon result = null;
//
//		result = new ImageIcon(IconsRetriever.retrieveIcon(image));
//
//		double scaleFactor = (double)ICON_SIZE / (double)result.getIconWidth();
//
//		if (scaleFactor != 0d) {
//			BufferedImage bufferedImage = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
//			Graphics2D graphics = bufferedImage.createGraphics();
//			graphics.scale(scaleFactor, scaleFactor);
//			result.paintIcon(this, graphics, 0, 0);
//			graphics.dispose();
//
//			result = new ImageIcon(bufferedImage);
//		}
//
//		return result;
//	}

}
