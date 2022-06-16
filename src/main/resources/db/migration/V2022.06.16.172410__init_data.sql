-- password is admin
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `phone`, `email`, `avatar`, `enabled`, `locked`, `created_by`, `modified_by`)
VALUES
	(1, 'admin', 'admin', '{bcrypt}$2y$10$R9IEm15vmg02zDPiPToZ9uTOSvfCaugrW7uwbPuV3ZsSepbdiqgiq', NULL, NULL, NULL, 1, 0, NULL, NULL),
	(2, 'ly', 'ly', '{bcrypt}$2y$10$R9IEm15vmg02zDPiPToZ9uTOSvfCaugrW7uwbPuV3ZsSepbdiqgiq', NULL, NULL, NULL, 1, 0, NULL, NULL);

INSERT INTO `role` (`id`, `name`, `code`)
VALUES
	(1, 'user', 'user');

INSERT INTO `user_role` (`id`, `user_id`, `role_id`)
VALUES
	(1, 1, 1),
	(2, 2, 1);
