-- With all that said, here are the details for what you will need for the demo:

-- All ESFs provided in the spec should be loaded into your database.
-- You should have one user for each user type. You are free to name users anything of your choosing and set their attributes to anything that would make sense for that user type.
-- Have 4 technicians for each user (16 total):
-- One of those 16 technicians should be named “Leo’s Ladder” and other attributes set to anything of your choosing.
-- One of those 16 technicians should have the word “Ladder” in the model and the name “Will’s Little Giant” and other attributes set to anything of your choosing.
-- One of those 16 should have the word “Ladder” in its capabilities and the name “Peter’s Fire Truck” and other attributes set to anything of your choosing.
-- The other 13 technicians can be created with attributes of your choosing, ensuring that there is at least one technician for each Skill in the database.  (Since there are 16 technicians we are asking for, and 15 ESFs, you are going to have at least one Skill with two technicians.)
-- Have two of each issue type already populated in the database.  These incidents can have attributes of your choosing.  (You could have all of one issue type assigned to each user, but the distribution of these eight issue types is entirely up to you.)
-- One technician from each user in your database should be in use until 7/30/2018, and must have been requested by a different user.
-- One technician request for each user in your database should be pending.

DELETE FROM ESF;
INSERT INTO ESF
VALUES (1, 'Transportation'),
       (2, 'Communications'),
       (3, 'Public Works and Engineering'),
       (4, 'Firefighting'),
       (5, 'Emergency Management'),
       (6, 'Mass Care, Emergency Assistance,Housing, and Human Services'),
       (7, 'Logistics Management and technician Support'),
       (8, 'Public Health and Medical Services'),
       (9, 'Search and Rescue'),
       (10, 'Oil and Hazardous Materials Response'),
       (11, 'Agriculture and Natural Resources'),
       (12, 'Energy'),
       (13, 'Public Safety and Security'),
       (14, 'Long-Term Community Recovery'),
       (15, 'External Affairs');

DELETE FROM USERS;
INSERT INTO USERS VALUES('John','jmeanor','password','individual'),('Home Depot','homedepot','password','company'),('FEMA','fema','password','govt'),('Orlando Public Works','orlando','password','municipality');
INSERT INTO INDIVIDUAL VALUES ('jmeanor','manager','2014-01-01');
INSERT INTO COMPANY VALUES ('homedepot','Washington DC',10000);
INSERT INTO GOVT VALUES ('fema','DCHQ');
INSERT INTO MUNICIPALITY VALUES ('orlando','city');

DELETE FROM RESOURCE;
INSERT INTO RESOURCE VALUES(1, 'jmeanor', 'truck', 1, 'f-250', 39.49, -113.30, 300),(2, 'jmeanor', 'car', 2, 'mazda 5', 37.68, -117.84, 500), (3, 'jmeanor', 'Leo''s Ladder', 4, 'Ladder', 23.46, 132.34, 0),(4,'jmeanor','plane',3,'cessna-152',37.710990, -122.21,1000);
INSERT INTO RESOURCE VALUES(5, 'homedepot', 'truck', 5, 'f-250', 40.00, -90.30, 250),(6, 'homedepot', 'Will''s Little Giant', 1, 'mazda 5', 37.68, -117.84, 500), (7, 'homedepot', 'Leo''s Ladder', 7, 'Ladder', 23.46, 132.34, 0),(8,'homedepot','plane',1,'cessna-152',37.710990, -122.21,1000);
INSERT INTO RESOURCE VALUES(9, 'fema', 'van', 12, 'type-a', 23.4673, 132.3423, 24),(10, 'fema', 'construction barrier', 11, 'heavy duty', 23.4673, 132.3423, 1), (11, 'fema', 'truck', 9, 'f-150', 23.4673, 132.3423, 24),(12, 'fema', 'truck', 13, 'f-150', 23.4673, 132.3423, 24);
INSERT INTO RESOURCE VALUES(13, 'orlando', 'Peter''s Fire Truck', 10, 'long ladder', 23.4673, 132.3423, 24),(14, 'fema', 'construction barrier', 7, 'heavy duty', 23.4673, 132.3423, 1), (15, 'fema', 'truck', 6, 'f-150', 23.4673, 132.3423, 24),(16, 'orlando', 'Peter''s Fire Truck', 10, 'long ladder', 23.4673, 132.3423, 24);

