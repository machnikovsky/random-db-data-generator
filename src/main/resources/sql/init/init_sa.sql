CREATE TYPE voivodeship AS ENUM ('DOLNOSLASKIE',
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
        'ZACHODNIOPOMORSKIE');

CREATE TABLE IF NOT EXISTS address
(
    address_id      uuid unique not null,
    city            varchar(50)        not null,
    number          int                not null,
    street          varchar(50)        not null,
    postal_code     varchar(5)         not null,
    voivodeship     voivodeship        not null
);

CREATE TYPE gender AS ENUM ('MALE', 'FEMALE', 'UNKNOWN');
CREATE TYPE role AS ENUM ('ADMIN', 'USER');

CREATE TABLE IF NOT EXISTS client
(
    client_id           uuid unique not null,
    first_name          varchar(50) not null,
    last_name           varchar(50) not null,
    pesel               varchar(11) not null,
    birth_date          timestamp   not null,
    login               varchar(50) not null,
    password            varchar(50) not null,
    email               varchar(50) not null,
    gender              gender not null,
    phone_number        varchar(15) not null,
    bank_account_number varchar(22) not null,
    role                role not null,
    address_id          VARCHAR(36) not null,
    created_at          timestamp   not null,
    CONSTRAINT fk_client_address FOREIGN KEY (address_id) REFERENCES address (address_id)
);


CREATE TYPE category AS ENUM ('OBUWIE', 'UBRANIA', 'SAMOCHODY', 'NIERUCHOMOSCI', 'ZABAWKI', 'INNE');
CREATE TYPE offer_type AS ENUM ('KUP_TERAZ', 'LICYTACJA', 'OGLOSZENIE');
CREATE TYPE shipment_type AS ENUM ('KURIER', 'POCZTA', 'PACZKOMAT', 'ODBIOR_OSOBISTY');
CREATE TYPE stage AS ENUM ('PUSTY', 'ZAPELNIONY', 'PLATNOSC');
CREATE TYPE payment_method AS ENUM ('CREDIT', 'CARD');

CREATE TABLE IF NOT EXISTS item
(
    item_id uuid    unique          not null,
    name            varchar(100)    not null,
    description     varchar(500)    not null,
    category        category        not null,
    price           decimal         not null
);

CREATE TABLE IF NOT EXISTS offer
(
    offer_id            uuid unique not null,
    item_id             uuid not null,
    seller_id           uuid not null,
    publication_date    timestamp not null,
    offer_type          offer_type not null,
    shipment_type       shipment_type not null,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES item (item_id),
    CONSTRAINT fk_client FOREIGN KEY (seller_id) REFERENCES client (client_id)
);

CREATE TABLE IF NOT EXISTS recommendation
(
    recommendation_id   uuid unique not null,
    client_id           uuid not null,
    offer_id            uuid not null,
    content             varchar(100) not null,
    showed_count        int          not null,
    clicked_count       int          not null,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT fk_offer FOREIGN KEY (offer_id) REFERENCES offer (offer_id)
);

CREATE TABLE IF NOT EXISTS purchase
(
    purchase_id         uuid unique not null,
    client_id           uuid not null,
    offer_id            uuid not null,
    quantity            int       not null,
    payment_method      payment_method not null,
    date                timestamp not null,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT fk_offer FOREIGN KEY (offer_id) REFERENCES offer (offer_id)
);

CREATE TABLE IF NOT EXISTS return
(
    return_id       uuid unique not null,
    client_id       uuid not null,
    offer_id        uuid not null,
    purchase_id     uuid not null,
    quantity        int       not null,
    date            timestamp not null,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT fk_offer FOREIGN KEY (offer_id) REFERENCES offer (offer_id),
    CONSTRAINT fk_purchase FOREIGN KEY (purchase_id) REFERENCES purchase (purchase_id)
);
