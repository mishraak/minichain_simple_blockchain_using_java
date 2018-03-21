#This is simply a rewrite of Naivechain developed originally in JavaScript

url(https://github.com/lhartikk/naivechain)

Clone the repo, and run:

```
mvn clean install 
```

Run your node:

`java -jar ./target/<naivechain-jar-file>`


### Adding Peer requires sending a POST request to /peer endpoint with a body that contains URL of peer followed by /send

POST REQ:

url="http://localhost:8080/peer" 
body = "http://peeraddress:port/send"


### To generate a new block (transaction in the context of bitcoin), you have to send a HTTP POST request to /mine endpoint. (denoted "mining")

POST REQ:

url="http://localhost:8080/mine" 
 body = "This is my newly mined block."

### To explore the complete blockchain simply fire a HTTP GET request to /blocks endpoint

GET REQ:

url="http://localhost:8080/blocks"




