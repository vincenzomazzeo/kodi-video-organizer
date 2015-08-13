CREATE TABLE tv_serie_episode_writer (
	id					IDENTITY	NOT NULL,
	tv_serie_episode_id	UUID		NOT NULL,
	writer				VARCHAR		NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_episode_writer FOREIGN KEY (tv_serie_episode_id) REFERENCES tv_serie_episode(id) ON DELETE CASCADE
);
