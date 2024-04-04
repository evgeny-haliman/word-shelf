CREATE TABLE IF NOT EXISTS public.person (
	id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE),
	"name" varchar(20) NOT NULL,
	"password" varchar(100) NOT NULL,
	CONSTRAINT person_pkey PRIMARY KEY (id),
	CONSTRAINT person_username_key UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS public.shelf (
	id int4 PRIMARY KEY ,
	"json" jsonb NULL,
    FOREIGN KEY (id) REFERENCES public.person(id) ON DELETE CASCADE
);

