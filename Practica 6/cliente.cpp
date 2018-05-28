#include "cliente.h"


#define ENDLINE "\r\n"

Cliente::Cliente(const char* inet, const char* p) {
    // works
    std::string temp(inet);
    ipAddress = temp;
    Cliente::port = atoi(p);
    std::cout << "Creado el objeto cliente... Puerto: " << port << " ipAddress " <<  ipAddress
    << std::endl;
}


void Cliente::ConnectToServer(const char* inet) {
    // works
    clientSocket = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
    if(clientSocket != -1) {
        struct sockaddr_in server_address;
        server_address.sin_family = AF_INET;
        inet_aton(inet, &(server_address.sin_addr));
        server_address.sin_port = htons(25);
        server_address.sin_addr.s_addr = INADDR_ANY;
        if(connect(clientSocket, (struct sockaddr*) &server_address, sizeof(struct sockaddr_in)) == -1) {
            close(clientSocket);
            clientSocket = -1;
            std::cout << "No se ha podido conectar con el servidor..." << std::endl;
        } else {
            std::cout << "Conectado al servidor..." << std::endl;
            std::cout << inet << " " << server_address.sin_port << std::endl;
        }
    } 
}

ssize_t Cliente::SendMessage(std::string message) {
    message = message.append(ENDLINE);
    ssize_t sent = write(clientSocket, message.c_str(), message.length());
    if(sent == -1) {
        std::cout << "No se ha enviado bien el mensaje" << std::endl;
        std::cout << std::strerror(errno) << std::endl;
    }
    return sent;
}


int main(int argc, const char* argv[]) {
    // std::shared_ptr<Cliente> c = new Cliente("192.168.164.28");
    // std::shared_ptr<Cliente> c (new Cliente("192.168.164.28")); si pudiéramos usar c++11 pues jeje pero no xd
    if (argc < 3) {
        std::cout << "Necesito el hostname para conectarme..." << std::endl;
        exit(-1);
    }
    Cliente *c = new Cliente(argv[1], argv[2]);
    (*c).ConnectToServer(argv[1]);
    ssize_t status = (*c).SendMessage("EHLO EHLO mbpddealejandro.servidor.io");
    status = (*c).SendMessage("MAIL FROM: developers@go4me.org");
    status = (*c).SendMessage("RCPT TO: cuandotepasa@si.xD");
    status = (*c).SendMessage("DATA\nubject: Buenas tardes\nTo: jesuspa98@gmail.com\nHola, qué tal\n.");
    if(status == -1) {
        std::cout << "HA ocurrido un error enviando el mensaje..." << std::endl;
    }
    return 0;
}