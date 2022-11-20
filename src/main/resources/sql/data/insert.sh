#!/usr/bin/env bash

docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/client_tmp.sql &&
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/account_tmp.sql &&
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/item_tmp.sql &&
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/offer_tmp.sql &&
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/recommendation_tmp.sql
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/shopping_cart_tmp.sql
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/cart_offer_tmp.sql
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/purchase_tmp.sql
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/return_tmp.sql
