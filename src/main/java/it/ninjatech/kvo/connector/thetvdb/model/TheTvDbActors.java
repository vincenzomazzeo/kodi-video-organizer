package it.ninjatech.kvo.connector.thetvdb.model;

import it.ninjatech.kvo.tvserie.model.TvSerie;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Actors")
public class TheTvDbActors {

    @XmlElement(name = "Actor")
    private List<TheTvDbActor> actors;

    protected TheTvDbActors() {
    }

    public void fill(TvSerie tvSerie) {
        if (this.actors != null) {
            for (TheTvDbActor actor : this.actors) {
                actor.fill(tvSerie);
            }
        }
    }

    protected List<TheTvDbActor> getActors() {
        return this.actors;
    }

    protected void setActors(List<TheTvDbActor> actors) {
        this.actors = actors;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    protected static class TheTvDbActor {

        @XmlElement(name = "Image")
        private String imagePath;
        @XmlElement(name = "Name")
        private String realName;
        @XmlElement(name = "Role")
        private String roleName;
        @XmlElement(name = "SortOrder")
        private Integer sortOrder;

        protected TheTvDbActor() {
        }

        private void fill(TvSerie tvSerie) {
            tvSerie.addActor(this.realName, this.roleName, this.imagePath, this.sortOrder);
        }

        protected String getImagePath() {
            return this.imagePath;
        }

        protected void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        protected String getRealName() {
            return this.realName;
        }

        protected void setRealName(String realName) {
            this.realName = realName;
        }

        protected String getRoleName() {
            return this.roleName;
        }

        protected void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        protected Integer getSortOrder() {
            return this.sortOrder;
        }

        protected void setSortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
        }

    }

}
