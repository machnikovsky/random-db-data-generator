#!/usr/bin/env bash

docker cp ../../sql/data/kuba/address_kuba.sql zbd-postgres:/tmp/address_kuba.sql &&
docker cp ../../sql/data/kuba/offer_kuba.sql zbd-postgres:/tmp/offer_kuba.sql &&
docker cp ../../sql/data/kuba/item_kuba.sql zbd-postgres:/tmp/item_kuba.sql &&
docker cp ../../sql/data/kuba/account_kuba.sql zbd-postgres:/tmp/account_kuba.sql &&
docker cp ../../sql/data/kuba/client_kuba.sql zbd-postgres:/tmp/client_kuba.sql &&
docker cp ../../sql/data/kuba/recommendation_kuba.sql zbd-postgres:/tmp/recommendation_kuba.sql &&
docker cp ../../sql/data/kuba/shopping_cart_kuba.sql zbd-postgres:/tmp/shopping_cart_kuba.sql &&
docker cp ../../sql/data/kuba/cart_offer_kuba.sql zbd-postgres:/tmp/cart_offer_kuba.sql &&
docker cp ../../sql/data/kuba/purchase_kuba.sql zbd-postgres:/tmp/purchase_kuba.sql &&
docker cp ../../sql/data/kuba/return_kuba.sql zbd-postgres:/tmp/return_kuba.sql &&
docker cp ../../sql/clear/clear_kuba.sql zbd-postgres:/tmp/clear_kuba.sql &&
docker cp ../../sql/init/init_kuba.sql zbd-postgres:/tmp/init_kuba.sql
