# Project

### André Almeida (up202010520), Sara Daniela Sousa (up201504217), Inês Rocha (up201606266)
#### Faculdade de Ciências (2021)


## 1 Distributed Ledger

In our distributed leger we implemented a blockchain based on the proof of work. Proof of work is a protocol that validates transactions. This protocol is used during the mining process. These miners compete between themselves to be the first to validate the transactions. These transactions are then used to create a new block that is added to the blockchain. The first person to complete this process is the winner and receives a reward. 
In our program each block has 4 transactions and the miners have to find the correct hash of the block. They need to perform several computations that create a hash with a determinate number of zeros in the beginning.
These types of computations get exponentially more expensive with the number of zeros required by the blockchain in the beginning of the hash [1], so this serves an incentive to the miners to not validate false transactions. If a block has false transactions it will be discarded and the miners will lose the reward and the computational value that was needed to find the hash. In conclusion, this protocol allows for trust between unknown parts given that dishonest parties will not be able to profit from cheating the system.

### 1.1 Architecture

The architecture of our blockchain is described in the image 1. 
Each transaction has the public key of the sender and the receiver, the coins that are being transferred, the signature of the sender to validate the user and a timestamp to help to convert to a hash. These coins can’t be divided in smaller pieces, that means that they can only be whole numbers.
The blocks of our blockchain are composed of 4 transactions. The blocks also have the previous and the actual hashes and a nonce that the miners will use to mine the block. Each block will also have a hash and an id.
Finally our blockchain will have a list of block already mined and validated and a queue of transaction waiting to be validated. This queue is implemented as a ”first in first out”, this allows us to mine the transactions in order that they were created, thus creating a more fair environment for our users.
A wallet will be created for each user. It has a private and a public key that is generated when the wallet is created. To check the balance faster, instead of traversing all the blockchain to find the transactions referring that user, we will have a variable that tells us the last block that was checked so that when we inspect the balance, we can use the older value of the balance and go through the last block that was checked until the end of the blockchain.
Lastly when a wallet is created we add a miner class to the user. This class has the public and private key of the user, a boolean that the user can control to stop and start the mining process and a variable that controls the threads that are created to accelerate mining. This variable is also controlled by the user.



![image](https://user-images.githubusercontent.com/44119905/144411801-ac48e3ab-edf8-4d3a-be65-2a7bc13e7ece.png)
Figure 1: Architecture of the blockhain

### 1.2 Genesis Block

When we create the blockchain, we don’t have transactions to mine because the users don’t have the coins to transfer between themselves, so we started the chain with genesis blocks. This block is mine by what we call a super user. This user mines the blocks and when it is concluded, he will give coins to new users.

## 2 Peer-to-Peer (P2P)

### 2.1 Kademlia algorithm

For the peer-to-peer network we tried to implement the Kademlia algorithm. This algorithm allows us to have a decentralized peer-to-peer computer network based on distributed hash tables. The decision to have a decentralized network is that with this type of network we remove the single point failure, making the network more robust [2].
The Kademlia algorithm is based on this paper [3]. Each node has an id, an address node, a routing table and an API to call other nodes. They define the routing table as a list of buckets. These buckets contain information of what nodes a single node knows. 
All peer-to-peer algorithms have a distance function that tells each node how far away another node is. The function is not calculating the physical distance between the nodes but instead the distance based on XOR. With this type of function, every node will have a distance of zero to itself and given two nodes their distances will be symmetric, the distance from A to B will be the same as B to A.

### 2.2 gRPC

To help the construction and implementation of the Kademlia network we used Remote Procedure Calls (RPC). For this we used a framework that can run in any environment. It can efficiently connect services in and across data centers with pluggable support for load balancing, tracing, health checking and authentication.
We implemented protocol buffers to help create the necessary code for the implementation of the gRPC.

## 3 Auction Mechanisms

This auction would function in the following way. When a user sells something, it will create a listing for that object and then other users will take part in an auction. If this auction was implemented without a filtering system, the users would receive multiple notifications.
To prevent users from receiving updates form auctions that they have no interest is receiving, we will create channels for each auction. This channel will have a hash table with all the users that choose to subscribe to that auction. When an alteration occurs in the auction, all users that subscribe to that channel will receive an update. When the auction closes, the winner will have to make a transaction to the seller and when the seller checks that the transaction was valid, the object will be sent to the buyer. Do to the time constrains we didn’t implement this part of the project.

## 4 Conclusions

Unfortunately we had several problems with the network regarding the buckets and routing tables. We tried to implement the network using gRPC and make an Auction Mechanism on top of it but as we didn’t finish the Kademlia network, the Auction couldn’t be finished. As we never worked in gRPC it caused this part of the project to be more complex than we anticipated.

## References

[1] Satoshi Nakamoto. “Bitcoin: A Peer-to-Peer Electronic Cash System”. In: (2008).

[2] Eng Keong Lua et al. “A survey and comparison of peer-to-peer overlay network
schemes”. In:IEEE Communications Surveys Tutorials7.2 (2005), pp. 72–93.doi:
10.1109/COMST.2005.1610546.

[3] Petar Maymounkov and David Eres. “Kademlia: A Peer-to-peer Information System
Based on the XOR Metric”. In: vol. 2429. Apr. 2002.isbn: 978-3-540-44179-3.doi:
10.1007/3-540-45748-8_5.


