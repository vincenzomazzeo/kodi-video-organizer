CREATE TABLE tv_serie_episode_subtitle (
	id					IDENTITY	NOT NULL,
	tv_serie_episode_id	UUID		NOT NULL,
	filename			VARCHAR		NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_episode_subtitle FOREIGN KEY (tv_serie_episode_id) REFERENCES tv_serie_episode(id) ON DELETE CASCADE
);
