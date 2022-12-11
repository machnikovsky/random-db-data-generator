-- dimension tables

CREATE TABLE address
(
    address_id  VARCHAR2(40) unique not null,
    city        varchar(50)  not null,
    voivodeship varchar(30) not null
);

CREATE TABLE "date"
(
    date_id VARCHAR2(40) unique not null,
    year    number(4)    not null,
    month   number(2)    not null,
    week    number(2)    not null
);

CREATE TABLE category
(
    category_id VARCHAR2(40) unique not null,
    category    varchar(30) not null
);

CREATE TABLE age
(
    age_id VARCHAR2(40) unique not null,
    age    number(3)    not null
);

CREATE TABLE engagement
(
    engagement_id VARCHAR2(40) unique not null,
    engagement            varchar(20) not null
);

CREATE TABLE membership_length
(
    membership_length_id VARCHAR2(40) unique not null,
    membership_length    varchar(20) not null
);

CREATE TABLE gender
(
    gender_id VARCHAR2(40) unique not null,
    gender    varchar(20) not null
);

-- fact table

CREATE TABLE fact_table
(
    address_id                VARCHAR2(40) not null,
    engagement_id             VARCHAR2(40) not null,
    date_id                   VARCHAR2(40) not null,
    category_id               VARCHAR2(40) not null,
    age_id                    VARCHAR2(40) not null,
    membership_length_id      VARCHAR2(40) not null,
    gender_id                 VARCHAR2(40) not null,
    profit_summary            number(24)   not null,
    sold_value_summary        number(24)   not null,
    sold_value_average        number(24)   not null,
    sold_items                number(24)   not null,
    returned_items            number(24)   not null,
    recommendation_click_rate number(24)   not null,
);