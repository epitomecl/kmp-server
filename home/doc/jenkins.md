## 접속 정보
```
$ ssh ubuntu@ec2-13-125-3-157.ap-northeast-2.compute.amazonaws.com
```

## Oracle JDK 1.8 설치
```
$ sudo apt-add-repository ppa:webupd8team/java
$ sudo apt-get update
$ sudo apt-get install oracle-java8-installer
$ javac -version
```

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
