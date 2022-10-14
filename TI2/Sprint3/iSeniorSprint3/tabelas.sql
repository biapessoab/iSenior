CREATE TABLE IF NOT EXISTS public.usuario
(
    idusuario integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1 ),
    email text COLLATE pg_catalog."default",
    nomeusuario text COLLATE pg_catalog."default",
    senha text COLLATE pg_catalog."default",
    primeironome text COLLATE pg_catalog."default",
    sobrenome text COLLATE pg_catalog."default",
    CONSTRAINT pk_usuario PRIMARY KEY (idusuario),
    CONSTRAINT un_usuario_email UNIQUE (email),
    CONSTRAINT un_usuario_nome UNIQUE (nomeusuario)
)



CREATE TABLE IF NOT EXISTS public.categoria
(
    idcategoria integer NOT NULL,
    categoria text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_categoria PRIMARY KEY (idcategoria)
)


CREATE TABLE IF NOT EXISTS public.artigo
(
    idartigo integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1 ),
    categoria integer,
    palavrachave text COLLATE pg_catalog."default",
    tag text COLLATE pg_catalog."default" NOT NULL,
    titulo text COLLATE pg_catalog."default" NOT NULL,
    texto text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_artigo PRIMARY KEY (idartigo),
    CONSTRAINT fk_artigo FOREIGN KEY (categoria)
        REFERENCES public.categoria (idcategoria) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


CREATE TABLE IF NOT EXISTS public.faq
(
    idfaq integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1 ),
    pergunta text COLLATE pg_catalog."default",
    resposta text COLLATE pg_catalog."default",
    CONSTRAINT pk_faq PRIMARY KEY (idfaq)
)


TABLESPACE pg_default;


insert into categoria values (0, 'Dia-a-dia')
insert into categoria values (1, 'Configuracoes')
insert into categoria values (2, 'Cuidados')