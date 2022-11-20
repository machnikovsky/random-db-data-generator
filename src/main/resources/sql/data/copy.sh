#!/usr/bin/env bash

docker cp ./tmp/offer_tmp.sql zbd-postgres:/tmp/offer_tmp.sql &&
docker cp ./tmp/item_tmp.sql zbd-postgres:/tmp/item_tmp.sql &&
docker cp ./tmp/account_tmp.sql zbd-postgres:/tmp/account_tmp.sql &&
docker cp ./tmp/client_tmp.sql zbd-postgres:/tmp/client_tmp.sql &&
docker cp ./tmp/recommendation_tmp.sql zbd-postgres:/tmp/recommendation_tmp.sql &&
docker cp ./tmp/shopping_cart_tmp.sql zbd-postgres:/tmp/shopping_cart_tmp.sql &&
docker cp ./tmp/cart_offer_tmp.sql zbd-postgres:/tmp/cart_offer_tmp.sql &&
docker cp ./tmp/purchase_tmp.sql zbd-postgres:/tmp/purchase_tmp.sql &&
docker cp ./tmp/return_tmp.sql zbd-postgres:/tmp/return_tmp.sql &&
docker cp ./clear.sql zbd-postgres:/tmp/clear.sql &&
docker cp ../init/init.sql zbd-postgres:/tmp/init.sql
