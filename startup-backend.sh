#!/bin/sh
mvn clean install
terminal -e ./startup-frontend.sh --noclose
mvn spring-boot:run
