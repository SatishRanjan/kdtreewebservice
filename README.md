# K Dimensional(k-d) Tree Web Service
The K-d tree is binary search tree (BST) data structure which is used to partition Euclidean space to organize and store the coordinate points into a k dimensions. The K-d tree data structure has practical use in mathematical and sicentific n-dimensional modeling, in database technologies to efficiently implement range searches on multiple properties dimensions of an object etc. 
This implementation of this k-d tree webservice runs a webserver by opening and listening for the incoming client Socket connection on port 8080 of the network interfaces of the host. The client socket connection is subsequently transformed into the http GET and POST API. The web service creates and hosts a thread safe instance of k-d tree data structure and currently supports couple of operations on it exposed through GET and POST http request:
- Inserts a n-dimensional coordinate
- Searches for a coordinate

##### Dependencies
- Java version >= 1.8

**High Level Design:**

![alternativetext](/kd-tree-webservice-design.png)


**Going forward:**
- Implement range search
- Support of coordinate delete through Http DELETE verb
- Logging
- Keep improving

***References:***
https://en.wikipedia.org/wiki/K-d_tree

