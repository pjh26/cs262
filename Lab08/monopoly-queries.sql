
-- Exercise 8.1
-- a.)
  SELECT *
    FROM Game
ORDER BY time ASC
  ;

-- b.)
SELECT *
  FROM Game
 WHERE TO_DAYS(TIMEDIFF(NOW(), time)) < 7
;

-- c.)
SELECT *
  FROM Player
 WHERE name IS NOT Null
;

-- d.)
SELECT playerID
  FROM PlayerGame
 WHERE score > 2000
;

-- e.)
SELECT *
  FROM Player
 WHERE emailAddress LIKE '%gmail%'
;

----------------------------------------------------

-- Exercise 8.2
-- a.)
  SELECT score
    FROM Player, PlayerGame
   WHERE playerID = Player.ID
     AND Player.name =  'The King'
ORDER BY score ASC
;

-- b.)
  SELECT Player.name
    FROM Player, PlayerGame, Game
   WHERE Game.time = '2006-06-28 13:20:00'
     AND gameID = Game.ID
     AND playerID = Player.ID
ORDER BY score DESC
LIMIT 1
;

-- c.)
-- The P1.ID < P2.ID clause ensures that for every name that is repeated, the name is only returned half as much. So that repeated names are not returned every single time that the name appears.

-- d.)
-- Joining a table to itself could be useful when searching for repeated data, or when you need to compare a dataset against itself.









