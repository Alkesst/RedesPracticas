#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <iostream>

struct InfoCliente {
    int socket;
};


class Cliente {
private: 
    struct InfoCliente infoCliente;

public:
    Cliente(const char* inet);
    void ConnectToServer(const char* inet);
};