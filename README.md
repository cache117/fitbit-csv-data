# fitbit-csv-data
This is a tool to convert data downloaded from Fitbit in CSV Format. This will turn the data into easily manageable Java Objects.

This codebase uses Java and Maven.

##Obtaining a CSV file from Fitbit
Since you own all of your data in Fitbit, they allow you to download it from from their site. This can be done on the [Data Export tab ](https://www.fitbit.com/export/user/data) of Settings.  Ensure to select the "Time period" that you want to download (you are limited by Fitbit to 31 days at a time), and select all options under "Data": Body, Foods, Activities, and Sleep. The conversion might fail if you fail to select all four options. Ensure that the "File Format" is CSV, and click "Download". Use that file as the `<fitbit-csv-file>` in the Usage below.

##Usage
Run `mvn package` to build the executable jar that contains this program. Then, you can run the program with the following command:

`USAGE: java -jar <jar-file> <fitbit-csv-file> <start-date> <end-date> [<json-output-directory>]`

Dates are in the format `yyyy-MM-dd`

##Limitations
- Currently, this only turns the csv files into Json readable import files which can be used by [this repository](https://github.com/cache117/wellness-auto-healthyme#importjson-file-format).
    - The Java objects created do not reflect the data in the food logs. This is in progress. As such:
        - The Json will always report false for `fruit_veg_4_or_more_servings`. It currently cannot interpret the food logs to determine what is fruit and vegatable and not.
        - The Json will always report false for `water_5_or_more_cups`. As soon as the Java objects can be properly created for Food Logs, this will be easily implemented.
