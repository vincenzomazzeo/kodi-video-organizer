package it.ninjatech.kvo.model;

import java.io.File;
import java.util.UUID;

public abstract class Root {

	private final String id;
	private final String path;
	private final String label;
	
	protected Root(String id, String path, String label) {
		this.id = id;
		this.path = path;
		this.label = label;
	}
	
	protected Root(File file) {
		this.id = UUID.randomUUID().toString();
		this.path = file.getAbsolutePath();
		this.label = file.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Root other = (Root)obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public String getLabel() {
		return label;
	}
	
}
