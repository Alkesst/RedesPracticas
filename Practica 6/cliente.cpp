#include "cliente.h"


#define ENDLINE "\r\n"

/*
 * Crea un objeto cliente asignándole una direccion ip
 * y un puerto p, el cual hay que pasar de char a int con la funcion atoi
 */
Cliente::Cliente(const char* inet, const char* p) {
    ipAddress = inet;
    Cliente::port = atoi(p);
    std::cout << "Creado el objeto cliente..." << std::endl;
}

/*
 * crea un socket para conectarse a la dirección inet.
 * si no es capaz de conectarse al socket, cierra el programa.
 * en otro caso, recive el mensaje de bienvenida del servidor,
 * lo parsea, lo guarda en una variable del cliente y muestra por
 * consola a qué hostname te has conectado y al puerto.
 */
void Cliente::ConnectToServer(const char* inet) {
    // works
    clientSocket = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP); // crea el socket
    if(clientSocket != -1) {
        struct sockaddr_in server_address;
        server_address.sin_family = AF_INET;
        inet_aton(inet, &(server_address.sin_addr));
        server_address.sin_port = htons(25);
        if(connect(clientSocket, (struct sockaddr*) &server_address, sizeof(struct sockaddr_in)) == -1) { // se conecta al servidor
            close(clientSocket);
            clientSocket = -1;
            std::cout << "No se ha podido conectar con el servidor..." << std::endl;
            exit(-1);
        } else {
            std::string received = this->ReceiveMessage(); // recibe el mensaje de bienvenida
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
    std::string currentLine; // obtiene toda la información del correo para enviarlo
    do {
        getline(std::cin, currentLine);
        if(currentLine != "FIN") {
            body << currentLine << ' ' << std::endl;;
        }
    } while(currentLine != "FIN");
    this->SendEmail(from, to, subject, body.str()); // envía el correo con toda la información
}

void Cliente::SendEmail(const std::string &from, const std::string &to, const std::string &subject, const std::string &body) {
    std::stringstream buildUp;
    std::string received;
    std::string error;
    buildUp << "EHLO " << hostName; // avisamos al servidor de que queremos mandar un correo
    this->SendMessage(buildUp.str()); // Send message hace un append de \r\n al final por tanto no lo añadimos nunca
    received = this->ReceiveMessage();  // recibe la respuesta del servidor
    std::cout << received << std::endl;
    if(!ShoulIContinue(this->ParseReceivedMessage(received)[0], error)) { // analiza la respuesta y muestra el error
        exit(to_int(error.c_str())); // si existe un error, sale de la aplicación.
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
    buildUp.str(""); // añade información al correo. La codificación, el tipo de contenido, etc...
    buildUp << "Subject: " << subject << "\nFrom: " << from << "\nTo: " << to << "\nContent-Type: text/plain;"
    << "\nContent-Transfer-Encoding: 8bit\n" << body << ENDLINE << ".";
    this->SendMessage(buildUp.str()); // envía el correo.
    received = this->ReceiveMessage();
    if(!ShoulIContinue(this->ParseReceivedMessage(received)[0], error)) {
        exit(to_int(error.c_str()));
    }
    this->SendMessage("QUIT"); // cerramos la conexión con el servidor
    received = this->ReceiveMessage();
    std::cout << "Message sent successfuly!!\nDisconnected from server... Status: " << received << std::endl;
    std::cout << "Exiting..." << std::endl;
}

ssize_t Cliente::SendMessage(const std::string &message) {
    //works
    std::string formattedMessage = message;
    formattedMessage.append(ENDLINE); // añadimos \r\n al final del string.
    ssize_t sent = write(clientSocket, formattedMessage.c_str(), formattedMessage.length()); // escribimos el contenido para mandarlo
    if(sent == -1) {
        std::cout << "No se ha enviado bien el mensaje" << std::endl; // si hay algún error, sale de la aplicación
        std::cout << std::strerror(errno) << std::endl;
        exit(-1);
    }
    return sent; // devuelve el número de bytes enviados
}

std::string Cliente::ReceiveMessage() {
    //Recive el mensaje
    std::stringstream message;
    bool finishedMessage = false;
    ssize_t currentStatus = 10;
    char currentChar, lastRead;
    while((currentStatus != -1 && currentStatus != 0) && !finishedMessage) {
        currentStatus = read(clientSocket, &currentChar, sizeof(currentChar)); // lee el mensaje que recibimos. 
        if(lastRead != '\r' || currentChar != '\n') { // parseamos la entrada. Mientras sea distinto de \r\n, añadimos caracteres
            if(currentChar != '\r') { // siempre que sea distinto de \r, sigue, en cualquier otro caso, hemos terminado
                message << currentChar;
            }
        } else {
            finishedMessage = true;
        }
        lastRead = currentChar;
    }
    std::string copia = message.str();
    return copia;  // devolvemos el mensaje recibido
}

std::vector<std::string> Cliente::ParseReceivedMessage(const std::string &input) {
    std::vector<std::string> stringsParsed;
    std::istringstream f(input);
    std::string eachString;
    while(getline(f, eachString, ' ')) { // parseamos la entrada. Para cada palabra lo añadimos a un vector de strings.
        stringsParsed.push_back(eachString);
    }
    return stringsParsed; // devolvemos dicho vector parseado
}

bool Cliente::ShoulIContinue(const std::string &input, std::string &causeError) {
    // works
    char firstLiteral = input[0];
    if(firstLiteral == '1' || firstLiteral == '2' || firstLiteral == '3') { // si el primer carácter es 1, 2, o 3. Continua
        return true;
    } else if(firstLiteral == '4') { // si es 4, error y la causa es 4
        causeError = '4';
    } else if(firstLiteral == '5') { // si es 5, error y  la causa es 5
        causeError = '5';
    }
    return false; // si llega aquí, significa que ha habido algún error.
}

int Cliente::to_int(char const *s) {
    /*
        * Adapted from https://stackoverflow.com/questions/4442658/c-parse-int-from-string.
        * c++98 does not support stoi function. Lab computers only supports c++98.
        * from char const * to integer
    */
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
    // std::shared_ptr<Cliente> c (new Cliente("192.168.164.28")); si pudiéramos usar c++11
    if (argc < 3) {
        std::cout << "Necesito el hostname para conectarme..." << std::endl;
        exit(-1);
    }
    Cliente *c = new Cliente(argv[1], argv[2]);
    (*c).ConnectToServer(argv[1]);
    (*c).GetInfoToSend();
    return 0;
}