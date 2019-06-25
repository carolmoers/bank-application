# Bank Application

> Simple bank API to make transactions between accounts

### Prerequisites

To have the `bank application` running locally you need to install some stuff first:

```
$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
$ brew install git
$ brew install gradle
$ brew tap caskroom/cask
$ brew cask install 'java'
```

### Installing and running

```
$ git clone https://github.com/carolmoers/bank-application.git
$ ./gradlew clean build
$ ./gradlew bootRun
```

#### Running unit tests

```
$ ./gradlew test
```

#### Running integration tests

```
$ ./gradlew integrationTest
```

### Database

The `bank application` uses the database [H2](https://www.h2database.com/html/main.html) with the simple purpose of test.
By default, the database username is `bankapplication` and the password is `password` (don't do this at home)

To access the database when the application is running, you can access

```
http://localhost:8080/h2-console
```

There is already some default data being inserted when the app starts running.
You can see it in `src/main/resources/data.sql`

### How to use the app

There is a post endpoint to transfer money from one account to another

```
http://localhost:8080/api/transfer
```

You can test with the data that is already inserted when the app starts,
just add it to the body request (don't forget to change the contentType to JSON):

```
{ "accountToDebitNumber":123, "accountToDepositNumber":321, "amount":10.0 }
```

### Built With

* [Spring-Boot](https://spring.io/projects/spring-boot)
* [Gradle](https://gradle.org/)
* [H2](https://www.h2database.com/html/main.html)

---------------------------------------------------------

## Como o código pode ser melhorado

* Analisar mais cenários onde o tratamento de erro é necessário
* Validação mais robusta dos dados recebidos na API
* Autenticação na API para evitar fraudes
* Utilizar HTTPS
* Utilizar lombok para diminuir verbosidade
* Refatorar testes de integração e unitários para que cenários que hoje estão no nível de integração passem para
unitário pois é menos custoso
* Adicionar task do sonar para auxiliar na qualidade do código 

* Endpoint para visualizar as movimentações de uma determinada conta
* Mais informações na conta para auxiliar na validação e autenticação dos dados 


