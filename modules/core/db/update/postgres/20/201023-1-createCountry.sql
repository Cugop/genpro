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
);