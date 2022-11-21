#!/usr/bin/env bash

docker exec -it zbd-weronika bash -c 'mysql -u zbd-weronika -pzbd-weronika zbd-weronika </tmp/clear_weronika.sql'
