package it.ninjatech.kvo.tvserie.model;

import it.ninjatech.kvo.util.PeopleManager;

import java.util.UUID;

public class TvSerieActor implements Comparable<TvSerieActor> {

	private final String id;
	private final String name;
	private final String role;
	private final String imagePath;
	private final Integer sortOrder;
	
	protected TvSerieActor(String name, String role, String imagePath, Integer sortOrder) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.role = role;
		this.imagePath = imagePath;
		this.sortOrder = sortOrder;
		
		PeopleManager.getInstance().addTvSerieActor(this);
	}
	
	@Override
    public String toString() {
        return String.format("[%s] %s", this.id, this.name);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
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
		TvSerieActor other = (TvSerieActor)obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		}
		else if (!this.id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(TvSerieActor other) {
		int result = 0;
		
		if (this.sortOrder.equals(other.sortOrder)) {
			if (this.name.equals(other.name)) {
				if (this.role.equals(other.role)) {
					result = -1;
				}
				else {
					result = this.role.compareTo(other.role);
				}
			}
			else {
				result = this.name.compareTo(other.name);
			}
		}
		else {
			result = this.sortOrder.compareTo(other.sortOrder);
		}
		
		return result;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getRole() {
		return this.role;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
}
