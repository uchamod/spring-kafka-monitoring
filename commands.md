#### UP *docker-compose.yml* file
``docker compose up -d``

#### Down *docker-compose.yml* file
``docker compose down``

#### Find running containers
``docker ps``

#### Check current kafka topics
``docker exec -it <kafka-container-name> kafka-topics --bootstrap-server localhost:9092 --list``

#### Read all messages from specific topic
``docker exec -it kafka kafka-console-consumer 
  --bootstrap-server localhost:9092 
  --topic topic-name 
  --from-beginning``

#### Send a message directly using console producer
``docker exec -it kafka kafka-console-producer --bootstrap-server localhost:9092 --topic topic-name``

``Ex-: {"productId":"4f4689b6-6750-4b91-8492-b6a44b3b708d","quantity":1,"increment":false} using json format``
