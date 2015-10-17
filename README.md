# janala2-gradle
<a href="https://travis-ci.org/zhihan/janala2-gradle"><img src="https://travis-ci.org/zhihan/janala2-gradle.svg?branch=master"></img></a>

Migrate CATG (janala2) library to use Gradle and upgrade some dependencies. Branched from https://github.com/ksen007/janala2

Here is more information on the background of the project.
https://www.youtube.com/watch?v=9lEvPwR7g-Q

### Major changes
* Support Java 8
* Upgrade ASM library to 5.0.4
* Remove dependency on GNU trove
* Unit tests.

This is a working repository. The changes will be merged back to the main repository. I will keep this repository updated to experiment some ideas with.

## Requirements
* Gradle 2
* Python 2
* CVC4 (http://cvc4.cs.nyu.edu/web/)
* Java 1.8

## How to compile
In the root of the repository, do 

    gradle build
    
In addition, the project has a number of unit tests written in Groovy. To see the current test coverage. Do the following

    gradle jacocoTestReport
    
And then look at the test coverage report in build/reports.

## Integration tests
The integration tests from the original CATG repo are now included in this repo. To run these tests, 
first download a copy of asm-all-5.0.4.jar, automaton-1.11-8.jar and put them in the /lib directory.

At the root directory, invoke gradle to compile the test classes.

    gradle integrationClasses

Go to /scripts directory and do the following.

    ./setup.sh
    
Run the integration tests by invoking the following command from /scripts directory.

    python testall.py

Now a number of integration tests are failing. I am still investigating why.

## More integration tests
More integration tests are being added. These tests require a working version of CVC4 in your path.
To run these tests, do the following.

    gradle integrationTest
