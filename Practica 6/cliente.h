#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <iostream>
#include <memory>
#include <string>


class Cliente {
private: 
    int clientSocket;
    int port;
    char* ipAddress;
public:
    Cliente(const char* inet, const char* port);
    void ConnectToServer(const char* inet);
    ssize_t SendMessage(std::string message);
    void ReceiveMessage(std::string message);
};