# Solar Car Performance Modeling Application
The Solar Car Modeling Application is designed to assist with energy management during the American Solar Challenge.
The application takes the Google Maps route file provided by the ASC and simulates solar car performance over legs of the race, 
incorporating location data along with weather and elevation along the route to estimate energy usage.
The application runs in the lead car during the race and assists with race strategy that can be communicated to the solar car driver.

## Prerequisites

### Java
[Oracle Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) is required to run this software.

### IDE
[IntelliJ IDEA](https://www.jetbrains.com/idea/) was used in the development of this software.

It is strongly encouraged that this IDE is used for future work on this project.

Students can receive the full Jetbrains product line with the [Free Student Account](https://www.jetbrains.com/student/)

## Tests
"Tests in seniorDesign" is an option in the run configurations found in IntelliJ IDEA.

This will run all of the tests within the project.

## APIs
 * [Open Weather Map](https://openweathermap.org/api) - Weather data
 * [Google Maps](https://developers.google.com/maps/) - Display and elevation data

## Built With
 * [GMapsFX](http://rterp.github.io/GMapsFX/) - For showing a Google Map in a JavaFX window
 * [JavaAPIforKML](https://labs.micromata.de/projects/jak.html) - For parsing the kml file
 * [Open Weather Map Java library](https://github.com/migtavares/owmClient) - For retrieving Open Weather Map data

## Directory Hierarchy
```
├── carconfig
├── doc
│   ├── standards
│   └── stories
├── kml
├── legs
│   └── ASC 2016
├── lib
│   └── sunseeker
└── src
    ├── config
    ├── google
    ├── main
    ├── META-INF
    ├── models
    ├── SpeedLimitTool
    │   └── fxml
    ├── Tests
    │   ├── models
    │   └── utilities
    ├── ui
    │   ├── controllers
    │   └── fxml
    ├── utilities
    └── weather
```
### carconfig
Storage place for car configs that can be loaded by the application.
### doc
Documentation for the software. This includes the install, maintenance and user guides.
### kml
Storage place for the route kml file that is retrieved from ASC.
### legs
Folder where auto-generated route leg files will reside.
### lib
Folder where project library files are stored.
### src
The source code directory for the project
