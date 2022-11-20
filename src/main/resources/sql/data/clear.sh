#!/usr/bin/env bash

docker exec -it zbd-postgres psql -U zbd-kuba -d zbd-kuba -f /tmp/clear.sql
