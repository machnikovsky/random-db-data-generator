CREATE TABLE IF NOT EXISTS address
(
    address_id  VARCHAR(36) unique not null,
    city        varchar(50)        not null,
    number      int                not null,
    street      varchar(50)        not null,
    postal_code varchar(5)         not null,
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
        'ZACHODNIOPOMORSKIE')      not null
);

CREATE TABLE IF NOT EXISTS item
(
    item_id  VARCHAR(36) unique                                         not null,
    name     varchar(100)                                               not null,
    category ENUM ('DOM', 'ELEKTRONIKA', 'JEDZENIE', 'UBRANIA', 'INNE') not null
);

CREATE TABLE IF NOT EXISTS user
(
    user_id    VARCHAR(36) unique                 not null,
    username   varchar(50)                        not null,
    email      varchar(50)                        not null,
    password   varchar(50)                        not null,
    first_name varchar(50)                        not null,
    last_name  varchar(50)                        not null,
    birth_date timestamp                          not null,
    gender     ENUM ('MALE', 'FEMALE', 'UNKNOWN') not null,
    role       ENUM ('USER', 'ADMIN')             not null,
    address_id VARCHAR(36)                        not null,
    CONSTRAINT fk_user_address FOREIGN KEY (address_id) REFERENCES address (address_id)
);

CREATE TABLE IF NOT EXISTS recommendation
(
    recommendation_id VARCHAR(36) unique not null,
    item_id           VARCHAR(36)        not null,
    text              varchar(100)       not null,
    showed_count      int                not null,
    clicked_count     int                not null,
    user_id           VARCHAR(36)        not null,
    CONSTRAINT fk_recommendation_item FOREIGN KEY (item_id) REFERENCES item (item_id),
    CONSTRAINT fk_recommendation_user FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE IF NOT EXISTS offer
(
    offer_id    VARCHAR(36) unique          not null,
    seller_id   VARCHAR(36)                 not null,
    description varchar(1000)               not null,
    status      ENUM ('ACTIVE', 'SOLD_OUT') not null,
    item_id     VARCHAR(36)                 not null,
    price       decimal                     not null,
    CONSTRAINT fk_offer_seller FOREIGN KEY (seller_id) REFERENCES user (user_id),
    CONSTRAINT fk_offer_item FOREIGN KEY (item_id) REFERENCES item (item_id)
);

CREATE TABLE IF NOT EXISTS purchase
(
    purchase_id     VARCHAR(36) unique                             not null,
    payment_method  ENUM ('BLIK', 'CASH', 'CARD')                  not null,
    date            timestamp                                      not null,
    shipping_method ENUM ('PACZKOMAT', 'DPD', 'POCZTA')            not null,
    purchase_status ENUM ('IN_PROGRESS', 'CANCELLED', 'DELIVERED') not null,
    buyer_id        VARCHAR(36)                                    not null,
    seller_id       VARCHAR(36)                                    not null,
    address_id      VARCHAR(36)                                    not null,
    CONSTRAINT fk_buyer FOREIGN KEY (buyer_id) REFERENCES user (user_id),
    CONSTRAINT fk_purchase_seller FOREIGN KEY (seller_id) REFERENCES user (user_id),
    CONSTRAINT fk_purchase_address FOREIGN KEY (address_id) REFERENCES address (address_id)
);


CREATE TABLE IF NOT EXISTS `return`
(
    return_id       VARCHAR(36) unique not null,
    date            timestamp          not null,
    number_of_items int                not null,
    purchase_id     VARCHAR(36)        not null,
    CONSTRAINT fk_purchase FOREIGN KEY (purchase_id) REFERENCES purchase (purchase_id)
);
