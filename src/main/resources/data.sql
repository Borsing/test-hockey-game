insert into TEAM(id, coach, year) values
(123, 'Benjamin Robert', 2019),
(456, 'Vincent Heurtault', 2020),
(789, 'Remi Laot', 2021);

insert into PLAYER(number, name, last_Name, position, is_Captain, team_id) values
(11, 'Jean', 'Heinz', 'goaltender', false, 123),
(22, 'Maxime', 'Martin', 'forward', false, 123),
(33, 'Celine', 'Martin', 'defenseman', true, 123),
(44, 'Baptiste', 'Dupont', 'goaltender', false, 456),
(55, 'Benoit', 'Duquenoy', 'defenseman', false, 456),
(66, 'Simon', 'Mac', 'doaltender', true, 789),
(77, 'Auguste', 'Chirron', 'forward', false, 789);
