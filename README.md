# K Dimensional(k-d) Tree Web Service
The K-d tree is binary search tree (BST) data structure which is used to partition Euclidean space to organize and store the coordinate points into k dimensions. The K-d tree data structure has practical use in mathematical and sicentific n-dimensional modeling, in database technologies to efficiently implement range searches on multiple properties/dimensions of an object, etc.
This implementation of k-d tree webservice runs a webserver by opening and listening for the incoming client Socket connection on a default port 8080 of all of the network interfaces on the host computer. The client socket connection is subsequently transformed into the http GET and POST API. The web service creates and hosts a thread safe instance of k-d tree data structure and currently supports couple of operations on it exposed through GET and POST http request:
- Inserts a n-dimensional coordinate
- Searches for a coordinate
<br />
Also, this project demonstrate the key concepts on building a multi threaded Http web service from the raw socket connection by:

- Offloading client socket connection processing on a cached thread pool
- Reading the client socket's input stream data for the Http method name (e.g Get, Post etc.), headers, request body content etc. and converting them into the typed objects
- Processing the client request and sending the output stream to the client socket

##### Dependencies
- Java version >= 1.8

**High Level Design:**

![alternativetext](/kd-tree-webservice-design.png)


**Going forward:**
- Implement range search
- Support of coordinate delete through Http DELETE verb
- Packaging and deployments
- Loggings
- Keep improving

***References:***
https://en.wikipedia.org/wiki/K-d_tree

