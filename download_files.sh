#!/bin/bash

for f in ./blackrock_ishares/*.txt
do
	echo "*********************"
	echo "File: $f" 
	echo "+++++++++++++++++++++"
	while IFS=, read number name code; do
  		echo "name: $name, number: $number, code: $code"
  		rm -f "downloaded_files/${code}_${number}_${name}.xls"
  		wget -c "https://www.blackrock.com/ca/individual/en/$number/fund-download.dl" -O "downloaded_files/${code}_${number}_${name}.xls"
	done <"$f"
	echo "---------------------"
done