CREATE TABLE mileage_waybill
(
	mileage_waybill_id serial PRIMARY KEY,
	vehicle_id bigint,
	convoy_number integer,
	transport_column varchar(128),
	vehicle_modification varchar(128),
	vehicle_number varchar(128),
	driver_name varchar(128),
	waybill_number bigint,
	departure_date date,
	return_date date,
	mileage double precision,
	planned_route text,
	actual_route text
);

CREATE TABLE fuel_waybill
(
	fuel_waybill_id serial PRIMARY KEY,
	waybill_number bigint,
	fuel_grade varchar(128),
	fuel_start double precision,
	fuel_end double precision,
	consumption_rate double precision,
	fueling_own_weight double precision,
	fueling_branch_weight double precision,
	fueling_own_volume double precision,
	fueling_branch_volume double precision,
	fueling_outside_volume double precision,
	fuel_consumption double precision,
	fuel_total double precision,
	convoy_number integer
);

CREATE TABLE comparative_report(
    id serial PRIMARY KEY,
	fuel_waybill_id int REFERENCES fuel_waybill (fuel_waybill_id) ON DELETE CASCADE,
	mileage_waybill_id int REFERENCES mileage_waybill (mileage_waybill_id) ON DELETE CASCADE,
    max_speed integer,
    average_speed integer,
    mileage double precision,
    fuel_start double precision,
    fuel_end double precision,
    refill_total double precision,
    mileage_difference double precision,
    fuel_difference_start double precision,
    fuel_difference_end double precision,
    refill_difference double precision,
    consumption_total double precision,
    consumption_difference double precision,
    fuel_deviation_reason varchar(128),
    fuel_deviation_action varchar(128),
    mileage_deviation_reason varchar(128),
    mileage_deviation_action varchar(128),
    note text,
    convoy_number integer
);

CREATE TABLE fuel_glonass
(
	fuel_glonass_id serial PRIMARY KEY,
	vehicle_number varchar(128),
	trip_date date,
	fuel_start double precision,
	fuel_end double precision,
	refill_total double precision,
	convoy_number integer,
	comparative_report_id int REFERENCES comparative_report (id) ON DELETE CASCADE
);

CREATE TABLE mileage_glonass
(
	mileage_glonass_id serial PRIMARY KEY,
	trip_date date,
	vehicle_number varchar(128),
	max_speed integer,
	average_speed integer,
	mileage double precision,
	convoy_number integer,
	comparative_report_id int REFERENCES comparative_report (id) ON DELETE CASCADE
);
