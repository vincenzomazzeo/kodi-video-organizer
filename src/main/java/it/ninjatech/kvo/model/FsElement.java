package it.ninjatech.kvo.model;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class FsElement implements Comparable<FsElement> {

	private final String name;
	private final Boolean directory;
	private final SortedSet<FsElement> children;

	public FsElement(String name, Boolean directory) {
		this.name = name;
		this.directory = directory;
		this.children = new TreeSet<>();
	}

	@Override
    public String toString() {
        return String.format("[%s] %s", this.directory ? "d" : " ", this.name);
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FsElement other = (FsElement)obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		}
		else if (!this.name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(FsElement other) {
		int result = 0;
		
		if (this.directory && other.directory) {
			result = this.name.compareTo(other.name);
		}
		else {
			result = this.directory ? -1 : 1; 
		}
		
		return result;
	}

	public String getName() {
		return this.name;
	}

	public Boolean getDirectory() {
		return this.directory;
	}
	
	public void addChild(FsElement child) {
		this.children.add(child);
	}
	
	public Set<FsElement> getChildren() {
		return Collections.unmodifiableSortedSet(this.children);
	}
	
}
