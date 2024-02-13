select * from weather_stations;

select *from temperature_models
order by stationId, date;


SELECT *
FROM temperature_models
WHERE stationId in (Select id from weather_stations)
order by stationId, date asc;