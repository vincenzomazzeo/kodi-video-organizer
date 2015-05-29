package it.ninjatech.kvo.model;

import java.io.File;
import java.util.UUID;

public abstract class AbstractPathEntity {

	private final String id;
	private final String path;
	private final String label;
	
	protected AbstractPathEntity(String id, String path, String label) {
		this.id = id;
		this.path = path;
		this.label = label;
	}
	
	protected AbstractPathEntity(File file) {
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
		AbstractPathEntity other = (AbstractPathEntity)obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return this.id;
	}

	public String getPath() {
		return this.path;
	}

	public String getLabel() {
		return this.label;
	}
	
}