CREATE TABLE address
(
    address_id  VARCHAR2(40) unique not null,
    city        varchar(50)  not null,
    "number"    int          not null,
    street      varchar(50)  not null,
    postal_code varchar(5)   not null,
    voivodeship varchar(50) not null
);

CREATE TABLE client
(
    client_id  VARCHAR2(40) unique not null,
    first_name varchar(50)  not null,
    last_name  varchar(50)  not null,
    birth_date timestamp    not null,
    login      varchar(50)  not null,
    password   varchar(50)  not null,
    email      varchar(50)  not null,
    gender     varchar(15) not null,
    address_id VARCHAR(36)  not null,
    created_at timestamp    not null,
    CONSTRAINT fk_client_address FOREIGN KEY (address_id) REFERENCES address (address_id)
);

CREATE TABLE item
(
    item_id  VARCHAR2(40) unique not null,
    name     varchar(100) not null,
    category varchar(20) not null
);

CREATE TABLE offer
(
    offer_id      VARCHAR2(40) unique not null,
    item_id       VARCHAR2(40) not null,
    seller_id     VARCHAR2(40) not null,
    shipment_type varchar(20) not null,
    price         decimal      not null,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES item (item_id),
    CONSTRAINT fk_client FOREIGN KEY (seller_id) REFERENCES client (client_id)
);

CREATE TABLE recommendation
(
    recommendation_id VARCHAR2(40) unique not null,
    client_id         VARCHAR2(40) not null,
    offer_id          VARCHAR2(40) not null,
    content           varchar(100) not null,
    showed_count      int          not null,
    clicked_count     int          not null,
    CONSTRAINT fk_client_rec FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT fk_offer_rec FOREIGN KEY (offer_id) REFERENCES offer (offer_id)
);

CREATE TABLE purchase
(
    purchase_id    VARCHAR2(40) unique not null,
    client_id      VARCHAR2(40) not null,
    offer_id       VARCHAR2(40) not null,
    quantity       int      not null,
    payment_method varchar(10) not null,
    "date"           timestamp    not null,
    CONSTRAINT fk_client_purchase FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT fk_offer_purchase FOREIGN KEY (offer_id) REFERENCES offer (offer_id)
);

CREATE TABLE return
(
    return_id   VARCHAR2(40) unique not null,
    client_id   VARCHAR2(40),
    offer_id    VARCHAR2(40),
    purchase_id VARCHAR2(40),
    quantity    int          not null,
    "date"        timestamp    not null,
    CONSTRAINT fk_client_ret FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT fk_offer_ret FOREIGN KEY (offer_id) REFERENCES offer (offer_id),
    CONSTRAINT fk_purchase_ret FOREIGN KEY (purchase_id) REFERENCES purchase (purchase_id)
);
