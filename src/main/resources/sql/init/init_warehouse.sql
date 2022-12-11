-- fucntions
create or replace function age_to_name(n1 in timestamp)
    return varchar2
    is
    age_value number(10);
    age_name  varchar2(20);
begin
    select months_between(TRUNC(sysdate), cast(n1 as date)) / 12 into age_value from dual;
    if age_value < 21 then
        age_name := 'YOUNG_ADULT';
    elsif age_value < 29 then
        age_name := 'TWENTIES';
    elsif age_value < 39 then
        age_name := 'THIRTIES';
    elsif age_value < 49 then
        age_name := 'FORTIES';
    elsif age_value < 70 then
        age_name := 'MIDDLE_AGED';
    else
        age_name := 'ELDERLY';
    end if;
    return age_name;
end;


create or replace function calculate_engagement(purchases_made in number)
    return varchar2
    is
    engagement varchar2;
begin
    if purchases_made < 11 then
        engagement := 'LOW';
    elsif purchases_made < 29 then
        engagement := 'MEDIUM';
    else
        engagement := 'HIGH';
    end if;
    return engagement;
end;


-- dimension tables

CREATE TABLE ADDRESS as (select address_id, city, voivodeship
                         from zbd_sa.address);

CREATE TABLE "DATE" as (select purchase_id                as date_id,
                               extract(year from "date")  as year,
                               extract(month from "date") as month,
                               to_char("date", 'WW')      as week
                        from zbd_sa.purchase);
CREATE TABLE category as (select distinct(category) as category_id
                          from zbd_sa.item);

CREATE TABLE age
(
    age_id varchar2(20) unique not null
);
INSERT ALL
    INTO age (age_id)
VALUES ('YOUNG_ADULT')
INTO age (age_id)
VALUES ('TWENTIES')
INTO age (age_id)
VALUES ('THIRTIES')
INTO age (age_id)
VALUES ('FOURTIES')
INTO age (age_id)
VALUES ('MIDDLE_AGED')
INTO age (age_id)
VALUES ('ELDERLY')
SELECT 1
FROM DUAL;

CREATE TABLE engagement
(
    engagement_id varchar2(10) unique not null
);
INSERT ALL
    INTO engagement (engagement_id)
VALUES ('LOW')
INTO engagement (engagement_id)
VALUES ('MEDIUM')
INTO engagement (engagement_id)
VALUES ('HIGH')
SELECT 1
FROM DUAL;

CREATE TABLE membership_length
(
    membership_length_id varchar2(10) unique not null
);
INSERT ALL
    INTO membership_length (membership_length_id)
VALUES ('NEW')
INTO membership_length (membership_length_id)
VALUES ('VERY_SHORT')
INTO membership_length (membership_length_id)
VALUES ('SHORT')
INTO membership_length (membership_length_id)
VALUES ('MEDIUM')
INTO membership_length (membership_length_id)
VALUES ('LONG')
SELECT 1
FROM DUAL;

CREATE TABLE gender
(
    gender_id varchar2(10) unique not null
);
INSERT ALL
    INTO gender (gender_id)
VALUES ('MALE')
INTO gender (gender_id)
VALUES ('FEMALE')
INTO gender (gender_id)
VALUES ('UNKNOWN')
SELECT 1
FROM DUAL;


-- fact table

CREATE
    TABLE
    fact_table
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
    recommendation_click_rate number(24)   not null
);