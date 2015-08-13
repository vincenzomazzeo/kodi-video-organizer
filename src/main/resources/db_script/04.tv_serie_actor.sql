CREATE TABLE tv_serie_actor (
	id					UUID			NOT NULL,
	tv_serie_id			UUID			NOT NULL,
	name				VARCHAR			NOT NULL,
	role				VARCHAR			NOT NULL,
	image_path			VARCHAR			NOT NULL,
	sort_order			INT(1)			NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_actor_fk FOREIGN KEY (tv_serie_id) REFERENCES tv_serie(id) ON DELETE CASCADE
);
