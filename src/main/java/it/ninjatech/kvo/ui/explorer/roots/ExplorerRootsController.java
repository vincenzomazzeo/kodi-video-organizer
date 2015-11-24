package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.configuration.Settings;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.explorer.ExplorerController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractFsExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractRootExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractRootsExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSerieExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.ui.tvserie.TvSerieFetchController;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.worker.SettingsStorer;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.tree.TreePath;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.managers.notification.NotificationManager;
import com.alee.utils.swing.DialogOptions;

public class ExplorerRootsController {

    private final ExplorerRootsModel model;

    private final ExplorerController parent;
    private final ExplorerRootsView view;
    private final WebDirectoryChooser rootChooser;

    public ExplorerRootsController(ExplorerRootsModel model, ExplorerController parent, List<TvSeriesPathEntity> tvSeriesPathEntities) {
        this.model = model;
        this.parent = parent;
        this.view = new ExplorerRootsView(this, this.model);

        this.rootChooser = new WebDirectoryChooser(UI.get());

        if (!tvSeriesPathEntities.isEmpty()) {
            for (TvSeriesPathEntity tvSeriesPathEntity : tvSeriesPathEntities) {
                this.model.addRoot(tvSeriesPathEntity);
            }
            this.view.removeTooltip();
        }
    }

    public ExplorerRootsView getView() {
        return this.view;
    }

    public void searchForTvSerie(TvSerieExplorerRootsTreeNode node) {
        TvSerieFetchController tvSerieSearchController = new TvSerieFetchController(Collections.singleton(node.getValue()));
        Map<TvSeriePathEntity, Boolean> searchResult = tvSerieSearchController.search();
        TvSeriesExplorerRootsTreeNode parent = (TvSeriesExplorerRootsTreeNode)node.getParent();
        for (TvSeriePathEntity tvSeriePathEntity : searchResult.keySet()) {
            if (searchResult.get(tvSeriePathEntity)) {
                this.parent.addTvSerieTile(tvSeriePathEntity);
                this.model.nodeChanged(node);
            }
            else {
                parent.getValue().removeTvSerie(tvSeriePathEntity);
                parent.remove(node);
                this.model.nodeStructureChanged(parent);
            }
        }
    }

    public void scanTvSeries(TvSeriesExplorerRootsTreeNode node) {
    }
    
    public void searchForTvSeries(TvSeriesExplorerRootsTreeNode node) {
        if (TvSerieManager.getInstance().check(node.getValue())) {
            TvSerieFetchController tvSerieSearchController = new TvSerieFetchController(node.getValue().getTvSeries());
            Map<TvSeriePathEntity, Boolean> searchResult = tvSerieSearchController.search();
            Set<TvSeriePathEntity> entitiesToRefresh = new HashSet<>();
            Set<TvSeriePathEntity> entitiesToRemove = new HashSet<>();
            for (TvSeriePathEntity tvSeriePathEntity : searchResult.keySet()) {
                if (searchResult.get(tvSeriePathEntity)) {
                    entitiesToRefresh.add(tvSeriePathEntity);
                    this.parent.addTvSerieTile(tvSeriePathEntity);
                }
                else {
                    entitiesToRemove.add(tvSeriePathEntity);
                    node.getValue().removeTvSerie(tvSeriePathEntity);
                }
            }
            if (!entitiesToRefresh.isEmpty() || !entitiesToRemove.isEmpty()) {
                if (!entitiesToRefresh.isEmpty()) {
                    for (TvSerieExplorerRootsTreeNode child : node.findChildren(entitiesToRefresh).values()) {
                        this.model.nodeChanged(child);
                    }
                }
                if (!entitiesToRemove.isEmpty()) {
                    for (TvSerieExplorerRootsTreeNode child : node.findChildren(entitiesToRemove).values()) {
                        node.remove(child);
                    }
                    this.model.nodeStructureChanged(node);
                }
                NotificationManager.showNotification(this.view, Labels.notificationTvSeriesRefreshRemove(entitiesToRefresh, entitiesToRemove));
            }
        }
        else {
            // Root exists no more!
            this.model.removeRoot(node);
            NotificationManager.showNotification(this.view, Labels.notificationTvSeriesRootRemoved(node.getValue().getLabel()));
        }
    }

