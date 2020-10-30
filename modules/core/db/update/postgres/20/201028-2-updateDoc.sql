alter table GENPRO_DOC rename column citizenship_id to citizenship_id__u08026 ;
alter table GENPRO_DOC drop constraint FK_GENPRO_DOC_ON_CITIZENSHIP ;
drop index IDX_GENPRO_DOC_ON_CITIZENSHIP ;
