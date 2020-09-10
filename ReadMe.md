# Auto.Ria
#### Project created as test task for ["Auto Ria" website](https://auto.ria.com/uk/search/)

[Java](https://java.com/en/) and [Maven](https://maven.apache.org/download.cgi) are required for running the project. The framework uses [Selenium](https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java), [Webdriver Manager](https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager), [TestNG](https://mvnrepository.com/artifact/org.testng/testng) and [Allure](https://docs.qameta.io/allure/).

To run the whole project use the following command:
```
mvn clean test
```
To generate and see allure report use:
```
mvn allure:serve
```
