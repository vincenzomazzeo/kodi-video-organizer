package it.ninjatech.kvo.ui.explorer;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

public class ExplorerRootsItemTreeNode implements TreeNode, Comparable<ExplorerRootsItemTreeNode> {

	private final ExplorerRootsItemTreeNode parent;
	private final String label;
	private final Path value;
	private final List<ExplorerRootsItemTreeNode> children;

	@SuppressWarnings("unchecked")
	protected ExplorerRootsItemTreeNode(ExplorerRootsItemTreeNode parent, String label, Path value) {
		this.parent = parent;
		this.label = label;
		this.value = value;

		if (this.value == null || this.value.toFile().isDirectory()) {
			this.children = new ArrayList<>();

			if (this.value != null) {
				for (File file : value.toFile().listFiles()) {
//					file = new File(Utils.normalizeUnicode(file.getAbsolutePath()));
					if (!file.isHidden()) {
						this.children.add(new ExplorerRootsItemTreeNode(this, file.getName(), file.toPath()));
					}
				}
			}
			
			Collections.sort(this.children);
		}
		else {
			this.children = Collections.EMPTY_LIST;
		}
	}
	
	@Override
	public String toString() {
		return this.label;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return this.children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return this.children.size();
	}

	@Override
	public TreeNode getParent() {
		return this.parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		int result = 0;

		for (int i = 0, n = this.children.size(); i < n; i++) {
			if (((ExplorerRootsItemTreeNode)node).value.equals(this.children.get(i).value)) {
				result = i;
				break;
			}
		}

		return result;
	}

	@Override
	public boolean getAllowsChildren() {
		return this.value == null || this.value.toFile().isDirectory();
	}

	@Override
	public boolean isLeaf() {
		return this.value != null && this.value.toFile().isFile();
	}

	@Override
	public Enumeration<?> children() {
		return this.children();
	}

	@Override
	public int compareTo(ExplorerRootsItemTreeNode other) {
		int result = 0;
		
		boolean selfIsDirectory = this.value != null ? this.value.toFile().isDirectory() : true;
		boolean otherIsDirectory = other.value != null ? other.value.toFile().isDirectory() : true;
		
		if (selfIsDirectory == otherIsDirectory) {
			result = this.label.compareToIgnoreCase(other.label);
		}
		else if (selfIsDirectory) {
			result = -1;
		}
		else {
			result = 1;
		}
		
		return result;
	}
	
	
	protected void add(ExplorerRootsItemTreeNode node) {
		this.children.add(node);
		
		Collections.sort(this.children);
	}
}
