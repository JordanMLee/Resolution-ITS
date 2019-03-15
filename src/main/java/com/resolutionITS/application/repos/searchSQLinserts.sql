INSERT INTO ESF VALUES (1, 'Transportation'),(2 ,'Communications'),(3 ,'Public Works and Engineering'),(4 ,'Firefighting'),(5, 'Emergency Management'),(6 ,'Mass Care, Emergency Assistance,Housing, and Human Services'),(7 ,'Logistics Management and Resource Support'),(8 ,'Public Health and Medical Services'),(9, 'Search and Rescue'),(10, 'Oil and Hazardous Materials Response'),(11, 'Agriculture and Natural Resources'),(12, 'Energy'),(13, 'Public Safety and Security'),(14, 'Long-Term Community Recovery'),(15, 'External Affairs');

INSERT INTO RESOURCE VALUES(123454, 'jmlee1', 'truck', 1, 'f-250', 23.4673, 132.3423, 24),(123455, 'jmlee1', 'truck', 2, 'f-250', 23.4673, 132.3423, 24), (123456, 'jmlee1', 'truck', 3, 'f-150', 23.4673, 132.3423, 24);

INSERT INTO HAS_ESF VALUES(123454,1),(123455,2),(123456,3);

INSERT INTO COST VALUES(2000,'each,',12),(3000,'each',13),(4000,'each',15);

INSERT INTO HAS_COST VALUES (123454,12),(123455,13),(123456,15);

SELECT r.resourceid, r.resourcename, u.name as ownername, c.value as cost, c.unit as unit, exists(select 1 from IS_DEPLOYED_TO as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid WHERE (LOWER(r.resourcename) LIKE LOWER('truck') OR LOWER(r.model) LIKE LOWER('truck') OR r.resourceid IN (SELECT resourceid FROM capability WHERE LOWER(capability) LIKE LOWER('truck')));

SELECT r.resourceid, r.resourcename, u.name as ownername, c.value as cost, c.unit as unit, exists(select 1 from IS_DEPLOYED_TO as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid WHERE (LOWER(r.resourcename) LIKE LOWER('truk') OR LOWER(r.model) LIKE LOWER('truk') OR r.resourceid IN (SELECT resourceid FROM capability WHERE LOWER(capability) LIKE LOWER('truk')));

SELECT r.resourceid, r.resourcename, u.name as ownername, c.value as cost, c.unit as unit, exists(select 1 from IS_DEPLOYED_TO as d where r.resourceid = d.resourceid) as in_use, i.returndate as returndate FROM RESOURCE r INNER JOIN USERS u on r.username = u.username INNER JOIN HAS_COST hc on r.resourceid = hc.resourceid INNER JOIN COST c on hc.costid = c.costid LEFT JOIN IS_DEPLOYED_TO i on r.resourceid = i.resourceid WHERE (r.esfid = 2 OR r.resourceid in (SELECT resourceid FROM has_esf WHERE esfid = 2));

--resource status sql
--getResourcesInUse
SELECT d.resourceid, r.resourcename, i.description,r.username, d.startdate, d.returndate FROM is_deployed_to d INNER JOIN resource r ON d.resourceid = r.resourceid INNER JOIN incident i ON d.incidentid = i.incidentid WHERE i.username =
                                                                                                                                                                                                                               insert into incident values ('jmeanor',2,123,'2018-07-18','fire',21.64,75.34);
insert into incident values ('jmlee1',2,123,'2018-07-18','fire',21.64,75.34);
insert into is_deployed_to values (123454,123,'2018-12-01','2018-07-18','2018-07-18');

insert into resource values (123,'jmlee1','myfirstresource',5,'ff',12.0,12.0,12);