CREATE TABLE tv_serie_episode_director (
	id					IDENTITY	NOT NULL,
	tv_serie_episode_id	UUID		NOT NULL,
	director			VARCHAR		NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_episode_director FOREIGN KEY (tv_serie_episode_id) REFERENCES tv_serie_episode(id) ON DELETE CASCADE
);
