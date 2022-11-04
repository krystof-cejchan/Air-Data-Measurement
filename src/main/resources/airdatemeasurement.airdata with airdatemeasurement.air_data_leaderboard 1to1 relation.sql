SELECT 
    airdatameasurement.air_data_leaderboard.*,
    airdatameasurement.airdata.*
FROM
    airdatameasurement.air_data_leaderboard
        INNER JOIN
    airdatameasurement.airdata
WHERE
    airdatameasurement.air_data_leaderboard.air_data_id_id = airdatameasurement.airdata.id;