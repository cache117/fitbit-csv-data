# fitbit-csv-data
This is a tool to convert data downloaded from Fitbit in CSV Format. This will turn the data into easily manageable Java Objects.

##Obtaining a CSV file from Fitbit
Since you own all of your data in Fitbit, they allow you to download it from from their site. This can be done [here](https://www.fitbit.com/export/user/data).

##Usage
Run `mvn package` to build the executable jar. Then, you can run the program with the following command:

`USAGE: java -jar <jar-file> <fitbit-csv-file> <start-date> <end-date> [<json-output-directory>]`

Dates are in the format `yyyy-MM-dd`

##Limitations
- Currently, this only turns the csv files into Json readable import files which can be used by [this repository](https://github.com/cache117/wellness-auto-healthyme#importjson-file-format)
- The Java objects created do not reflect the data in the food logs. This is in progress. As such:
    - The Json will always report false for `fruit_veg_4_or_more_servings`
    - The Json will always report false for `water_5_or_more_cups`
