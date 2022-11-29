SELECT 
    airdatameasurement.airdata.id,
    airdatameasurement.airdata.temperature,
    airdatameasurement.locations.name
FROM
    airdatameasurement.airdata
        INNER JOIN
    airdatameasurement.locations ON airdatameasurement.airdata.location_id_id = airdatameasurement.locations.id
GROUP BY airdatameasurement.airdata.id
ORDER BY airdatameasurement.airdata.id