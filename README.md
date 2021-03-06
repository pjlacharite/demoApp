
# demoApp

Demo Application tied to the AppDirect API.

Currently supported APIs

### 1. Subscription Create
/subscription/create?eventUrl={eventUrl}

### 2. Subscription Change
/subscription/change?eventUrl={eventUrl}

### 3. Subscription Notice 
/subscription/notice?eventUrl={eventUrl}

### 4. Subscription Cancel 
/subscription/cancel?eventUrl={eventUrl}

# Packaging and Running the application

Packaging and running the application
```bash
    mvn package && java -jar target/demoapp-0.0.1-SNAPSHOT.jar
```

Running the application from terminal using spring-boot
```bash
    mvn spring-boot:run
```

Running the application from terminal on a specific port
```bash
    mvn spring-boot:run -Drun.jvmArguments='-Dserver.port=9000'
```

Running the application on heroku
```bash
    heroku create
    git push heroku master
```

Checking the heroku logs
```bash
    heroku logs --tail
```