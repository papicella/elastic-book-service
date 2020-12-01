# Elastic Book Service - APM Demo

This demo shows how to build an spring boot APi using Open API. Follow the steps below to get this deployed to K8s and setup to be traced by Elastic APM

**Note: This demo is using a simple micro service deployed to K8s and we use Elastic Cloud (ESS) for our APM Server and Elasticsearch cluster. In this example we use GKE as the K8s provider**

## Prerequisites

* HTTPie installed - https://httpie.io/
* pack CLI - https://buildpacks.io/docs/tools/pack/

## Steps

- Clone project and change into directory as follows

```bash 
$ git clone https://github.com/papicella/elastic-book-service.git
$ cd 
```

- Login to docker hub as follows

```bash
$ docker login -u DOCKER-HUB-USER -p PASSWD
```

- Package using Cloud Native Buildpacks. Download pack CLI from here

_Note: This will take some time for the first build_

```bash
$ pack build DOCKER-HUB-USER/elastic-book-service:1.0 --builder paketobuildpacks/builder:base --publish --path ./
```

- Make sure your connected to your K8s cluster and run the following commands to create a K8s Secret and ConfigMap. Please replace values as shown below

* APM-TOKEN
* APM-SERVER-URL
* APM-SERVER-PORT

```bash
kubectl create secret generic apm-token-secret --from-literal=secret_token=APM-TOKEN
kubectl create configmap apm-agent-details --from-literal=service_name=elastic-book-service \
--from-literal=application_packages=com.example.springbookservice \
--from-literal=server_urls=https://APM-SERVER-URL:APM-SERVER-PORT
```

- Now we can deploy our service to K8s using the K8s YAML for deployment as follows

**YAML**

```yaml

```

**Deploy as Follows** 

```bash
$ kubectl apply -f ebs-deployment.yml
```

- Once deployed check if LB service IP address is available as follows

```bash
$ kubectl get svc
NAME                      TYPE           CLUSTER-IP       EXTERNAL-IP      PORT(S)          AGE
elastic-book-service-lb   LoadBalancer   10.131.253.69    35.197.186.117   80:30220/TCP     5d23h
```

- Run a script as follows to generate some basic traffic to the elastic book service

```http
$ ./generate-traffic.sh
Using IP as 35.197.186.117

HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 01 Dec 2020 10:36:18 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "author": "Peter Armstrong",
        "id": 1,
        "title": "Flexible Rails"
    },
    {
        "author": "Kyle Baley",
        "id": 2,
        "title": "Brownfield Application Development in .NET"
    },
    {
        "author": "Kyle Banker",
        "id": 3,
        "title": "MongoDB in Action"
    },
    {
        "author": "Christian Bauer",
        "id": 4,
        "title": "Java Persistence with Hibernate"
    },
    {
        "author": "Chris Richardson",
        "id": 5,
        "title": "POJO's In Action"
    }
]


HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 01 Dec 2020 10:36:18 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "author": "Peter Armstrong",
    "id": 1,
    "title": "Flexible Rails"
}


HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 01 Dec 2020 10:36:18 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "author": "Kyle Baley",
    "id": 2,
    "title": "Brownfield Application Development in .NET"
}


HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 01 Dec 2020 10:36:18 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "author": "Kyle Banker",
    "id": 3,
    "title": "MongoDB in Action"
}


HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 01 Dec 2020 10:36:19 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "author": "Christian Bauer",
    "id": 4,
    "title": "Java Persistence with Hibernate"
}
```

- Open up APM to verify the elastic book spring boot service has been discovered as shown below

![alt tag](https://i.ibb.co/K7JVnRm/ebs-apm-1.png)

![alt tag](https://i.ibb.co/BnjXfD0/ebs-apm-2.png)

![alt tag](https://i.ibb.co/SJsVrT3/ebs-apm-3.png)

![alt tag](https://i.ibb.co/YWPYZ0J/ebs-apm-4.png)


<hr />
Pas Apicella [pas.apicella at elastic.co] is an Solution Architect at Elastic APJ  