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
            std::string received = this->ReceiveMessage();
            std::vector<std::string> gotThem = this->ParseReceivedMessage(received);
            //hostName = gotThem[1];
            std::cout << "Servidor:\t";
            std::cout << inet << ":" << ntohs(server_address.sin_port) << "\n";
            std::cout << "Hostname: \t" << hostName << std::endl;
            std::cout << received << std::endl;
        }
    } 
}

void Cliente::GetInfoToSend() {
    //works
    std::string from;
    std::string to;
    std::string subject; 
    std::stringstream body;
    std::cout << "¿Quién va a mandar el correo? ";
    std::cin >> from;
    std::cout << "¿Quién va a recibir el correo? ";
    std::cin >> to;
    std::cout << from << " " << to << std::endl;
    std::cout << "Cual va a ser el asunto del correo? ";
    std::cin >> subject;
    std::cout << "Escribe el cuerpo del correo a enviar, para terminar, ponga una línea que solo contenga FIN.\n";
    std::string currentLine;
    do {
        getline(std::cin, currentLine);
        if(currentLine != "FIN") {
            body << currentLine << ' ' << std::endl;;
        }
    } while(currentLine != "FIN");
    std::cout << body.str() << std::endl;
    this->SendEmail(from, to, subject, body.str());
}

void Cliente::SendEmail(std::string from, std::string to, std::string subject, std::string body) {
    std::stringstream buildUp;
    buildUp << "EHLO " << hostName;
    this->SendMessage(buildUp.str());
    /* 
        Esto no está bien, hay que parsear el mensaje recibido!!!
        */
    buildUp.str("");
    buildUp << "MAIL FROM: " << from;
    this->SendMessage(buildUp.str());
    buildUp.str("");
    buildUp << "RCPT TO: " << to;
    this->SendMessage(buildUp.str());
    buildUp.str("");
    buildUp << "DATA";
    this->SendMessage(buildUp.str());
}

ssize_t Cliente::SendMessage(std::string message) {
    //works
    message = message.append(ENDLINE);
    ssize_t sent = write(clientSocket, message.c_str(), message.length());
    if(sent == -1) {
        std::cout << "No se ha enviado bien el mensaje" << std::endl;
        std::cout << std::strerror(errno) << std::endl;
    }
    return sent;
}

std::string Cliente::ReceiveMessage() {
    //Recive el mensaje
    std::stringstream message;
    ssize_t currentStatus;
    char currentChar, lastRead;
    while(currentStatus != -1 && currentStatus != 0 && lastRead != '\r' && currentChar != '\n') {
        currentStatus = read(clientSocket, &currentChar, sizeof(currentChar));
        if(lastRead != '\r' && currentChar != '\n') {
            if(currentChar != '\r') {
                message << currentChar;
            }
        }
        lastRead = currentChar;
    }
    return message.str();
}

std::vector<std::string> Cliente::ParseReceivedMessage(std::string input) {
    std::vector<std::string> stringsParsed;
    std::istringstream f(input);
    std::string eachString;
    while(getline(f, eachString, ' ')) {
        stringsParsed.push_back(eachString);
    }
    return stringsParsed;
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
    (*c).GetInfoToSend();
    return 0;
}