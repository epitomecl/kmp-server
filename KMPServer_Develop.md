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
| <center>**OS**</center> | <center>win10</center> |
| <center>**JAVA**</center> | <center>openJDK11</center> |
| <center>**IDE**</center> | <center>IntelliJ IDEA 2018.2.2</center> |
|**<center>DB</center>** | <center>postgresql</center> |

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
~~~

#### kmp-server 빌드하기
~~~
1. intellij IDEA에서 다운받은 kmp-server소스 디렉토리를 연다
2. kmp-41-jh/src/main/resources/application.yml 파일을 찾아 datasource의 url,username,password를 자신의 환경에 맞게 수정
3. kmp-41-jh 모듈을 선택하여 빌드
~~~
