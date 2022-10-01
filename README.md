MAE-VOTING GOVERNMENT SCALE PROTOCOL

* Product is developed for facilitating secure, transparent and fair e-voting, using blockchain. 

* Current version works for various government elections, though it is relatively easy to modify its use for various scales.
 
* System consists of external auxiliary server, blockchain database and users device.

* Current version's user is citizen of some particular country, with corresponding passportID.

* Solution has class Server which simply emulates required functions and storage, hence it can be implemented variously.

Protocol description:

1. Check whether the user is eligible to vote.
2. User creates symmetric key (AES_GBC) and encrypts his passportID, after that it gets hashed. - adress creation (privacy guarantee)
	* redo for non-govenment level
3. Server generates asymmetric key pair (RSA) and provides user with it's public content. 
4. User encrypts desired ballot with that key. (noone can see preliminary results and noone can get his/her own choice after voting) 
5. User creates asymmetric key pair (ECDSA/ecp256r1) and signs his encrypted choice. 
6. User creates operation sence is that the user sends his only vote, encrypted choice and choice signature to the server.
	* voted param is set to true when final block, including corresponding operation, gets added to blockchain
7. After the end of the voting, server publishes private choice decryption key corresponding to particular user address to the open source. 

This approach guarantees:

	• Eligibility: only legitimate voters can take part in voting.
	• Unreusability: each voter can vote only once.
	• Anonymity: no one can track user address to the particular person. 
	• Fairness: no one can obtain intermediate voting results.
	• Soundness: invalid ballots are detected and not taken into account during tallying.
	• Completeness: All valid ballots are tallied correctly.
	
* C++ folder is yet incomplete, however later I will realise this protocol there in order to achieve HIGH performance and more or less real implementation of the server and databases
