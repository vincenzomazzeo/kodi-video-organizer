CREATE TABLE tv_serie_episode (
	id					UUID			NOT NULL,
	tv_serie_season_id	UUID			NOT NULL,
	provider_id			VARCHAR(10)		NOT NULL,
	number				INT(4)			NOT NULL,
	language			VARCHAR(2)		NOT NULL,
	filename			VARCHAR					,
	dvd_number			DECIMAL(4,2)			,
	name				VARCHAR					,
	first_aired			DATE					,
	imdb_id				VARCHAR					,
	overview			CLOB					,
	rating				DECIMAL(3,1)			,
	rating_count		INT						,
	artwork				VARCHAR					,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_episode_fk FOREIGN KEY (tv_serie_season_id) REFERENCES tv_serie_season(id) ON DELETE CASCADE
);
