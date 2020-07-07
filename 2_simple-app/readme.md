# A Complete Quarkus Application , step by step

## Create a new Quarkus project 

`mvn io.quarkus:quarkus-maven-plugin:1.5.2.Final:create`

## Start the project 

`mvn quarkus:dev` 

## Add a new endpoint 

```
    @GET
    @Path("/fr")
    @Produces(MediaType.TEXT_PLAIN)
    public String bonjour() {
        return "hello";
    }

```

Try out your new endpoint : `curl localhost:8080/hello/fr`

## Add Configuration 



