alter table GENPRO_DOC add constraint FK_GENPRO_DOC_ON_CITIZENSHIP foreign key (CITIZENSHIP_ID) references Country(ID);
create index IDX_GENPRO_DOC_ON_CITIZENSHIP on GENPRO_DOC (CITIZENSHIP_ID);
