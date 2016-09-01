# ishares-etf-data-download

## Installation

Set the environment variables in `conf/application.conf`. Also set the environment variable ISHARES_DATA_DIR to the path to where you will download the files.

Create the postgres databases according to the environment variables you set.


```
git clone https://github.com/shafiquejamal/ishares-etf-data-download.git some_directory
cd some_directory
sbt flywayMigrate
sbt assembly
./download_files.sh
```

The bash script will download the data, then run the .jar file to store the data in your postgres db.