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
#include <vector>


class Cliente {
private: 
    int clientSocket;
    int port;
    std::string ipAddress;
    std::string hostName;
    std::vector<std::string> ParseReceivedMessage(std::string input);
    bool ShoulIContinue(std::string input, char &causeError);
    void SendEmail(std::string from, std::string to, std::string subject, std::string body);
    std::string ReceiveMessage();
    ssize_t SendMessage(std::string message);

public:
    Cliente(const char* inet, const char* port);
    void GetInfoToSend();
    void ConnectToServer(const char* inet);
};