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

