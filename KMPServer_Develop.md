# KMP-Server  develop

---
## 소스 다운로드
~~~
git: https://github.com/epitomecl/kmp-server.git
~~~
---
## 빌드하기
### 준비도구
|  <center>구분</center> |  <center>설명</center> |
|:--------------------:|:-------------------:|
| <center>**OS**</center> | <center>win10(dev), linux(deploy)</center> |
| <center>**JAVA**</center> | <center>openJDK11</center> |
| <center>**IDE**</center> | <center>IntelliJ IDEA 2018.2.2</center> |
|**<center>DB</center>** | <center>postgresql</center> |
|**<center>storage</center>** | <center>최소 30GB 이상(testnet), 최소 200GB이상(main net)</center> |

#### openJDK11 설치
~~~
1. http://jdk.java.net/11/ 로 이동
2. 다음 파일을 찾아 다운로드 openjdk-11.0.1_windows-x64_bin.zip
3. 원하는 위치에 받은 파일의 압축을 풀고 JAVA_HOME 시스템 변수와 %JAVA_HOME%\bin 환경 변수 및 jdk폴더 경로를 Path에 추가
~~~

#### intelliJ IDEA 설치
~~~
1. https://www.jetbrains.com/idea/download 로 이동
2. IntelliJ IDEA windows Ultimate 다운로드
3. 다운받은 설치파일을 실행하여 설치
~~~

#### postgresql 설치
~~~
1. https://www.postgresql.org/download/windows/ 로 이동
2. 'Download the installer' 링크를 통해 연결된 페이지에서 최신 버전의 'Windows x86-64' 설치파일을 받는다
3. 받은 파일을 실행하여 db를 설치
4. db접속정보를 설정하고 접속을 확인한다.
~~~

---
#### kmp-32-blockstore 빌드하기
~~~
1. intelliJ IDEA 에서 다운받은 kmp-server소스 디렉토리를 연다
2. kmp-32-blockstore/src/main/resources/secrets.properties 파일을 찾아 postgresql 접속정보를 자신의 환경에 맞게 수정(URL,DB,USER,PASS)
3. kmp-32-blockstore 모듈을 선택하여 빌드
4. Run BlockStoreApp.main() , Debug BlockStoreApp.main()
5.실행파일은  메뉴에서 Build->Build Artifacts-> 목록에서 kmp-32-blockstore:jar를 빌드하여 얻음
6. /out/artifacts/kmp_32_blockstore_jar 폴더에 빌드된 jar를 사용
7. 커맨드창에서 java를 통해 실행할 경우 'java -jar kmp_32_blockstore_jar'명령어를 사용한다.
~~~

#### kmp-32-blockstore 배포및 실행
~~~
1. 빌드에서 얻어진 jar를 배포 linux서버로 복사.
2. linux에서 실행할 경우 접속을 끊어도 데몬처럼 계속 실행되도록 nohup 명렁어로 실행한다. 'nohup java -jar kmp-32-blockstore.jar'
3. nohup.out 표준 출력파일을 볼 필요가 없다면 다음과 같이 실행. 'nohup java -jar kmp-32-blockstore.jar 1> /dev/null 2>&1 &'
4. kmp-32-blockstore 를 실행하면 최초 실행시 bitcoin_core_testnet을 생성하고 블록체인을 받아 테이블을 구축한다.
5. 최초 실행시 모든 블럭들을 전부 다운로드 받아 db에 저장하기 때문에 최초 기동시 db 구축시간이 매우 오래걸린다. 거래량이 적은 테스트넷이라고 해도 회선 상태에 따라서 3~4일 혹은 그 이상이 소요된다.
6. 모든 블록데이터를 db에 저장하였다면 kmp-32-blockstore.jar를 개발 pc에 두지 말고 따로 서버에 띄워 놓는것이 좋다. db를 항상 최신 상태로 유지 해야하기 때문.
~~~

#### kmp-server 빌드하기
~~~
1. intellij IDEA에서 다운받은 kmp-server소스 디렉토리를 연다
2. kmp-41-jh/src/main/resources/application.yml 파일을 찾아 datasource의 url,username,password를 자신의 환경에 맞게 수정
3. kmp-41-jh 모듈을 선택하여 빌드(디버그시는 KmpjhApp)
~~~

#### kmp-server 배포및 실행하기
~~~
1. Jenkins설치및 실행. deploy 서버 os는 linux.
2. Jenkins설치후 접속하여 kmp-server-deploy 이름의 프로젝트 설정. ant build를 사용하여 빌드및 배포하는데  Invoke Ant 를 빌드에 추가하고 targets all 설정해준다.
3. kmp-server-deploy를 빌드하여 경로 /data/ubuntu/kmp-server 에 복사된 tar 파일의 압축을 풀고 start.sh 스크립트를 실행하여 서버를 동작시키는 것을 확인.
4. 브라우저를 통해 deploy 서버에 접속하여 'Welcome, KMP' 페이지를 확인.
~~~
* [jenkins 설치하기](home/doc/jenkins.md)
