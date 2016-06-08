ReadMe

SP0PQ

The MST.java has prim1 code
The MST2.java has prim2 code

BinaryHeap.java has priority queue implementation of the Heap 
IndexedHeap.java has indexed priority queue

The runtime for prim1 was around 4 seconds on the big data set
prim2 was slightly better with runtime of 2 seconds.

Our observation tells that as in the prim2 only a queue of max vertices is created, so it is faster as number of vertices are smaller than the number of edges in almost all the graphs.

Also in case of prim1 there is a case when both vertices are already a part of the MST, then it causes a waste iteration. This is not in case of the prim2 algorithm.