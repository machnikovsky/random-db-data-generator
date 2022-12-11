-- fucntions
create or replace function age_to_name(n1 in timestamp)
    return varchar2
as
    age_value number;
    age_name  varchar2;
begin
    select months_between(TRUNC(sysdate), cast(n1 as date)) / 12 into age_value from dual;
    if age_name < 21 then
        age_name := 'YOUNG_ADULT';
    elsif age_name < 29 then
        age_name := 'TWENTIES';
    elsif age_name < 39 then
        age_name := 'THIRTIES';
    elsif age_name < 49 then
        age_name := 'FORTIES';
    elsif age_name < 70 then
        age_name := 'MIDDLE_AGED';
    else
        age_name := 'ELDERLY';
    end if;
    return age_name;
end;

create or replace function calculate_engagement(purchases_made in number)
    return varchar2
as
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

CREATE TABLE address (
                      address_id VARCHAR2(40) unique not null,
                      city varchar(50)  not null,
                      voivodeship varchar(30) not null
    ) as
select address_id, city, vovoideship
from zbd_sa.address;

CREATE TABLE "date" (
                     date_id VARCHAR2(40) unique not null,
                     year number(4)    not null,
                     month number(2)    not null,
                     week number(2)    not null
    ) as
select purchase_id, extract(year from "date"), extract(month from "date"), to_char("date", 'WW')
from zbd_sa.purchase;

CREATE TABLE category (
                       category_id VARCHAR2(40) unique not null,
                       category varchar(30) not null
    ) as
select (select dbms_random.string('A', 36) str from dual), (select distinct(category) from zbd_sa.item);

CREATE TABLE age (
                  age_id VARCHAR2(40) unique not null,
                  age VARCHAR2(30)   not null
    ) as
select (select dbms_random.string('A', 36) str from dual), age_to_name(date_of_birth)
from zbd_sa.client;



CREATE TABLE engagement (
                         engagement_id VARCHAR2(40) unique not null,
                         engagement varchar(20)         not null
    ) as
select c.client_id, calculate_engagement(select count(*) from zbd_sa.purchase p where p.client_id = c.client_id)
from zbd_sa.client c;

CREATE
    TABLE
    membership_length
(
    membership_length_id VARCHAR2(40) unique not null,
    membership_length    varchar(20)         not null
);

CREATE
    TABLE
    gender
(
    gender_id VARCHAR2(40) unique not null,
    gender    varchar(20)         not null
);

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