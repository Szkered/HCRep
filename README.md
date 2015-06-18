# HCRep

## Basic survival skills in mavenland

Download:

```Bash
git clone https://github.com/Szkered/Hazelcast-CIBGrp5.git
```

Build:

```Bash
mvn clean package
```

Test:

```Bash
mvn test
```

Run: double click the jar file in target directory, or in command line

```Bash
java -jar $module-name/target/$module-name-1.0-SNAPSHOT.jar
```

Directory Structure (Partial):
```
.
|-- Client
|   |-- dependency-reduced-pom.xml
|   |-- pom.xml
|   |-- src
|   |   |-- main
|   |   |   `-- java
|   |   |       `-- CIBGrp5
|   |   |           `-- App.java
|   |   `-- test
|   |       `-- java
|   |           `-- CIBGrp5
|   |               `-- AppTest.java
```

Main program resides in `App.java`, and a sample unit test is in `AppTest.java`. `pom.xml` is the config file for maven.

## More about Maven (you can safely skip this)

`package` and `test` are actually phases, which are items in the build lifecycle, an ordered sequence of phases.
List of essential phases in build lifecycle: validate -> compile -> test -> package -> integration-test -> verify -> install ->  deploy. 
When you do `mvn $phase-name`, Maven will execute every phase in the sequence up to and including the one defined. So if you do `mvn package`, `validate`, `compile` and `test` phases will all be executed before make the jar file.

