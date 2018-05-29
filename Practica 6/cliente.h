#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <iostream>
#include <memory>
#include <cstring>
#include <sstream>
#include <cerrno>
#include <stdlib.h>


class Cliente {
private: 
    int clientSocket;
    int port;
    std::string ipAddress;
    std::string hostName;
public:
    Cliente(const char* inet, const char* port);
    void ConnectToServer(const char* inet);
    ssize_t SendMessage(std::string message);
    std::string ReceiveMessage();
    void SendEmail();
};