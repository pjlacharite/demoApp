

# demoApp
demoApp

TODO:
1- Write test/refactor to increase testability.
2- oAuth 1.0 inbound verification using a filter

Running the application from terminal
    mvn spring-boot:run

Running the application from terminal on a specific port
    mvn spring-boot:run -Drun.jvmArguments='-Dserver.port=9000'

Running the application on heroku
    heroku create
    git push heroku master

Checking the heroku logs
    heroku logs --tail