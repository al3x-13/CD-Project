--
-- PostgreSQL database dump
--

-- Dumped from database version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: beaches; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.beaches (
    id character(1) NOT NULL
);


ALTER TABLE public.beaches OWNER TO admin;


--
-- Name: bookings; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.bookings (
    id integer NOT NULL,
    beach_id character(1) NOT NULL,
    date date NOT NULL,
    from_time time without time zone NOT NULL,
    to_time time without time zone NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_id integer NOT NULL,
	lounge_ids varchar[] NOT NULL
);


ALTER TABLE public.bookings OWNER TO admin;

--
-- Name: bookings_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.bookings_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 14;


ALTER TABLE public.bookings_id_seq OWNER TO admin;

--
-- Name: bookings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.bookings_id_seq OWNED BY public.bookings.id;


--
-- Name: lounges; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.lounges (
    id character varying(3) NOT NULL,
    beach_id character(1) NOT NULL,
    max_capacity integer NOT NULL
);


ALTER TABLE public.lounges OWNER TO admin;

--
-- Name: users; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password_hash text NOT NULL
);


ALTER TABLE public.users OWNER TO admin;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 2;


ALTER TABLE public.users_id_seq OWNER TO admin;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: bookings id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.bookings ALTER COLUMN id SET DEFAULT nextval('public.bookings_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: beaches; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.beaches (id) FROM stdin;
A
B
C
\.


--
-- Data for Name: bookings; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.bookings (id, beach_id, date, from_time, to_time, created_at, user_id, lounge_ids) FROM stdin;
1	A	2024-06-12	10:00:00	11:00:00	2023-12-17 01:38:30.625406	1	{A16}
2	A	2024-06-12	10:00:00	11:00:00	2023-12-17 01:39:00.472586	1	{A17,A18,A1}
3	A	2024-06-12	10:00:00	11:00:00	2023-12-17 01:39:38.040579	1	{A11,A12}
4	A	2024-06-12	10:00:00	11:00:00	2023-12-17 01:39:41.558063	1	{A13}
5	A	2024-06-12	10:00:00	11:00:00	2023-12-17 01:39:52.465283	1	{A2}
6	A	2024-06-12	10:00:00	11:00:00	2023-12-17 01:39:54.662278	1	{A3}
7	A	2024-06-12	10:00:00	11:00:00	2023-12-17 01:40:01.261202	1	{A4,A5}
8	B	2024-06-13	10:00:00	11:00:00	2023-12-17 01:46:11.243954	1	{B1}
9	B	2024-06-13	10:00:00	11:00:00	2023-12-17 01:46:46.783754	1	{B11,B6}
10	B	2024-06-13	10:00:00	11:00:00	2023-12-17 01:47:17.200241	1	{B2,B3}
11	B	2024-06-13	10:00:00	11:00:00	2023-12-17 01:47:37.1703	1	{B7,B8}
12	C	2024-06-14	11:00:00	14:00:00	2023-12-17 01:48:36.13475	1	{C1,C2,C3}
13	C	2024-06-14	11:00:00	14:00:00	2023-12-17 01:48:43.980997	1	{C4,C5,C6,C7,C8}
14	C	2024-06-14	11:00:00	14:00:00	2023-12-17 01:48:48.504974	1	{C9,C10}
\.


--
-- Data for Name: lounges; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.lounges (id, beach_id, max_capacity) FROM stdin;
A1	A	2
A2	A	2
A3	A	2
A4	A	2
A5	A	2
A6	A	2
A7	A	2
A8	A	2
A9	A	2
A10	A	2
A11	A	3
A12	A	3
A13	A	3
A14	A	3
A15	A	3
A16	A	4
A17	A	4
A18	A	4
A19	A	4
A20	A	4
B1	B	2
B2	B	2
B3	B	2
B4	B	2
B5	B	2
B6	B	3
B7	B	3
B8	B	3
B9	B	3
B10	B	3
B11	B	4
C1	C	2
C2	C	2
C3	C	2
C4	C	2
C5	C	2
C6	C	2
C7	C	2
C8	C	2
C9	C	2
C10	C	2
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.users (id, username, password_hash) FROM stdin;
1	john	$2a$10$LOsnM1XyJ8nUKTA0hQuxN.N6Dw9mDIzZvPA3xb3bfFUOlvnpbxj2e
\.


--
-- Name: bookings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.bookings_id_seq', 14, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.users_id_seq', 2, false);


--
-- Name: beaches beaches_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.beaches
    ADD CONSTRAINT beaches_pkey PRIMARY KEY (id);



--
-- Name: bookings bookings_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.bookings
    ADD CONSTRAINT bookings_pkey PRIMARY KEY (id);


--
-- Name: lounges lounges_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.lounges
    ADD CONSTRAINT lounges_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: bookings bookings_beach_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.bookings
    ADD CONSTRAINT bookings_beach_id_fkey FOREIGN KEY (beach_id) REFERENCES public.beaches(id);


--
-- Name: bookings bookings_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.bookings
    ADD CONSTRAINT bookings_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: lounges lounges_beach_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.lounges
    ADD CONSTRAINT lounges_beach_id_fkey FOREIGN KEY (beach_id) REFERENCES public.beaches(id);


--
-- PostgreSQL database dump complete
--

