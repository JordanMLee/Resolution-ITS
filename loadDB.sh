#/bin/sh

echo "ENTER DATABASE NAME: "
read dbname

echo "ENTER DATABASE USERNAME: "
read dbuser

# echo "ENTER DATABASE PASSWORD: "
# read dbpassword

createdb -h localhost -p 5432 -U $dbuser $dbname

psql -U $dbuser $dbname < "./src/main/resources/ResolutionITS_export.pgsql"