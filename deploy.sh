#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i /c/dev/ssh/FrankfurtServerKey.pem \
    /c/dev/projects/test_server/target/com.denis.test.server-1.0-SNAPSHOT.war \
    ubuntu@35.157.234.188:~/server/

echo 'Restart server...'

ssh -i /c/dev/ssh/FrankfurtServerKey.pem ubuntu@35.157.234.188 << E0F

pgrep java | xargs kill -9
nohup java -jar ~/server/com.denis.test.server-1.0-SNAPSHOT.war > log.txt &

E0F

echo 'Done'