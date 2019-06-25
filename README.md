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

### Database

The `bank application` uses the database [H2](https://www.h2database.com/html/main.html) with the simple purpose of test.
By default, the database username is `bankapplication` and the password is `password` (don't do this at home)

To access the database when the application is running, you can access `http://localhost:8080/h2-console`

