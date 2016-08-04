# fitbit-csv-data
This is a tool to convert data downloaded from Fitbit in CSV Format. This will turn the data into easily manageable Java Objects.

##Obtaining a CSV file from Fitbit


##Usage
Run `mvn package` to build the executable jar. Then, you can run the program with the following command:

`USAGE: java -jar <jar-file> <fitbit-csv-file> <start-date> <end-date> [<json-output-directory>]`

Dates are in the format `yyyy-MM-dd`

##Limitations
Currently, this only turns the csv files into Json readable import files which can be used by [this repository](https://github.com/cache117/wellness-auto-healthyme)