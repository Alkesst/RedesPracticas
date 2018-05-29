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
        if(connect(clientSocket, (struct sockaddr*) &server_address, sizeof(struct sockaddr_in)) == -1) {
            close(clientSocket);
            clientSocket = -1;
            std::cout << "No se ha podido conectar con el servidor..." << std::endl;
            exit(-1);
        } else {
            // struct hostent *a = gethostbyaddr((const void*) &server_address.sin_addr, sizeof(server_address.sin_addr),
            //  server_address.sin_family);
            // hostName = a->h_name;
            std::string received = this->ReceiveMessage();
            std::vector<std::string> gotThem = this->ParseReceivedMessage(received);
            if(gotThem.size() > 0) {
                hostName = gotThem[1];
            }
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
    this->SendEmail(from, to, subject, body.str());
}

void Cliente::SendEmail(const std::string &from, const std::string &to, const std::string &subject, const std::string &body) {
    std::stringstream buildUp;
    std::string received;
    std::string error;
    buildUp << "EHLO " << hostName;
    this->SendMessage(buildUp.str());
    received = this->ReceiveMessage();
    std::cout << received << std::endl;
    if(!ShoulIContinue(this->ParseReceivedMessage(received)[0], error)) {
        exit(to_int(error.c_str()));
    }
    received = this->ReceiveMessage();
    std::cout << received << std::endl;
    if(!ShoulIContinue(this->ParseReceivedMessage(received)[0], error)) {
        exit(to_int(error.c_str()));
    }
    received = this->ReceiveMessage();
    std::cout << received << std::endl;
    if(!ShoulIContinue(this->ParseReceivedMessage(received)[0], error)) {
        exit(to_int(error.c_str()));
    }
    received = this->ReceiveMessage();
    std::cout << received << std::endl;
    if(!ShoulIContinue(this->ParseReceivedMessage(received)[0], error)) {
        exit(to_int(error.c_str()));
    }
    buildUp.str("");
    buildUp << "MAIL FROM: " << from;
    this->SendMessage(buildUp.str());
    received = this->ReceiveMessage();
    std::cout << received << std::endl;
    if(!ShoulIContinue(this->ParseReceivedMessage(received)[0], error)) {
        exit(to_int(error.c_str()));
    }
    buildUp.str("");
    buildUp << "RCPT TO: " << to;
    this->SendMessage(buildUp.str());
    received = this->ReceiveMessage();
    std::cout << received << std::endl;
    if(!ShoulIContinue(this->ParseReceivedMessage(received)[0], error)) {
        exit(to_int(error.c_str()));
    }
    buildUp.str("");
    buildUp << "DATA";
    this->SendMessage(buildUp.str());
    buildUp.str("");
    buildUp << "Subject: " << subject << "\nFrom: " << from << "\nTo: " << to << "\nContent-Type: text/plain;"
    << "\nContent-Transfer-Encoding: 8bit\n" << body << ENDLINE << ".";
    this->SendMessage(buildUp.str());
    received = this->ReceiveMessage();
    if(!ShoulIContinue(this->ParseReceivedMessage(received)[0], error)) {
        exit(to_int(error.c_str()));
    }
    std::cout << "Message sent successfuly!!" << std::endl;
}

ssize_t Cliente::SendMessage(const std::string &message) {
    //works
    std::string formattedMessage = message;
    formattedMessage.append(ENDLINE);
    ssize_t sent = write(clientSocket, formattedMessage.c_str(), formattedMessage.length());
    if(sent == -1) {
        std::cout << "No se ha enviado bien el mensaje" << std::endl;
        std::cout << std::strerror(errno) << std::endl;
        exit(-1);
    }
    return sent;
}

std::string Cliente::ReceiveMessage() {
    //Recive el mensaje
    std::stringstream message;
    bool finishedMessage = false;
    ssize_t currentStatus = 10;
    char currentChar, lastRead;
    while((currentStatus != -1 && currentStatus != 0) && !finishedMessage) {
        currentStatus = read(clientSocket, &currentChar, sizeof(currentChar));
        if(lastRead != '\r' || currentChar != '\n') {
            if(currentChar != '\r') {
                message << currentChar;
            }
        } else {
            finishedMessage = true;
        }
        lastRead = currentChar;
    }
    std::string copia = message.str();
    return copia;
}

std::vector<std::string> Cliente::ParseReceivedMessage(const std::string &input) {
    std::vector<std::string> stringsParsed;
    std::istringstream f(input);
    std::string eachString;
    while(getline(f, eachString, ' ')) {
        stringsParsed.push_back(eachString);
    }
    return stringsParsed;
}

bool Cliente::ShoulIContinue(const std::string &input, std::string &causeError) {
    // works
    char firstLiteral = input[0];
    if(firstLiteral == '1' || firstLiteral == '2' || firstLiteral == '3') {
        return true;
    } else if(firstLiteral == '4') {
        causeError = '4';
    } else if(firstLiteral == '5') {
        causeError = '5';
    }
    return false;
}

int Cliente::to_int(char const *s) {
    if ( s == NULL || *s == '\0' ) {
        throw std::invalid_argument("null or empty string argument");
    }

    bool negate = (s[0] == '-');
    if ( *s == '+' || *s == '-' ) {
        ++s;
    }

    if ( *s == '\0') {
        throw std::invalid_argument("sign character only.");
    }

    int result = 0;
    while(*s) {
        if ( *s >= '0' && *s <= '9' ) {
            result = result * 10  - (*s - '0');  //assume negative number
        } else {
            throw std::invalid_argument("invalid input string");
        }
        ++s;
    }
    return negate ? result : -result; //-result is positive!
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