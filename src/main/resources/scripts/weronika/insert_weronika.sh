#!/usr/bin/env bash

docker exec -it zbd-weronika bash -c 'mysql -u zbd-weronika -pzbd-weronika zbd-weronika </tmp/address_weronika.sql' &&
docker exec -it zbd-weronika bash -c 'mysql -u zbd-weronika -pzbd-weronika zbd-weronika </tmp/user_weronika.sql' &&
docker exec -it zbd-weronika bash -c 'mysql -u zbd-weronika -pzbd-weronika zbd-weronika </tmp/item_weronika.sql' &&
docker exec -it zbd-weronika bash -c 'mysql -u zbd-weronika -pzbd-weronika zbd-weronika </tmp/offer_weronika.sql' &&
docker exec -it zbd-weronika bash -c 'mysql -u zbd-weronika -pzbd-weronika zbd-weronika </tmp/purchase_weronika.sql' &&
docker exec -it zbd-weronika bash -c 'mysql -u zbd-weronika -pzbd-weronika zbd-weronika </tmp/recommendation_weronika.sql' &&
docker exec -it zbd-weronika bash -c 'mysql -u zbd-weronika -pzbd-weronika zbd-weronika </tmp/return_weronika.sql'
