-- :name add-exp
-- :command :returning-execute
-- :result :raw
-- :doc insert a row for a new experiment
INSERT INTO exp DEFAULT VALUES RETURNING id;

-- :name add-trial
-- :command :returning-execute
-- :result :raw
-- :doc insert a row for single trial
INSERT INTO trial (exp_id, seq_num)
VALUES (:exp-id, :seq-num)
RETURNING id;

-- :name add-trial-res
-- :command :returning-execute
-- :result :raw
-- :doc a the results for a single trial
INSERT INTO trial_res
(trial_id, min_val, max_val, final_val)
VALUES (:trial-id, :min-val, :max-val, :final-val)
RETURNING id;

-- :name get-exp :? :*
SELECT trial_id,
       id as trial_res_id,
       seq_num as trial_seq_num,
       min_val, max_val, final_val
FROM (SELECT trial.id as exp_trial_id,
             trial.seq_num as seq_num
     FROM exp JOIN trial
     ON exp.id = exp_id
     WHERE exp.id = :exp-id) as exp_trial
     JOIN trial_res
     ON exp_trial_id = trial_id;
