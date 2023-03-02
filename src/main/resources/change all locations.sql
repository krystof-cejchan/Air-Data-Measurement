UPDATE airdata 
SET 
    airdata.location_id_id = 5
WHERE
    airdata.id > - 1;

UPDATE airdatameasurement.air_data_leaderboard 
SET 
    airdatameasurement.air_data_leaderboard.location_id_id = 5
WHERE
    airdatameasurement.air_data_leaderboard.id > - 1;


UPDATE airdatameasurement.air_data_average_of_day 
SET 
    airdatameasurement.air_data_average_of_day.location_id = 5
WHERE
    airdatameasurement.air_data_average_of_day.id > - 1;
