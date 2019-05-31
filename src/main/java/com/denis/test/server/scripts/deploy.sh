#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i  C:\Dev\ssh\FrankfurtServerKey.pem \
    target/com.denis.test.server-1.0-SNAPSHOT.war \
    ubuntu/35.157.234.188:/home/ubuntu/

echo 'Restart server...'