## Report Generator Cli v1.0.0 :page_with_curl:

This project is a report generator of phones and their respective 
country. It counts how many are per country and shows if your input 
is an invalid number.

It was developed with Java, supporting 17 version, Maven 3.9.0 and has 
preliminary unit tests with Junit4.

To try a run you must have a countryCodes.txt file next to `report-generator-cli.jar` and a file with your phones. There's a sample of each one above for you to try it :blush:

###Usage

For basic usage you can just type:

`java -jar report-generator-cli.jar file.txt`

where file.txt is your phone file placed in the very same directory. If is not placed there, please
indicate the directory path until reach it:

`java -jar report-generator-cli.jar ../file.txt`

:point_right: Do not forget to place the file with country codes, that must be named `countryCodes.txt` near to the jar, otherwise it will return a basic help guide like the one below:

    usage: java -jar report-generator.jar [INPUT FILE] [OPTIONS]
    -h,--help           show help
    -i,--input <arg>    input phone file
    -S,--show-invalid   show invalid phones
    -v,--version        show version

If you prefer to indicate your input file, you can use the option `-i` before your phone file, here `file.txt`:

`java -jar report-generator-cli.jar -i file.txt`

The basic output is similar as this: 
    
    Netherlands=7
    Cuba=1
    Brazil=1
    Portugal=1
    United Kingdom=1
    Venezuela=1

ordered by the country with more phones to the one with less. If is important to you see the remaining countries and the invalid numbers, just use the option `-S`:

`java -jar report-generator-cli.jar -i file.txt -S`   
or  
`java -jar report-generator-cli.jar file.txt -S`  

and the return should be similar as this one:

    INVALID=8
    Netherlands=7
    Cuba=1
    Brazil=1
    Portugal=1
    United Kingdom=1
    Venezuela=1
    Papua New Guinea=0
    Cambodia=0
    Paraguay=0
    (...)

This CLI is still in progress and free to receive as many contributions as possible. 

Enjoy it :smiley:

