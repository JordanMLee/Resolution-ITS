--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6 (Ubuntu 10.6-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.6 (Ubuntu 10.6-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: types; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.types AS ENUM (
  'SW',
  'WS',
  'PR',
  'NT'
  );


ALTER TYPE public.types OWNER TO postgres;

--
-- Name: usertype; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.usertype AS ENUM (
  'user',
  'tier2',
  'tier1',
  'tier3'
  );


ALTER TYPE public.usertype OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: capability; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.capability
(
  technicianid integer NOT NULL,
  capability   character varying(30)
);


ALTER TABLE public.capability
  OWNER TO postgres;

--
-- Name: capability_technicianid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.capability_technicianid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.capability_technicianid_seq
  OWNER TO postgres;

--
-- Name: capability_technicianid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.capability_technicianid_seq OWNED BY public.capability.technicianid;


--
-- Name: has_skill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.has_skill
(
  technicianid integer  NOT NULL,
  skillid      smallint NOT NULL
);


ALTER TABLE public.has_skill
  OWNER TO postgres;

--
-- Name: has_skill_technicianid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.has_skill_technicianid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.has_skill_technicianid_seq
  OWNER TO postgres;

--
-- Name: has_skill_technicianid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.has_skill_technicianid_seq OWNED BY public.has_skill.technicianid;


--
-- Name: has_time; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.has_time
(
  technicianid integer NOT NULL,
  timeid       integer NOT NULL
);


ALTER TABLE public.has_time
  OWNER TO postgres;

--
-- Name: has_time_timeid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.has_time_timeid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.has_time_timeid_seq
  OWNER TO postgres;

--
-- Name: has_time_timeid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.has_time_timeid_seq OWNED BY public.has_time.timeid;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.hibernate_sequence
  OWNER TO postgres;

--
-- Name: is_deployed_to; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.is_deployed_to
(
  technicianid integer NOT NULL,
  issueid      integer NOT NULL,
  returndate   date    NOT NULL,
  startdate    date    NOT NULL,
  deployeddate date
);


ALTER TABLE public.is_deployed_to
  OWNER TO postgres;

--
-- Name: is_deployed_to_issueid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.is_deployed_to_issueid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.is_deployed_to_issueid_seq
  OWNER TO postgres;

--
-- Name: is_deployed_to_issueid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.is_deployed_to_issueid_seq OWNED BY public.is_deployed_to.issueid;


--
-- Name: is_deployed_to_technicianid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.is_deployed_to_technicianid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.is_deployed_to_technicianid_seq
  OWNER TO postgres;

--
-- Name: is_deployed_to_technicianid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.is_deployed_to_technicianid_seq OWNED BY public.is_deployed_to.technicianid;


--
-- Name: issue; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.issue
(
  username    character varying(30) NOT NULL,
  uniqid      integer               NOT NULL,
  issueid     integer               NOT NULL,
  issuedate   date                  NOT NULL,
  description character varying(100)
);


ALTER TABLE public.issue
  OWNER TO postgres;

--
-- Name: issue_issueid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.issue_issueid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.issue_issueid_seq
  OWNER TO postgres;

--
-- Name: issue_issueid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.issue_issueid_seq OWNED BY public.issue.issueid;


--
-- Name: issue_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.issue_type
(
  type    public.types,
  uniqid  integer NOT NULL,
  issueid integer NOT NULL
);


ALTER TABLE public.issue_type
  OWNER TO postgres;

--
-- Name: issue_type_issueid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.issue_type_issueid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.issue_type_issueid_seq
  OWNER TO postgres;

--
-- Name: issue_type_issueid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.issue_type_issueid_seq OWNED BY public.issue_type.issueid;


--
-- Name: issue_type_uniqid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.issue_type_uniqid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.issue_type_uniqid_seq
  OWNER TO postgres;

--
-- Name: issue_type_uniqid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.issue_type_uniqid_seq OWNED BY public.issue_type.uniqid;


--
-- Name: issue_uniqid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.issue_uniqid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.issue_uniqid_seq
  OWNER TO postgres;

--
-- Name: issue_uniqid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.issue_uniqid_seq OWNED BY public.issue.uniqid;


--
-- Name: request; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.request
(
  username     character varying(30) NOT NULL,
  technicianid integer               NOT NULL,
  issueid      integer,
  returndate   date                  NOT NULL
);


ALTER TABLE public.request
  OWNER TO postgres;

--
-- Name: request_technicianid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.request_technicianid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.request_technicianid_seq
  OWNER TO postgres;

--
-- Name: request_technicianid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.request_technicianid_seq OWNED BY public.request.technicianid;


--
-- Name: skill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.skill
(
  skillid   smallint              NOT NULL,
  skilldesc character varying(60) NOT NULL
);


ALTER TABLE public.skill
  OWNER TO postgres;

--
-- Name: technician; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.technician
(
  technicianid   integer               NOT NULL,
  username       character varying(30) NOT NULL,
  technicianname character varying(30) NOT NULL,
  skillid        smallint              NOT NULL,
  model          character varying(30),
  latitude       real                  NOT NULL,
  longitude      real                  NOT NULL,
  maxdist        smallint
);


ALTER TABLE public.technician
  OWNER TO postgres;

--
-- Name: technician_technicianid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.technician_technicianid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.technician_technicianid_seq
  OWNER TO postgres;

--
-- Name: technician_technicianid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.technician_technicianid_seq OWNED BY public.technician.technicianid;


--
-- Name: time; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."time"
(
  value  numeric(8, 2) NOT NULL,
  unit   character varying(15),
  timeid integer       NOT NULL
);


ALTER TABLE public."time"
  OWNER TO postgres;

--
-- Name: time_timeid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.time_timeid_seq
  AS integer
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.time_timeid_seq
  OWNER TO postgres;

--
-- Name: time_timeid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.time_timeid_seq OWNED BY public."time".timeid;


--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user"
(
  username  character varying(30) NOT NULL,
  jobtitle  character varying(30) NOT NULL,
  datehired date                  NOT NULL
);


ALTER TABLE public."user"
  OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users
(
  name     character varying(30) NOT NULL,
  username character varying(30) NOT NULL,
  password character varying(30) NOT NULL,
  id       integer,
  usertype character varying(255)
);


ALTER TABLE public.users
  OWNER TO postgres;

--
-- Name: users2; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users2
(
  id   bigint NOT NULL,
  name character varying(255)
);


ALTER TABLE public.users2
  OWNER TO postgres;

--
-- Name: capability technicianid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.capability
  ALTER COLUMN technicianid SET DEFAULT nextval('public.capability_technicianid_seq'::regclass);


--
-- Name: has_skill technicianid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.has_skill
  ALTER COLUMN technicianid SET DEFAULT nextval('public.has_skill_technicianid_seq'::regclass);


--
-- Name: has_time timeid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.has_time
  ALTER COLUMN timeid SET DEFAULT nextval('public.has_time_timeid_seq'::regclass);


--
-- Name: is_deployed_to technicianid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.is_deployed_to
  ALTER COLUMN technicianid SET DEFAULT nextval('public.is_deployed_to_technicianid_seq'::regclass);


--
-- Name: is_deployed_to issueid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.is_deployed_to
  ALTER COLUMN issueid SET DEFAULT nextval('public.is_deployed_to_issueid_seq'::regclass);


--
-- Name: issue uniqid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue
  ALTER COLUMN uniqid SET DEFAULT nextval('public.issue_uniqid_seq'::regclass);


--
-- Name: issue issueid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue
  ALTER COLUMN issueid SET DEFAULT nextval('public.issue_issueid_seq'::regclass);


--
-- Name: issue_type uniqid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue_type
  ALTER COLUMN uniqid SET DEFAULT nextval('public.issue_type_uniqid_seq'::regclass);


--
-- Name: issue_type issueid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue_type
  ALTER COLUMN issueid SET DEFAULT nextval('public.issue_type_issueid_seq'::regclass);


--
-- Name: request technicianid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
  ALTER COLUMN technicianid SET DEFAULT nextval('public.request_technicianid_seq'::regclass);


--
-- Name: technician technicianid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.technician
  ALTER COLUMN technicianid SET DEFAULT nextval('public.technician_technicianid_seq'::regclass);


--
-- Name: time timeid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."time"
  ALTER COLUMN timeid SET DEFAULT nextval('public.time_timeid_seq'::regclass);


--
-- Data for Name: capability; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.capability (technicianid, capability) FROM stdin;
3	df
\.


--
-- Data for Name: has_skill; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.has_skill (technicianid, skillid) FROM stdin;
123454	1
123455	2
123456	3
2	1
2	2
3	1
4	1
\.


--
-- Data for Name: has_time; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.has_time (technicianid, timeid) FROM stdin;
123454	12
123455	13
123456	15
2	2
3	3
4	4
\.


--
-- Data for Name: is_deployed_to; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.is_deployed_to (technicianid, issueid, returndate, startdate, deployeddate) FROM stdin;
123454	123	2018-12-01	2018-07-18	2018-07-18
\.


--
-- Data for Name: issue; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.issue (username, uniqid, issueid, issuedate, description) FROM stdin;
jmlee	4	84487	2018-07-18	Network connection taking too long
jmlee	1	0	2018-07-19	test data
jmlee	687	67889	2018-07-22	no paper in printer
jmlee	2	123	2018-07-18	computer keeps rebooting
username	2432	84488	2019-03-18	test
username	9359	84489	2019-03-18	test
username	8362	84490	2019-03-18	test
username	1168	84491	2019-03-18	test
\.


--
-- Data for Name: issue_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.issue_type (type, uniqid, issueid) FROM stdin;
\.


--
-- Data for Name: request; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.request (username, technicianid, issueid, returndate) FROM stdin;
jmlee	123454	67889	2018-07-22
jmlee	2	67889	2018-07-22
jmlee	123456	84487	2018-07-24
\.


--
-- Data for Name: skill; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.skill (skillid, skilldesc) FROM stdin;
1	Troubleshooting
2	Routers and Switches
3	AWS Cloud Certified
4	Database Engineer
5	Tech Lead
6	Probationary
7	Researcher
8	Configuration Guru
9	Javascript
10	Java
11	Python
12	Microservices
13	Security
14	Senior Technician
15	Senior Manager
\.


--
-- Data for Name: technician; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.technician (technicianid, username, technicianname, skillid, model, latitude, longitude,
                        maxdist) FROM stdin;
\.


--
-- Data for Name: time; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."time" (value, unit, timeid) FROM stdin;
50.00	each	100
20.00	each,	12
30.00	each	13
40.00	each	15
40.00	Day	1
40.00	Day	2
2.00	Hour	3
12.00	Hour	4
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (username, jobtitle, datehired) FROM stdin;
jmlee	director	2014-01-01
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (name, username, password, id, usertype) FROM stdin;
user	username	password	1	\N
john applesee	john	password	2	\N
\.


--
-- Data for Name: users2; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users2 (id, name) FROM stdin;
\.


--
-- Name: capability_technicianid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.capability_technicianid_seq', 1, false);


--
-- Name: has_skill_technicianid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.has_skill_technicianid_seq', 1, false);


--
-- Name: has_time_timeid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.has_time_timeid_seq', 1, false);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 2, true);


--
-- Name: is_deployed_to_issueid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.is_deployed_to_issueid_seq', 1, false);


--
-- Name: is_deployed_to_technicianid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.is_deployed_to_technicianid_seq', 1, false);


--
-- Name: issue_issueid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.issue_issueid_seq', 1, false);


--
-- Name: issue_type_issueid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.issue_type_issueid_seq', 4, true);


--
-- Name: issue_type_uniqid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.issue_type_uniqid_seq', 4, true);


--
-- Name: issue_uniqid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.issue_uniqid_seq', 1, false);


--
-- Name: request_technicianid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.request_technicianid_seq', 1, false);


--
-- Name: technician_technicianid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.technician_technicianid_seq', 4, true);


--
-- Name: time_timeid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.time_timeid_seq', 4, true);


--
-- Name: has_skill has_skill_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.has_skill
  ADD CONSTRAINT has_skill_pkey PRIMARY KEY (technicianid, skillid);


--
-- Name: has_time has_time_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.has_time
  ADD CONSTRAINT has_time_pkey PRIMARY KEY (timeid);


--
-- Name: is_deployed_to is_deployed_to_issueid_technicianid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.is_deployed_to
  ADD CONSTRAINT is_deployed_to_issueid_technicianid_key UNIQUE (issueid, technicianid);


--
-- Name: issue issue_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue
  ADD CONSTRAINT issue_pkey PRIMARY KEY (issueid);


--
-- Name: issue_type issue_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue_type
  ADD CONSTRAINT issue_type_pkey PRIMARY KEY (issueid, uniqid);


--
-- Name: issue issue_uniqid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue
  ADD CONSTRAINT issue_uniqid_key UNIQUE (uniqid);


--
-- Name: request request_username_technicianid_issueid_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
  ADD CONSTRAINT request_username_technicianid_issueid_key UNIQUE (username, technicianid, issueid);


--
-- Name: skill skill_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill
  ADD CONSTRAINT skill_pkey PRIMARY KEY (skillid);


--
-- Name: technician technician_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.technician
  ADD CONSTRAINT technician_pkey PRIMARY KEY (technicianid);


--
-- Name: time time_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."time"
  ADD CONSTRAINT time_pkey PRIMARY KEY (timeid);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
  ADD CONSTRAINT user_pkey PRIMARY KEY (username);


--
-- Name: users2 users2_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users2
  ADD CONSTRAINT users2_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
  ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- Name: is_deployed_to fk_issue_issueid_icident_issueid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.is_deployed_to
  ADD CONSTRAINT fk_issue_issueid_icident_issueid FOREIGN KEY (issueid) REFERENCES public.issue (issueid) ON DELETE CASCADE;


--
-- Name: request fk_issue_issueid_request_issueid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
  ADD CONSTRAINT fk_issue_issueid_request_issueid FOREIGN KEY (issueid) REFERENCES public.issue (issueid) ON DELETE CASCADE;


--
-- Name: issue_type fk_issuetype_uniqid_issue_uniqid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.issue_type
  ADD CONSTRAINT fk_issuetype_uniqid_issue_uniqid FOREIGN KEY (uniqid) REFERENCES public.issue (uniqid) ON DELETE CASCADE;


--
-- Name: has_time fk_time_timeid_has_time_time; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.has_time
  ADD CONSTRAINT fk_time_timeid_has_time_time FOREIGN KEY (timeid) REFERENCES public."time" (timeid) ON DELETE CASCADE;


--
-- Name: technician fk_users_username_technician_username; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.technician
  ADD CONSTRAINT fk_users_username_technician_username FOREIGN KEY (username) REFERENCES public.users (username) ON DELETE CASCADE;


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

