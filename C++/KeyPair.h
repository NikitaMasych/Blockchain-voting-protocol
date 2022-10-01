#pragma once
#include <cstdint>

class KeyPair {
private:
	uint_fast8_t* private_key;
	uint_fast8_t* public_key;
	size_t prkey_len, pbkey_len;
public:
	void genKeyPair();
	void printKeyPair();
};
