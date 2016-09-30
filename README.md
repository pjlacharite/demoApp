# demoApp
demoApp

TODO:
1- Sub entities are persisted as binary instead of table reference.
2- Implement Subscription Change End Point
3- Manage/Validate Subscriptions (Do not only store them)
4- oAuth 1.0 verification

Running the application from terminal
    mvn spring-boot:run

Running the application from terminal on a specific port
    mvn spring-boot:run -Drun.jvmArguments='-Dserver.port=9000'

Running the application on heroku
    heroku create
    git push heroku master

Checking the heroku logs
    heroku logs --tail