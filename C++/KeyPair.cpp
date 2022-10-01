#pragma comment(lib, "crypt32")
#pragma comment(lib, "ws2_32.lib")

#include <iostream>
#include <openssl/evp.h>
#include <openssl/pem.h>
#include "KeyPair.h"


void KeyPair::genKeyPair() {
	// initialise context with RSA
	EVP_PKEY_CTX* keyCtx = EVP_PKEY_CTX_new_id(EVP_PKEY_RSA, NULL);
	EVP_PKEY_keygen_init(keyCtx);
	// using 4096 bits key, generate it
	EVP_PKEY_CTX_set_rsa_keygen_bits(keyCtx, 4096);
	// store both private and public keys in
	EVP_PKEY* keypair = nullptr;
	EVP_PKEY_keygen(keyCtx, &keypair);
	// free context
	EVP_PKEY_CTX_free(keyCtx);
	
	// reserve space for writing private key
	BIO* pr_BIO = BIO_new(BIO_s_mem());
	PEM_write_bio_PrivateKey(pr_BIO, keypair, NULL, NULL, 0, 0, NULL);
	// get private key length
	KeyPair::prkey_len = BIO_pending(pr_BIO);
	// allocate enough memory for storing the key as a string
	KeyPair::private_key = static_cast<uint_fast8_t*>(malloc(KeyPair::prkey_len));
	// write key from BIO to private key string
	BIO_read(pr_BIO, KeyPair::private_key, KeyPair::prkey_len);

	// reserve space for writing public key
	BIO* pb_BIO = BIO_new(BIO_s_mem());
	PEM_write_bio_PUBKEY(pb_BIO, keypair);
	// get public key length
	KeyPair::pbkey_len = BIO_pending(pb_BIO);
	// allocate enough memory for storing the key as a string
	KeyPair::public_key = static_cast<uint_fast8_t*>(malloc(KeyPair::pbkey_len));
	// write key from BIO to private key string
	BIO_read(pb_BIO, KeyPair::public_key, KeyPair::pbkey_len);
}

void KeyPair::printKeyPair() {
	std::cout << "Private key: ";
	for (size_t i = 0; i != prkey_len; ++i) {
		std::cout << std::hex << static_cast<uint_fast16_t>(private_key[i]);
	}
	std::cout << "\nPublic key: ";
	for (size_t i = 0; i != pbkey_len; ++i) {
		std::cout << std::hex << static_cast<uint_fast16_t>(public_key[i]);
	}
}
