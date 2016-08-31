#!/bin/bash

for f in ./blackrock_ishares/*.txt
do
	echo "*********************"
	echo "File: $f" 
	echo "+++++++++++++++++++++"
	while IFS=, read number name code; do
  		echo "name: $name, number: $number, code: $code"
  		OUTPUTPATH="downloaded_files/${code}_${number}_${name}"
  		rm -f "${OUTPUTPATH}.*"
  		wget -c "https://www.blackrock.com/ca/individual/en/$number/fund-download.dl" -O "${OUTPUTPATH}.xls"
  		cat "${OUTPUTPATH}.xls" | perl -0777 -pe 's#</Style>#</ss:Style>#sg' > "${OUTPUTPATH}2.xls"
  		ssconvert -S "${OUTPUTPATH}2.xls" "${OUTPUTPATH}.csv"
  		rm -f "${OUTPUTPATH}.csv.0"
  		rm -f "${OUTPUTPATH}.csv.2"
  		rm -f "${OUTPUTPATH}.csv.3"
  		rm -f "${OUTPUTPATH}2.xls"	
  		rm -f "${OUTPUTPATH}.xls"
  		mv "${OUTPUTPATH}.csv.1" "${OUTPUTPATH}.csv"	
	done <"$f"
	echo "---------------------"
done