    protected void notifyAddRoot(int x, int y) {
        this.view.showAddRootMenu(x, y);
    }

    protected void notifyAddTvSeriesRoot() {
        Settings settings = SettingsHandler.getInstance().getSettings();

        File root = showRootChooser(Type.TvSerie, settings.getLastTvSeriesRootParent());
        TvSeriesPathEntity tvSeriesPathEntity = TvSerieManager.getInstance().addTvSeriesPathEntity(root);
        if (tvSeriesPathEntity != null) {
            if (root.getParentFile() != null) {
                settings.setLastTvSeriesRootParent(root.getParentFile());
                storeSettings();
            }
            this.model.addRoot(tvSeriesPathEntity);
            NotificationManager.showNotification(Labels.notificationRootAdded(Type.TvSerie.getPlural(), root.getName())).setDisplayTime(TimeUnit.SECONDS.toMillis(3));
            this.parent.addTvSerieTab();
            this.view.removeTooltip();
        }
    }

    protected void notifyAddMoviesRoot() {
// Settings settings = SettingsHandler.getInstance().getSettings();
//
// File root = showRootChooser(Type.Movie, settings.getLastMoviesRootParent());
// if (root != null && !this.model.containtsRoot(root)) {
// if (root.getParentFile() != null) {
// settings.setLastMoviesRootParent(root.getParentFile());
// storeSettings();
// }
// addRoot(root, Type.Movie);
// this.view.removeTooltip();
// }
    }

    protected void notifyPossibleFsScanning(TreePath path) {
        if (path.getLastPathComponent() instanceof AbstractRootExplorerRootsTreeNode) {
            AbstractRootExplorerRootsTreeNode<?> node = (AbstractRootExplorerRootsTreeNode<?>)path.getLastPathComponent();

            if (node.isFsScanningRequired()) {
                node.removeChildren();

                boolean scanResult = false;
                AbstractPathEntity value = (AbstractPathEntity)node.getValue();
                if (value instanceof TvSeriePathEntity) {
                    TvSeriePathEntity tvSeriePathEntity = (TvSeriePathEntity)value;
                    scanResult = TvSerieManager.getInstance().scan(tvSeriePathEntity);
                    if (scanResult) {
                        AbstractFsExplorerRootsTreeNode.createAndAddFromFsElements(tvSeriePathEntity.getFsElements(), node);
                    }
                    else {
                        AbstractRootsExplorerRootsTreeNode<?> parent = (AbstractRootsExplorerRootsTreeNode<?>)node.getParent();
                        parent.remove(node);
                    }
                    this.model.reload(node);
                }
            }
        }
    }

    protected void notifyShowContextMenu(TreePath path, int x, int y) {
        if (path != null && path.getLastPathComponent() instanceof AbstractExplorerRootsTreeNode) {
            AbstractExplorerRootsTreeNode node = (AbstractExplorerRootsTreeNode)path.getLastPathComponent();

            if (node.hasContextMenu()) {
                AbstractExplorerRootsContextMenu<?> menu = node.getContextMenu(this);
                menu.show(this.view.getTree(), x, y);
            }
        }
    }

    private File showRootChooser(Type type, File lastRootParent) {
        File result = null;

        this.rootChooser.setTitle(String.format(Labels.getChooseRoot(type.getPlural())));
        this.rootChooser.setSelectedDirectory(lastRootParent);
        this.rootChooser.setVisible(true);

        if (this.rootChooser.getResult() == DialogOptions.OK_OPTION) {
            result = this.rootChooser.getSelectedDirectory();
        }

        return result;
    }

    private void storeSettings() {
        SettingsStorer storer = new SettingsStorer();
        IndeterminateProgressDialogWorker.show(storer, Labels.STORING_SETTINGS);
    }

}
