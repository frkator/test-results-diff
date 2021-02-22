What
-
The purpose of the application is to compare _test reports_ for a given pair of directories. 

A _test report_ is:
 - any file in the file tree of a given directory whose name starts with `TEST-` and ends with `xml
` (case insensitive)
- the format of the file needs to conform to [`surefire-report-parser`](https://maven.apache.org/surefire/surefire-report-parser/summary.html) expected format

Upon parsing, standard set arithmetic operations are executed and reported per test case grouped by test suite:
 - intersection
 - left difference/relative complement
 - right difference/relative complement
 
For exact semantics of set arithmetic operations please check the immutable and comparable facades source code files.

The required test report format _seems_ to be a standard format produced by both maven and gradle plugins and also understood by different IDEs. There _may_ be a compatibility issue for older builds since there _seems_ to be two versions/xml schemas of the said format.

How
-
    ./gradlew clean shadowJar
    java -jar build/libs/test-results-diff-all.jar src/test/resources/byte-buddy-1 src/test/resources/byte-buddy-2 

Setting system properties or having a file named `default.properties` in the current directory will override default properties.
For details see file `src/resources/help.txt` and for defaults see `src/resources/default.properties`.

Any error with CLI/program arguments will invoke `java.lang.System#exit`. 

Why
-
Simplified analysis of test reports between builds with a single change:
 - different implementations of components
 - compiler upgrade
 - version bumps
 
 Notice
 -
 [byte-buddy](https://github.com/raphw/byte-buddy) test reports are used to test the functionality.