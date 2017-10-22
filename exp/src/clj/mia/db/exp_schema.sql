-- :name create-table-trial-res
-- :command :execute
-- :result :raw
-- :doc create table for results from an experimental trial
CREATE TABLE trial_res (
  id serial PRIMARY KEY,
  trial_id integer references trial (id) not null,
  min_val integer not null,
  max_val integer not null,
  final_val integer not null
);

-- :name drop-table-trial-res
-- :command :execute
-- :doc Drop trial_res table if exists
DROP TABLE IF EXISTS trial_res;

-- :name create-table-trial
-- :command :execute
-- :result :raw
-- :doc create table for a single trial
CREATE TABLE trial (
  id serial PRIMARY KEY,
  expone_id integer references exp (id) not null,
  seq_num integer not null
);

-- :name drop-table-trial
-- :command :execute
-- :doc Drop trial table if exists
DROP TABLE IF EXISTS trial;

-- :name create-table-exp
-- :command :execute
-- :result :raw
-- :doc create table for sandbox experiment
CREATE TABLE exp (
  id serial PRIMARY KEY,
  created_at timestamp not null default current_timestamp
);

-- :name drop-table-exp
-- :command :execute
-- :doc Drop exp table if exists
DROP TABLE IF EXISTS exp;
