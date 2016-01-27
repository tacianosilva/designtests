## Welcome to the Design Tests wiki!

This project contains experiments with design tests using DesignWizard API. This project contains scripts and programs for running design tests for a set of systems that use Hibernate/JPA and it been selected from github.

### Version before the separation of the projects - DesignTests and DesignTestMiner

v-0.8.9: Version that contains the classes with the Design Rules for hibernate and classes for GitHub Project Analysis. Classes related to github project analysis will be moved to the designtestminer project. Version before the separation of the projects.

### Continuos Integration with [Travis](https://travis-ci.org)

[![Build Status](https://travis-ci.org/tacianosilva/designtests.svg)](https://travis-ci.org/tacianosilva/designtests)

### Instructions to Eclipse users

This project is deployed as an Eclipse project with Maven. In order to import
*DesignTests* properly, consider the following steps:

1. Clone the project from the official GitHub repository
2. On *Eclipse*, install Git.
3. On *Eclipse*, install Maven.
2. On *Eclipse*, import a Java project of the cloned
folder. *Eclipse* will understand the configuration on the existing project.

### Instructions to Maven users

The DesignWizard project has the ASM dependency considered optional in the your pom.xml file. So, to use the DesignWizard API is necessary to inform the following dependencies:

        <dependency>
            <groupId>org.designwizard</groupId>
            <artifactId>designwizard</artifactId>
            <version>1.4</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>asm</groupId>
            <artifactId>asm</artifactId>
            <version>3.1</version>
        </dependency>


## History

For detailed changelog, see [Releases](https://github.com/tacianosilva/designtests/releases).