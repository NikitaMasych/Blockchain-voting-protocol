#include "KeyPair.h"
#include <iostream>


int main()
{
	KeyPair keypair;
	keypair.genKeyPair();
	keypair.printKeyPair();
    return 0;
}
