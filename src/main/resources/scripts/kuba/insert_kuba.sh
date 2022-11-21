#!/usr/bin/env bash

docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/address_kuba.sql &&
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/client_kuba.sql &&
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/account_kuba.sql &&
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/item_kuba.sql &&
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/offer_kuba.sql &&
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/recommendation_kuba.sql
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/shopping_cart_kuba.sql
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/cart_offer_kuba.sql
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/purchase_kuba.sql
docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/return_kuba.sql
