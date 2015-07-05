## Welcome to the Design Tests wiki!

This project contains experiments with design tests using DesignWizard API. This project contains scripts and programs for running design tests for a set of systems that use Hibernate/JPA and it been selected from github.

### Instructions to Eclipse users

This project is deployed as an Eclipse project with Maven. In order to import
*DesignTests* properly, consider the following steps:

1. Clone the project from the official GitHub repository
2. On *Eclipse*, install Git.
3. On *Eclipse*, install Maven.
2. On *Eclipse*, import a Java project of the cloned
folder. *Eclipse* will understand the configuration on the existing project.

### Instructions to Maven users

The API GXL doesn't distributed in the maven repositories and it needs to be
installed in local repository. The maven commando for install:

mvn install:install-file -Dfile=gxl.jar -DgroupId=net.sourceforge.gxl -DartifactId=gxl -Dversion=0.92 -Dpackaging=jar

Download GXL:  http://gxl.sourceforge.net/