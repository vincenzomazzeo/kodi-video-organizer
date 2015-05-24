package it.ninjatech.kvo.ui.explorer;

import it.ninjatech.kvo.model.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

public class ExplorerRootsItemTreeNode implements TreeNode, Comparable<ExplorerRootsItemTreeNode> {

	private ExplorerRootsItemTreeNode parent;
	private Integer level;
	private final Type type;
	private final String label;
	private final File value;
	private final List<ExplorerRootsItemTreeNode> children;

	protected ExplorerRootsItemTreeNode(String label) {
		this.parent = null;
		this.level = 0;
		this.type = null;
		this.label = label;
		this.value = null;
		this.children = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	protected ExplorerRootsItemTreeNode(File value, Integer level, Type type) {
		this.parent = null;
		this.level = level;
		this.type = type;
		this.label = value.getName();
		this.value = value;
		this.children = this.value.isDirectory() ? new ArrayList<>() : Collections.EMPTY_LIST;
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
		return this.value == null || this.value.isDirectory();
	}

	@Override
	public boolean isLeaf() {
		return this.value != null && this.value.isFile();
	}

	@Override
	public Enumeration<?> children() {
		return this.children();
	}

	@Override
	public int compareTo(ExplorerRootsItemTreeNode other) {
		int result = 0;
		
		boolean selfIsDirectory = this.value != null ? this.value.isDirectory() : true;
		boolean otherIsDirectory = other.value != null ? other.value.isDirectory() : true;
		
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
		node.parent = this;
		this.children.add(node);
	}
	
	protected void sortChildren() {
		Collections.sort(this.children);
	}
	
	protected File getValue() {
		return this.value;
	}
	
	protected Integer getLevel() {
		return this.level;
	}
	
	protected Type getType() {
		return this.type;
	}
	
}
