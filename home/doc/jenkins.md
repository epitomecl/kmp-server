## 접속 정보
```
$ ssh ubuntu@ec2-13-125-250-135.ap-northeast-2.compute.amazonaws.com
```

## Oracle JDK 1.8 설치
```
$ sudo apt-add-repository ppa:webupd8team/java
$ sudo apt-get update
$ sudo apt-get install oracle-java8-installer
$ javac -version
```

## maven 설치
```
$ sudo apt -qq install maven
```

## port open
 - ssh: 22
 - http: 80, 8080, 443, 8443
 - bitcoind: 18332, 18333
 - geth: 8545, 30305
 - jenkins: 9070
 - sonarqube: 9000 

## jenkins-ci 설치
```
$ mkdir -p work/local/jenkins
$ cd work/local/jenkins
$ wget http://mirrors.jenkins.io/war-stable/latest/jenkins.war
$ cat <<! > jenkins.sh
nohup /usr/lib/jvm/java-8-oracle/bin/java -DJENKINS_HOME=${HOME}/work/local/jenkins/.jenkins -jar jenkins.war --httpPort=9070 &
!
$ chmod +x jenkins.sh
$ ./jenkins.sh
$ jps -l
$ curl http://localhost:9070
```

## sonarqube 설치
- https://www.lesstif.com/pages/viewpage.action?pageId=39126262
- http://keichee.tistory.com/158
```
$ mkdir -p ~/work/local/sonarqube
$ cd ~/work/local/sonarqube
$ wget https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-7.1.zip
$ unzip sonarqube-7.1.zip
$ cd sonarqube-7.1/
$ ./bin/linux-x86-64/sonar.sh start
$ curl http://localhost:9000
```