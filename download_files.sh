#!/bin/bash

echo "++++++++++++++++++++++++++++++++++++++ Vanguard start ++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
for f in "${SECURITIESDATA_PROJECT_ROOT}"/securities_data_sources/vanguard*.txt
do
	echo "*********************"
	echo "File: $f"
	echo "+++++++++++++++++++++"
	while IFS=, read port name code; do
	    name=$(sed 's/ /+/g' <<< "${name}")
	    codelength=${#code}
	    echo "$codelength"
	    if [ "$codelength" -eq "2" ]; then
	        echo "code length is 2"
	        code="${code}_"
	    fi
  		OUTPUTPATH="${VANGUARD_DATA_DIR}/vanguard_${code}_${port}_${name}"
  		rm "${OUTPUTPATH}"*
        echo "${OUTPUTPATH}"
        curl 'https://www.vanguardcanada.ca/individual/mvc/download/ETFPriceHist' -H 'Accept-Encoding: gzip, deflate, br' \
            -H 'Accept-Language: en-US,en;q=0.8,ru;q=0.6' -H 'Upgrade-Insecure-Requests: 1' \
            -H 'Content-Type: application/x-www-form-urlencoded' \
            -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' \
            -H 'Cache-Control: max-age=0' \
            -H 'Connection: keep-alive' \
            --data 'port='"$port"'&assetCode=EQUITY&startDt=27-09-2016&endDt=11-10-2016&fundName=foo&column1=Date&column2=Market+price+%28CAD%29&column3=NAV+%28CAD%29&footnote=%C2%A0' \
            --compressed \
            > "${OUTPUTPATH}.csv"

        tail -n+4 "${OUTPUTPATH}.csv" > "${OUTPUTPATH}2.csv"
        perl -pi -e 's/\$//g' "${OUTPUTPATH}2.csv"
        perl -pi -e 's/"="//g' "${OUTPUTPATH}2.csv"
        perl -pi -e 's/"""/"/g' "${OUTPUTPATH}2.csv"
        rm "${OUTPUTPATH}.csv"
        mv "${OUTPUTPATH}2.csv" "${OUTPUTPATH}.csv"

  	done <"$f"
  	echo "---------------------"
	echo ""
done
echo "-------------------------------------- Vanguard end -----------------------------------------------------------"

echo "++++++++++++++++++++++++++++++++++++++ iShares start ++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
for f in "${SECURITIESDATA_PROJECT_ROOT}"/securities_data_sources/ishares*.txt
do
	echo "*********************"
	echo "File: $f" 
	echo "+++++++++++++++++++++"
	while IFS=, read number name code; do
  		OUTPUTPATH="${ISHARES_DATA_DIR}/${code}_${number}_${name}"
        rm "${OUTPUTPATH}"*
  		wget -c "https://www.blackrock.com/ca/individual/en/$number/fund-download.dl" -O "${OUTPUTPATH}.xls"
  		cat "${OUTPUTPATH}.xls" | perl -0777 -pe 's#</Style>#</ss:Style>#sg' > "${OUTPUTPATH}2.xls"
  		ssconvert -S --export-type=Gnumeric_stf:stf_csv "${OUTPUTPATH}2.xls" "${OUTPUTPATH}.csv"
  		
  		for CSVFILE in ${OUTPUTPATH}.csv.*; 
  		do
  			CSVFILE_FINAL=$(sed 's/.\{2\}$//' <<< "${CSVFILE}")
  			# https://stackoverflow.com/questions/11287861/how-to-check-if-a-file-contains-a-specific-string-using-bash
  			grep -iq '^"as of"' "${CSVFILE}"; [ $? -eq 0 ] && mv "${CSVFILE}" "${CSVFILE_FINAL}"|| rm -f "${CSVFILE}"
  		done;

  		rm -f "${OUTPUTPATH}2.xls"
	done <"$f"
	echo "---------------------"
	echo ""
done
echo "-------------------------------------- iShares end -----------------------------------------------------------"



java -jar target/scala-2.11/isharesetfdatadownload-assembly-1.0.jar ${ISHARES_DATA_DIR}