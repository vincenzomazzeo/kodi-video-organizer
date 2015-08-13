CREATE TABLE tv_serie_episode_guest_star (
	id					IDENTITY	NOT NULL,
	tv_serie_episode_id	UUID		NOT NULL,
	guest_star			VARCHAR		NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT tv_serie_episode_guest_star FOREIGN KEY (tv_serie_episode_id) REFERENCES tv_serie_episode(id) ON DELETE CASCADE
);
