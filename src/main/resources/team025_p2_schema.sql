--DROP TABLE

DROP TABLE IF EXISTS Individual CASCADE;
DROP TABLE IF EXISTS Company CASCADE;
DROP TABLE IF EXISTS Municipality CASCADE;
DROP TABLE IF EXISTS Govt CASCADE;
DROP TABLE IF EXISTS HAS_COST CASCADE;
DROP TABLE IF EXISTS Cost CASCADE;
DROP TABLE IF EXISTS Has_ESF CASCADE;
DROP TABLE IF EXISTS ESF CASCADE;
DROP TABLE IF EXISTS Capability CASCADE;
DROP TABLE IF EXISTS Request CASCADE;
DROP TABLE IF EXISTS Incident_declaration CASCADE;
DROP TABLE IF EXISTS Is_Deployed_To CASCADE;
DROP TABLE IF EXISTS Incident CASCADE;
DROP TABLE IF EXISTS Resource CASCADE;
DROP TABLE IF EXISTS Users CASCADE;

DROP TYPE IF EXISTS usertype;
DROP TYPE IF EXISTS municategory;
DROP TYPE IF EXISTS declarations;

--CREATE TABLE
CREATE TYPE usertype AS ENUM
('individual', 'municipality', 'company', 'govt');

CREATE TABLE Users (
        name VARCHAR(30) NOT NULL,
        username VARCHAR(30) NOT NULL,
        password VARCHAR(30) NOT NULL,
        usertype usertype NOT NULL,
        PRIMARY KEY(username)
);

CREATE TABLE Individual (
        username VARCHAR(30) NOT NULL,
        jobtitle VARCHAR(30) NOT NULL,
        datehired DATE NOT NULL,
        PRIMARY KEY(username)
        );

CREATE TABLE Company(
            username VARCHAR(30) NOT NULL,
            hq VARCHAR(30) NOT NULL,
            employeecnt INTEGER NOT NULL,
            PRIMARY KEY(username)
        );

CREATE TYPE municategory as ENUM('city', 'county', 'state', 'country');

CREATE TABLE Municipality(
            username VARCHAR(30) NOT NULL,
            category municategory,
              PRIMARY KEY(username)
        );

CREATE TABLE Govt(
            username VARCHAR(30) NOT NULL,
            agencyoffice VARCHAR(40),
            PRIMARY KEY(username)
        );

CREATE TABLE Resource(
            resourceid SERIAL,
            username VARCHAR(30) NOT NULL,
            resourcename VARCHAR(30) NOT NULL,
            esfid SMALLINT NOT NULL,
            model VARCHAR(30),
            latitude FLOAT(8) NOT NULL,
            longitude FLOAT(8) NOT NULL,
            maxdist SMALLINT NULL,
            PRIMARY KEY(resourceid)
        );

CREATE TABLE Cost (
            value NUMERIC(8,2) NOT NULL,
            unit VARCHAR(15),
            costid SERIAL,
            PRIMARY KEY(costid)
);

CREATE TABLE HAS_COST(
            resourceid INTEGER NOT NULL,
            costid SERIAL,
            PRIMARY KEY(resourceid)
         );

CREATE TABLE Has_ESF(
            resourceid SERIAL,
            esfid SMALLINT NULL,
            PRIMARY KEY(resourceid, esfid)
);

CREATE TABLE ESF(
            esfid SMALLINT NOT NULL,
            esfdesc VARCHAR(60) NOT NULL,
            PRIMARY KEY(esfid)
);

CREATE TABLE Capability (
            resourceid  SERIAL,
            capability VARCHAR(30) NULL,
            PRIMARY KEY(resourceid, capability)
);

CREATE TABLE Request(
            username VARCHAR(30) NOT NULL,
            resourceid SERIAL,
        incidentid INTEGER,
        returndate DATE NOT NULL,
            UNIQUE(username, resourceid, incidentid)
               );

