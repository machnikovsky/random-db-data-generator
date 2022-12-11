CREATE TABLE address
(
    address_id  VARCHAR2(40) not null,
    city        varchar(50)  not null,
    number      int          not null,
    street      varchar(50)  not null,
    postal_code varchar(5)   not null,
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

CREATE TABLE client
(
    client_id           VARCHAR2(40) not null,
    first_name          varchar(50)  not null,
    last_name           varchar(50)  not null,
    pesel               varchar(11)  not null,
    birth_date          timestamp    not null,
    login               varchar(50)  not null,
    password            varchar(50)  not null,
    email               varchar(50)  not null,
    gender              ENUM ('MALE', 'FEMALE', 'UNKNOWN') not null,
    phone_number        varchar(15)  not null,
    bank_account_number varchar(22)  not null,
    role                ENUM ( 'ADMIN', 'USER') not null,
    address_id          VARCHAR(36)  not null,
    created_at          timestamp    not null,
    CONSTRAINT fk_client_address FOREIGN KEY (address_id) REFERENCES address (address_id)
);

CREATE TABLE item
(
    item_id     VARCHAR2(40) not null,
    name        varchar(100) not null,
    description varchar(500) not null,
    category    ENUM ( 'DOM', 'ELEKTRONIKA', 'JEDZENIE', 'OBUWIE', 'UBRANIA', 'SAMOCHODY', 'NIERUCHOMOSCI', 'ZABAWKI', 'INNE') not null
);

CREATE TABLE offer
(
    offer_id         VARCHAR2(40) not null,
    item_id          VARCHAR2(40) not null,
    seller_id        VARCHAR2(40) not null,
    publication_date timestamp    not null,
    shipment_type    ENUM ('KURIER', 'POCZTA', 'PACZKOMAT', 'ODBIOR_OSOBISTY', 'DPD') not null,
    price            decimal      not null,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES item (item_id),
    CONSTRAINT fk_client FOREIGN KEY (seller_id) REFERENCES client (client_id)
);

CREATE TABLE recommendation
(
    recommendation_id VARCHAR2(40) not null,
    client_id         VARCHAR2(40) not null,
    offer_id          VARCHAR2(40) not null,
    content           varchar(100) not null,
    showed_count      int          not null,
    clicked_count     int          not null,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT fk_offer FOREIGN KEY (offer_id) REFERENCES offer (offer_id)
);

CREATE TABLE purchase
(
    purchase_id    VARCHAR2(40) not null,
    client_id      VARCHAR2(40) not null,
    offer_id       VARCHAR2(40) not null,
    quantity       int          not null,
    payment_method ENUM ('BLIK', 'CASH', 'CREDIT', 'CARD') not null,
    date           timestamp    not null,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT fk_offer FOREIGN KEY (offer_id) REFERENCES offer (offer_id)
);

CREATE TABLE return
(
    return_id   VARCHAR2(40) not null,
    client_id   VARCHAR2(40) not null,
    offer_id    VARCHAR2(40) not null,
    purchase_id VARCHAR2(40) not null,
    quantity    int          not null,
    date        timestamp    not null,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT fk_offer FOREIGN KEY (offer_id) REFERENCES offer (offer_id),
    CONSTRAINT fk_purchase FOREIGN KEY (purchase_id) REFERENCES purchase (purchase_id)
);
