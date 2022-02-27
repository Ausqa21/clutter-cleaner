# clutter-cleaner
A simple Java application that can move files/directories based on a pattern into a specified directory

To build
--------
    $ mvn clean package

To run
------
    $ java -jar target/clutter-cleaner-<version>.jar -m <pattern>:<target_directory> <source_directory>
where
+ **version** = current version of application
+ **pattern** = a pattern in the name of a file/directory, eg. "gh" for a file named "NASAghteam.txt"
+ **target_directory** = the directory you want the files to be moved into. Will be created if it doesn't exist
+ **source_directory** = the directory where the files you want to move are
