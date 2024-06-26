#!/bin/bash

# Konfigurationsvariablen
DB_USER="we"
DB_NAME="postgres"
DB_HOST="localhost"        # z.B. localhost
DB_PASSWORD="swe24"
SQL_FILE="V1_Create_Tables.sql"

export PGPASSWORD=$DB_PASSWORD

# psql Befehl um das SQL-Skript auszuführen
psql -U $DB_USER -d $DB_NAME -h $DB_HOST -f $SQL_FILE

unset PGPASSWORD