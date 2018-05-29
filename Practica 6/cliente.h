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
    std::string debuggingTasks;
    std::vector<std::string> ParseReceivedMessage(const std::string &input);
    bool ShoulIContinue(const std::string &input, std::string &causeError);
    void SendEmail(const std::string &from, const std::string &to, const std::string &subject, const std::string &body);
    std::string ReceiveMessage();
    ssize_t SendMessage(const std::string &message);
    int to_int(char const *s);
public:
    Cliente(const char* inet, const char* port);
    void GetInfoToSend();
    void ConnectToServer(const char* inet);
};