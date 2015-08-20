# janala2-gradle
<img src="https://travis-ci.org/zhihan/janala2-gradle.svg?branch=master"></img>

Migrate CATG (janala2) library to use Gradle and upgrade some dependencies. Branched from https://github.com/ksen007/janala2

### Major changes
* Support Java 8
* Upgrade ASM library to 5.0.4
* Remove dependency on GNU trove
* Unit tests.

This is a working repository. I am still hoping to merge it back to the main repository once the code changes are stable.

## Requirements
Gradle 2.

## How to compile
In the root of the repository, do 

    gradle build
    
In addition, the project has a number of unit tests written in Groovy. To see the current test coverage. Do the following

    gradle jacocoTestReport
    
And then look at the test coverage report in build/reports.

## Integration tests
This is still largely a manual process.

First, get a copy of the main repository of CATG from https://github.com/ksen007/janala2

Package the code in this repository by calling

    gradle jar

Replace ib/iagent.jar in the main repository with the jar file created in the build/libs/ directory.

In the main repository, replace the asm-all-3.3.1.jar with asm-all-5.0.4.jar

Change the concolic.py script to use the updated asm library.

Manually compile some of the test classes in src/tests in the main repository. 
