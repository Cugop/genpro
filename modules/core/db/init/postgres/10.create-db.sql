CREATE TABLE genpro_json (
	id uuid NOT NULL,
	body_doc jsonb NULL,
	CONSTRAINT genpro_document_pkey_1 PRIMARY KEY (id),
	CONSTRAINT genpro_json_fk FOREIGN KEY (id) REFERENCES public.genpro_document(id)
);
-- begin GENPRO_DOC
create table GENPRO_DOC (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    JSON_BODY text,
    doc_j jsonb,
    --
    primary key (ID)
)^
-- end GENPRO_DOC
-- begin COUNTRY
create table Country (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    name varchar(127) not null,
    name_im varchar(127),
    name_rod varchar(127),
    code_numeric varchar(7),
    code_2_letter_ru varchar(7),
    code_2_letter_en varchar(7),
    code_3_letter_en varchar(7),
    --
    primary key (ID)
)^
-- end COUNTRY
-- begin GENPRO_ENTITY_FOR_CHECH_WORK_JSON_OBJECT
create table GENPRO_ENTITY_FOR_CHECH_WORK_JSON_OBJECT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    citizenship_id uuid,
    --
    primary key (ID)
)^
-- end GENPRO_ENTITY_FOR_CHECH_WORK_JSON_OBJECT
