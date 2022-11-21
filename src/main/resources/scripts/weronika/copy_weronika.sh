#!/usr/bin/env bash

docker cp ../../sql/data/weronika/address_weronika.sql zbd-weronika:/tmp/address_weronika.sql &&
docker cp ../../sql/data/weronika/item_weronika.sql zbd-weronika:/tmp/item_weronika.sql &&
docker cp ../../sql/data/weronika/offer_weronika.sql zbd-weronika:/tmp/offer_weronika.sql &&
docker cp ../../sql/data/weronika/purchase_weronika.sql zbd-weronika:/tmp/purchase_weronika.sql &&
docker cp ../../sql/data/weronika/recommendation_weronika.sql zbd-weronika:/tmp/recommendation_weronika.sql &&
docker cp ../../sql/data/weronika/return_weronika.sql zbd-weronika:/tmp/return_weronika.sql &&
docker cp ../../sql/data/weronika/user_weronika.sql zbd-weronika:/tmp/user_weronika.sql &&
docker cp ../../sql/clear/clear_weronika.sql zbd-weronika:/tmp/clear_weronika.sql &&
docker cp ../../sql/init/init_weronika.sql zbd-weronika:/tmp/init_weronika.sql
