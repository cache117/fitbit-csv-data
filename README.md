# fitbit-csv-data
This is a tool to convert data downloaded from Fitbit in CSV Format. This will turn the data into easily manageable Java Objects.

##Usage
Run `mvn package` to build the executable jar. Then, you can run the program with the following command:
`USAGE: java -jar <jar-file> <fitbit-csv-file> [<json-output-directory>]`

##Limitations
Currently, this only turns the csv files into Json readable import files which can be used by [this repository](https://github.com/byu-oit-appdev/wellness-auto-healthyme)