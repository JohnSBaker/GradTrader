## GradTrader

A proof-of-concept FX trading application for Scott Logic grad week.

###### Clients:
* desktop and mobile:
 * view live (mocked) Price feeds for FX pairs
 * submit RFQs
 * place Trades
 * view Trade list
* desktop only:
 * Price history chart

###### Server:
* Web Sockets:
 * live (mocked) Price feeds
 * Trade list
* REST:
 * RFQs
 * Trades
 * Price history

#### Tech Stack

###### Server
* Dropwizard - Java server framework
 * Jetty - HTTP
 * Jersey - REST
 * Jackson - JSON
* Guice - Java dependency injection
* Atmosphere - web sockets
* MongoDB - NoSQL persistence
* Log4J - logging
* JUnit - testing

###### Client
* React with Redux - SPA framework
* ESLint - linting
* Sass - CSS preprocessor
* Babel - transpile ES6
* Mocha and Chai - testing

###### Tools
* Codenvy - cloud IDE
* Github - source control
* Wercker - continuous integration
* Heroku - hosting
* Gradle - build tool
* Webpack - bundler