CREATE TABLE Incident(
            username VARCHAR(30) NOT NULL,
            uniqid VARCHAR (50) NOT NULL,
            incidentid SERIAL NOT NULL,
            incidentdate DATE NOT NULL,
            description VARCHAR(100),
            latitude FLOAT(8) NOT NULL,
            longitude FLOAT(8) NOT NULL,
                UNIQUE(uniqid),
            PRIMARY KEY(incidentid)
);

CREATE TYPE declarations as ENUM('MD','EM','FM','FS');

CREATE TABLE Incident_declaration (
declaration declarations,
uniqid VARCHAR(50),
incidentid SERIAL,
PRIMARY KEY(incidentid, uniqid)
);

CREATE TABLE Is_Deployed_To(
            resourceid  SERIAL,
            incidentid  SERIAL,
            returndate DATE NOT NULL,
            startdate DATE NOT NULL,
            deployeddate DATE,
            UNIQUE(incidentid, resourceid)
            );

-- ALTER statements
ALTER TABLE Individual
ADD CONSTRAINT fk_Users_username_Individual_username
        FOREIGN KEY(username)
                REFERENCES Users (username) ON DELETE CASCADE;

ALTER TABLE Company
ADD CONSTRAINT fk_Users_username_Company_username
FOREIGN KEY(username)
                REFERENCES Users (username) ON DELETE CASCADE;

ALTER TABLE Municipality
ADD CONSTRAINT fk_Users_username_Municipality_username
        FOREIGN KEY(username)
                REFERENCES Users (username) ON DELETE CASCADE;

ALTER TABLE Govt
ADD CONSTRAINT fk_Users_username_Govt_username
FOREIGN KEY(username)
                REFERENCES Users (username) ON DELETE CASCADE;

ALTER TABLE Resource
ADD CONSTRAINT fk_Users_username_Resource_username
            FOREIGN KEY(username)
                REFERENCES Users (username) ON DELETE CASCADE;

ALTER TABLE HAS_COST
ADD CONSTRAINT fk_Resource_resourceid_Has_cost_resourceid
FOREIGN KEY (resourceid)
            REFERENCES Resource(resourceid) ON DELETE CASCADE,
        ADD CONSTRAINT fk_Cost_Costid_Has_cost_cost
FOREIGN KEY (costid)
            REFERENCES Cost(costid) ON DELETE CASCADE;

ALTER TABLE Has_ESF
        ADD CONSTRAINT fk_Resource_Resourceid_Has_esf_resourceid
            FOREIGN KEY(resourceid)
                REFERENCES Resource (resourceid) ON DELETE CASCADE;

ALTER TABLE Capability
ADD CONSTRAINT fk_Resource_Resourceid_Capability
FOREIGN KEY(resourceid)
                REFERENCES Resource (resourceid) ON DELETE CASCADE;

ALTER TABLE Request
ADD CONSTRAINT fk_Users_username_Request_username
FOREIGN KEY(username)
                REFERENCES Users (username) ON DELETE CASCADE,
        ADD CONSTRAINT fk_Resource_Resourceid_Request_resourceid
FOREIGN KEY(resourceid)
                REFERENCES Resource (resourceid) ON DELETE CASCADE,
         ADD CONSTRAINT fk_Incident_Incidentid_Request_Incidentid
FOREIGN KEY(incidentid)
        REFERENCES Incident(incidentid) ON DELETE CASCADE;

ALTER TABLE Incident
        ADD CONSTRAINT fk_Incident_username_Users_username
            FOREIGN KEY(username)
                REFERENCES Users (username) ON DELETE CASCADE;

ALTER TABLE Incident_declaration
        ADD CONSTRAINT fk_IncidentDeclaration_uniqid_Incident_uniqid
FOREIGN KEY(uniqid)
            REFERENCES Incident(uniqid) ON DELETE CASCADE;

ALTER TABLE Is_Deployed_To
ADD CONSTRAINT fk_IsDeployed_resourceid_Resource_resourceid
            FOREIGN KEY(resourceid)
                REFERENCES Resource (resourceid) ON DELETE CASCADE,
ADD CONSTRAINT fk_Incident_incidentid_Icident_incidentid
FOREIGN KEY(incidentid)
                REFERENCES Incident(incidentid) ON DELETE CASCADE;