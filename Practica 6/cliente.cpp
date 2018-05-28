#include "cliente.h"


Cliente::Cliente(const char* inet) {
    std::cout << "Creado el objeto cliente..." << std::endl;
}


void Cliente::ConnectToServer(const char* inet) {
    infoCliente.socket = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
    if(infoCliente.socket != -1) {
        struct sockaddr_in server_address;
        server_address.sin_family = AF_INET;
        inet_aton(inet, &(server_address.sin_addr));
        server_address.sin_port = htons(25);
        server_address.sin_addr.s_addr = INADDR_ANY;
        if(connect(infoCliente.socket, (struct sockaddr*) &server_address, sizeof(struct sockaddr_in)) == -1) {
            close(infoCliente.socket);
            infoCliente.socket = -1;
            std::cout << "No se ha podido conectar con el servidor..." << std::endl;
        } else {
            std::cout << "Conectado al servidor..." << std::endl;
            std::cout << inet << " " << server_address.sin_port << std::endl;
        }
    } 
}

int main() {
    Cliente *c = new Cliente("192.168.164.28");
    (*c).ConnectToServer("192.168.164.28");
}