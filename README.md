# ECA APARTMENT VISITOR SERVICE

This Application is to notify apartment owner, flat owner or tenants about any visitor visits.

## Steps to Setup

**1. Clone the repository**

```bash
git clone https://github.com/MohammedIrfan777/eca.git
```

**2. Run the app using maven**

```bash
cd eca
cd eca-visitor
mvn spring-boot:run
```

That's it! The application can be accessed at `http://localhost:6093`.

You may also package the application in the form of a jar and then run the jar file like so -

```bash
mvn clean package
java -jar target/eca-visitor*.jar
```

# OR

Simply run the docker image container using docker

```bash
docker build -t ecavisitor:latest .
docker run -d -p 6094:6094 ecavisitor:latest
```

