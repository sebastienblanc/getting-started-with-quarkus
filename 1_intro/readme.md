# Creating a new Quarkus App

## With Maven 

`mvn io.quarkus:quarkus-maven-plugin:1.5.2.Final:create` 

## With VS Code

Make sure to have the Quarkus Extension installed. 

Open the command palette and search for "Quarkus ..." 

## Using code.quarkus.io

Browse to [the online project bootstrapper](https://code.quarkus.io/) 

## Inspect the project structure 

```
.
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── docker
    │   │   ├── Dockerfile.jvm
    │   │   └── Dockerfile.native
    │   ├── java
    │   │   └── org
    │   │       └── sebi
    │   │           └── HelloResource.java
    │   └── resources
    │       ├── application.properties
    │       └── META-INF
    │           └── resources
    │               └── index.html
    └── test
        └── java
            └── org
                └── sebi
                    ├── HelloResourceTest.java
                    └── NativeHelloResourceIT.java
```

## Running the project 

### In dev mode  
`mvn quarkus:dev` 

Try updating the code while the project is running and see what happens. 

### Building a jar

`mvn clean package` 

### Building a Native Binary

`mvn clean package -Pnative` 