DELETE FROM INCIDENT;

INSERT INTO INCIDENT VALUES ('jmeanor','FM-1',1,'2018-03-12','massive flood', 12.54,-34.56),('jmeanor','FM-2',2,'2018-07-18','fire',21.64,75.34);
INSERT INTO INCIDENT VALUES ('homedepot','EM-1',3,'2018-01-11','LA dam breach',39.50,-114.00),('homedepot','EM-2',4,'2018-04-05','Mudslide',56.23,168.03);
INSERT INTO INCIDENT VALUES ('fema','MD-1',5,'2018-01-01','major fire and flood',72.12,-83.33),('fema','MD-2',6,'2018-02-02','tornado',56.23,145.11);
INSERT INTO INCIDENT VALUES ('orlando','FS-1',7,'2018-02-03','power outage',55.55,155.55),('orlando','FS-2',8,'2017-01-01','castrophe',44.44,144.44);

DELETE FROM INCIDENT_DECLARATION;
INSERT INTO INCIDENT_DECLARATION VALUES ('FM','FM-1',1),('FM','FM-2',2),('EM','EM-1',3),('EM','EM-2',4),('MD','MD-1',5),('MD','MD-2',6),('FS','FS-1',7),('FS','FS-2',8);

DELETE FROM REQUEST;
INSERT INTO REQUEST VALUES ('jmeanor',5,1,'2018-07-30'),('homedepot',1,5,'2018-07-30');
INSERT INTO REQUEST VALUES ('fema',13,5,'2018-07-30'),('orlando',11,8,'2018-07-30');


INSERT INTO COST VALUES(2000,'each,',10),(3000,'each',11),(4000,'each',12);
INSERT INTO HAS_COST VALUES (1,10),(2,12),(3,11),(4,12),(5,10),(6,10),(7,11),(8,12),(9,11),(10,11),(11,11),(12,12),(13,10),(14,11),(15,12),(16,11);

SELECT r.resourceid, r.resourcename, r.latitude, r.longitude, r.esfid, r.model, r.maxdist, u.name, c.value, c.unit, exists(select 1 from IS_DEPLOYED_TO as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid;




/*
INSERT INTO HAS_ESF VALUES(123454,1),(123455,2),(123456,3);

INSERT INTO COST VALUES(2000,'each,',12),(3000,'each',13),(4000,'each',15);

INSERT INTO HAS_COST VALUES (123454,12),(123455,13),(123456,15);

SELECT r.resourceid, r.resourcename, u.name as ownername, c.value as time, c.unit as unit, exists(select 1 from IS_DEPLOYED_TO as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid WHERE (LOWER(r.resourcename) LIKE LOWER('truck') OR LOWER(r.model) LIKE LOWER('truck') OR r.resourceid IN (SELECT resourceid FROM capability WHERE LOWER(capability) LIKE LOWER('truck')));

SELECT r.resourceid, r.resourcename, u.name as ownername, c.value as time, c.unit as unit, exists(select 1 from IS_DEPLOYED_TO as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid WHERE (LOWER(r.resourcename) LIKE LOWER('truk') OR LOWER(r.model) LIKE LOWER('truk') OR r.resourceid IN (SELECT resourceid FROM capability WHERE LOWER(capability) LIKE LOWER('truk')));

SELECT r.resourceid, r.resourcename, u.name as ownername, c.value as time, c.unit as unit, exists(select 1 from IS_DEPLOYED_TO as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid WHERE (r.esfid = 2 OR r.resourceid in (SELECT resourceid FROM has_esf WHERE esfid = 2));

SELECT uniqid FROM incident_declaration WHERE incidentid = (SELECT MAX (incidentid) FROM incident_declaration WHERE declaration = 'EM');
--technician status sql
--getResourcesInUse
SELECT d.resourceid, r.resourcename, i.description,r.username, d.startdate, d.returndate FROM is_deployed_to d INNER JOIN technician r ON d.resourceid = r.resourceid INNER JOIN issue i ON d.incidentid = i.incidentid WHERE i.username =
                                                                                                                                                                                                                               insert into issue values ('jmeanor',2,123,'2018-07-18','fire',21.64,75.34);
insert into issue values ('jmeanor',2,123,'2018-07-18','fire',21.64,75.34);
insert into is_deployed_to values (123454,123,'2018-12-01','2018-07-18','2018-07-18');

