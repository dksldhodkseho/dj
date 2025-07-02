# 

## Model
www.msaez.io/#/56288373/storming/0cffce87ecb991dfe2687f4ebb72eb0e

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- book
- writer
- user
- subscription
- bestseller
- openai
- point


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- book
```
 http :8088/books bookId="bookId"title="title"content="content"writerNickname="writerNickname"writerId="writerId"coverUrl="coverUrl"status="status"
```
- writer
```
 http :8088/writers writerId="writerId"approvalStatus="approvalStatus"
```
- user
```
 http :8088/users userId="userId"email="email"nickname="nickname"passwordHash="passwordHash"
```
- subscription
```
 http :8088/subscriptions userId="userId"subscriptionStatus="subscriptionStatus"subscriptionExpiryDate="subscriptionExpiryDate"
```
- bestseller
```
 http :8088/bestSellers bestsellerId="bestsellerId"bookId="bookId"viewCount="viewCount"selectedStatus="selectedStatus"selectedAt="selectedAt"
```
- openai
```
 http :8088/openAis requestId="requestId"bookId="bookId"prompt="prompt"coverUrl="CoverUrl"
```
- point
```
 http :8088/points userId="userId"amount="amount"
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
