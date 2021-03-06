# CovidTracker

This is the coronavirus infections counting app.

## Features

The application shows information about new and old infections from around the world by retrieving data from a CSV file,
the app also uses the world map.


Link to the CSV file:
Data source used: Novel Coronavirus (COVID-19) Cases, provided by JHU CSSE:
https://github.com/CSSEGISandData/COVID-19

JavaScript library used to display information on a map: 
https://leafletjs.com/


## Technologies

* Java
* HTML/CSS/JavaScript
* Thymeleaf
* Spring Framework 
* Spring Boot
* JUnit

## Prerequisites

The following items should be installed in your system:

* Java 8 or newer
* git command line tool
* Your preferred IDE


### Steps
1) On the command line
    ```
    git clone https://github.com/Kamylek27/CovidTracker
    ```
2) Inside Eclipse or STS
    ```
    File -> Import -> Maven -> Existing Maven project
    ```

    Then either build on the command line `./mvnw generate-resources` or using the Eclipse launcher (right click on project and `Run As -> Maven install`) to generate the css. Run the application main method by right clicking on it and choosing `Run As -> Java Application`.

3) Inside IntelliJ IDEA
    In the main menu, choose `File -> Open` and select the Petclinic [pom.xml](pom.xml). Click on the `Open` button.

    CSS files are generated from the Maven build. You can either build them on the command line `./mvnw generate-resources` project then `Maven -> Generates sources and Update Folders`.

    A run configuration named `CovidTracker` should have been created for you if you're using a recent Ultimate version. Otherwise, run the application by right clicking on the `CovidTracker` main class and choosing `Run 'CovidTracker'`.

4) Navigate to Petclinic

    Visit [http://localhost:8080](http://localhost:8080) in your browser.
