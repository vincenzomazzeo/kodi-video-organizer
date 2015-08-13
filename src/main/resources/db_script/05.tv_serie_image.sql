CREATE TABLE tv_serie_image (
	id					UUID			NOT NULL,
	tv_serie_id			UUID			NOT NULL,
	provider			VARCHAR(10)		NOT NULL,
	fanart				VARCHAR(10)		NOT NULL,
	path				VARCHAR			NOT NULL,
	rating				DECIMAL(3,1)			,
	rating_count		VARCHAR					,
	language			VARCHAR(2)		NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_image_fk FOREIGN KEY (tv_serie_id) REFERENCES tv_serie(id) ON DELETE CASCADE
);
