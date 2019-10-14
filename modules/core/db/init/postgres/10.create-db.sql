-- begin MWATTREPS_ATTACHMENT
create table MWATTREPS_ATTACHMENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ATTACHABLE varchar(255) not null,
    NAME varchar(255) not null,
    FILE_ID uuid not null,
    SOURCE varchar(50) not null,
    TYPE_ID uuid,
    REPORT_ID uuid,
    TEMPLATE_CODE varchar(255),
    --
    primary key (ID)
)^
-- end MWATTREPS_ATTACHMENT
-- begin MWATTREPS_ATTACHMENT_TYPE
create table MWATTREPS_ATTACHMENT_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    TARGET_META_CLASS varchar(255),
    --
    primary key (ID)
)^
-- end MWATTREPS_ATTACHMENT_TYPE
