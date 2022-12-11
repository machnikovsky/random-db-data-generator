-- dimension tables

CREATE TABLE address
(
    address_id  VARCHAR2(40) not null,
    city        varchar(50)  not null,
    voivodeship ENUM ('DOLNOSLASKIE',
        'KUJAWSKOPOMORSKIE',
        'LUBELSKIE',
        'LUBUSKIE',
        'LODZKIE',
        'MALOPOLSKIE',
        'MAZOWIECKIE',
        'OPOLSKIE',
        'PODKARPACKIE',
        'PODLASKIE',
        'POMORSKIE',
        'SLASKIE',
        'SWIETOKRZYSKIE',
        'WARMINSKOMAZURSKIE',
        'WIELKOPOLSKIE',
        'ZACHODNIOPOMORSKIE') not null
);

CREATE TABLE "date"
(
    date_id VARCHAR2(40) not null,
    year    number(4)    not null,
    month   number(2)    not null,
    week    number(2)    not null
);

CREATE TABLE category
(
    category_id VARCHAR2(40) not null,
    category    ENUM ( 'OBUWIE', 'UBRANIA', 'SAMOCHODY', 'NIERUCHOMOSCI', 'ZABAWKI', 'INNE') not null
);

CREATE TABLE age
(
    age_id VARCHAR2(40) not null,
    age    number(3)    not null
);

CREATE TABLE enagagement
(
    enagagement_id VARCHAR2(40) not null,
    age            ENUM ('LOW', 'MEDIUM', 'HIGH') not null
);

CREATE TABLE membership_length
(
    membership_length_id VARCHAR2(40) not null,
    membership_length    ENUM ('NEW', 'VERY_LOW', 'LOW', 'MEDIUM', 'HIGH', 'VERY_HIGH') not null
);

CREATE TABLE gender
(
    gender_id VARCHAR2(40) not null,
    gender    ENUM ('MALE', 'FEMALE', 'UNKNOWN') not null
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