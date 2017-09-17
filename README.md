# monthly-payslip. MYOB Coding Exercise for MYOB 2017

[![Build Status](https://travis-ci.org/slavau/monthly-payslip.svg?branch=master)](https://travis-ci.org/slavau/monthly-payslip)
[![Coverage Status](https://coveralls.io/repos/github/slavau/monthly-payslip/badge.svg)](https://coveralls.io/github/slavau/monthly-payslip)

## Prerequisite
- Java 8

## Quick start

1. Clone the project

2. Build using the following command from project root:
   > ./gradlew clean build

3. Go to the following folder:
   > cd build/libs/

4. To run for a single line input (Linux\MacOS):
   > echo "David,Rudd,60050,9%,01 March – 31 March" | java -jar monthly-payslip-1.0.jar

5. To run for a multi line input (Linux\MacOS):
    5.1 prepare a file containing multiple lines of the epmloyees data in the CSV format, e.g.:
    ```csv
        David,Rudd,60050,9%,01 March – 31 March
        Ryan,Chen,120000,10%,01 March – 31 March
    ```
    5.2 run application with input file (Linux\MacOS):
    > cat employees.txt | java -jar monthly-payslip-1.0.jar

By default, application output goes to StdOut in the CSV format. Can be easily redirected to a file for further processing

## Assumptions

* Calculations are only done for complete months. Partial calculations, like per day or per week or since a specific day during the month are not supported in this version
* Only using tax rates for 2012-13 financial year
* CSV file header is not supported
* If input csv line is not in the right format, program quits



