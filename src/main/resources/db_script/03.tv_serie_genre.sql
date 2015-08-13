CREATE TABLE tv_serie_genre (
	id					IDENTITY		NOT NULL,
	tv_serie_id			UUID			NOT NULL,
	genre				VARCHAR			NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_genre_fk FOREIGN KEY (tv_serie_id) REFERENCES tv_serie(id) ON DELETE CASCADE
);
