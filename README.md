# janala2-gradle
<img src="https://travis-ci.org/zhihan/janala2-gradle.svg?branch=master"></img>

Migrate CATG (janala2) library to use Gradle and latest libraries (ASM, etc.) 

This is a working repository to migrate CATG tool to use Gradle and update the dependencies. 

## Requirements
Gradle 2.

## How to compile
In the root of the repository, do 

    gradle build
    
In addition, the project has a number of unit tests written in Groovy. To see the current test coverage. Do the following

    gradle jacocoTestReport
    
And then look at the test coverage report in build/reports.
