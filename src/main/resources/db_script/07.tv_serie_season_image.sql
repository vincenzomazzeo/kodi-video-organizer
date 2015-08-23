CREATE TABLE tv_serie_season_image (
	id					UUID			NOT NULL,
	tv_serie_season_id	UUID			NOT NULL,
	provider			VARCHAR(10)		NOT NULL,
	path				VARCHAR			NOT NULL,
	rating				VARCHAR(4)				,
	rating_count		VARCHAR					,
	language			VARCHAR(2)		NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_season_image_fk FOREIGN KEY (tv_serie_season_id) REFERENCES tv_serie_season(id) ON DELETE CASCADE
);
