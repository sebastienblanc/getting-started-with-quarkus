# Deploy to the Cloud

For this section we will be using the `reverse-service`

## Building and pushing the Docker image

Let's add the needed extensions first : 

```

mvn quarkus:add-extension -Dextensions=quarkus-kubernetes,quarkus-container-image-jib

```

Now let's add some properties , note that you will have to use your own repository name here : 

```

quarkus.container-image.registry=quay.io
quarkus.container-image.group=sebastienblanc
quarkus.container-image.name=reverse-service
quarkus.container-image.tag=1.0-SNAPSHOT
quarkus.kubernetes.service-type=load-balancer

```

Make sure you are authenticated with your container registry (docker.io or qay/io ...). 

Now you can build and push your container image : 

`mvn clean package -DskipTests -Dquarkus.container-image.push=true`

## Depploying to Kubernetes

Make sure you are connected to your Kubernetes Cluster 

`kubectl apply -f target/kubernetes/kubernetes.yml`

