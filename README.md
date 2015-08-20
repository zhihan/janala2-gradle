# janala2-gradle
<a href="https://travis-ci.org/zhihan/janala2-gradle"><img src="https://travis-ci.org/zhihan/janala2-gradle.svg?branch=master"></img></a>

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
The integration tests from the original CATG repo are now included in this repo. To run these tests, 
first download a copy of asm-all-5.0.4.jar and put it in /lib directory.

At the root directory, invoke gradle to compile the test classes.

    gradle integrationClasses

Go to /scripts directory and do the following.

    ./setup.sh
    
Run the integration tests by invoking the following command from /scripts directory.

    python testall.py

Now a number of integration tests are failing. I am still investigating why.

## More integration tests
More integration tests are being added. To run these tests, do the following.

    gradle integrationTest