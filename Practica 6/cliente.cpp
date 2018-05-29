#include "cliente.h"


#define ENDLINE "\r\n"

Cliente::Cliente(const char* inet, const char* p) {
    // works
    ipAddress = inet;
    Cliente::port = atoi(p);
    std::cout << "Creado el objeto cliente..." << std::endl;
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
            // struct hostent *a = gethostbyaddr((const void*) &server_address.sin_addr, sizeof(server_address.sin_addr),
            //  server_address.sin_family);
            // hostName = a->h_name;

            std::cout << "Servidor:\t";
            std::cout << inet << ":" << ntohs(server_address.sin_port) << "\n";
            std::cout << "Hostname: \t" << hostName << std::endl;
        }
    } 
}

void Cliente::SendEmail() {
    std::string from;
    std::string to;
    std::string subject; 
    std::string body;
    std::cout << "¿Quién va a mandar el correo?\t";
    std::cin >> from;
    std::cout << "¿Quién va a recibir el correo?\t";
    std::cin >> to;
    std::cout << from << " " << to << std::endl;
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
    (*c).SendEmail();
    return 0;
}