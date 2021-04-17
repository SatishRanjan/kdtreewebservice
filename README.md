# K Dimensional(k-d) Tree Web Service
The implementation of this k-d tree webservice runs a webserver by opening and listening for the incoming client Socket connection on port 8080 of the network interfaces of the host. The client socket connection is subsequently transformed into the http GET and POST API. The web service creates and hosts a thread safe instance of k-d tree data structure and currently supports couple of operations on it exposed through GET and POST http request:
- Inserts a n-dimensional coordinate
- Searches for a coordinate
**High Level Design:**

![alternativetext](/kd-tree-webservice-design.png)
