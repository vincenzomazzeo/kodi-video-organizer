CREATE TABLE tv_serie_season (
	id					UUID			NOT NULL,
	tv_serie_id			UUID			NOT NULL,
	number				INT(2)			NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_season FOREIGN KEY (tv_serie_id) REFERENCES tv_serie(id) ON DELETE CASCADE
);
