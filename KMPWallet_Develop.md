# KMP-Wallet  develop

---
## 소스 다운로드
~~~
git: https://github.com/epitomecl/kmp-wallet.git
~~~
---
## 빌드하기
### 준비도구
|  <center>구분</center> |  <center>설명</center> |
|:--------------------:|:-------------------:|
| <center>**OS**</center> | <center>win10(dev), android(device)</center> |
| <center>**JAVA**</center> | <center>openJDK11</center> |
| <center>**IDE**</center> | <center>Android Studio 3.2.1</center> |

#### openJDK11 설치
~~~
1. http://jdk.java.net/11/ 로 이동
2. 다음 파일을 찾아 다운로드 openjdk-11.0.1_windows-x64_bin.zip
3. 원하는 위치에 받은 파일의 압축을 풀고 JAVA_HOME 시스템 변수와 %JAVA_HOME%\bin 환경 변수 및 jdk폴더 경로를 Path에 추가
~~~

#### Android Studio 설치
~~~
1. https://developer.android.com/studio/?hl=ko 로 이동
2. 'DOWNLOAD ANDROID STUDIO' 클릭
3. https://developer.android.com/studio/install?hl=ko 페이지를 참고하여 설치
~~~

---
#### kmp-wallet 빌드하기
~~~
1. Android Studio [File]=>[Open]메뉴를통해 kmp-wallet 소스를 열고 팝업으로 올라오는 업데이트들을 모두 설치한다
2. app/secrets.properties 파일을 찾아 SEKRIT(app정보 저장소 암호), SERVER_URI(접속 서버 uri) 정보를 자신의 개발환경에 맞게 수정
3. project build
4. run & debug
~~~

#### kmp-wallet 사용하기
##### 1. 실행
~~~
1. kmp-wallet이 접속할 kmp-server를 실행(https://github.com/epitomecl/kmp-server)
2. Android Studio AVD를 통해 앱을 실행하여 kmp-server에 접속
~~~

##### 2. 계정생성및 로그인
~~~
1. 앱의 첫 화면에서  id와 password를 입력하고 Regist버튼을 눌러 계정 생성
2. 이미 id가 있다면 'LOGIN>>' 버튼을 클릭하여 로그인 화면으로 이동하여 id와 password를 입력하여 로그인.
~~~
![kmp-wallet_01](https://user-images.githubusercontent.com/36718155/48330395-cf740880-e68f-11e8-83db-3537f8e26b1e.png) ![kmp-wallet_02](https://user-images.githubusercontent.com/36718155/48330397-d00c9f00-e68f-11e8-9d7a-c2343b2f1fe2.png)

##### 3. 지갑 생성 및 복구
~~~
1. 로그인후 지갑 생성 화면에서 'Select wallet type' 을 BITCOIN_TESTNET을 선택하고 'Input wallet label'에 지갑 라벨이 될 이름을 입력한다.
2. 'CREATE WALLET' 버튼을 클릭하여 지갑을 생성한다.
3. 'WALLET FROM SEED' 버튼을 누르면 지갑의 시드값으로부터 지갑을 만들수 있게된다.
4. 서버에 백업해 놓은 지갑이 있는 경우 지갑 생성 화면에서 'RESTORE WALLET' 버튼이 보여지는데 이를 클릭하면 서버에 백업된 지갑 목록이 보여지고 이를 선택하면 해당 지갑이 복구되며 만들어진다.
~~~
![kmp-wallet_03](https://user-images.githubusercontent.com/36718155/48330398-d00c9f00-e68f-11e8-90d8-34db3544acbb.png) ![kmp-wallet_03_02](https://user-images.githubusercontent.com/36718155/48330400-d0a53580-e68f-11e8-8acc-78a748e6f1e4.png) ![kmp-wallet_03_01](https://user-images.githubusercontent.com/36718155/48330399-d0a53580-e68f-11e8-893e-fbd9b48e6cc0.png)

##### 4. 지갑 정보 보기
~~~
1. 지갑 정보 화면에서 지갑의 라벨(이름)과 지갑의 seed값을 확인.(seed값이 직접 보여지는것은 개발 버전이기 때문이며 보안상 실제로는 보여지면 안됨)
2. 1번에서 확인한 seed값은 '3. 지갑생성' 3번을 참고하여 시드값으로부터 지갑을 복구할경우 사용할수 있다.(이는 개발자를 위한 기능. 일반적으로는 BACKUP & RESTORE WALLET으로 지갑을 복구한다)
3. 이 지갑을 서버에 백업하기 위해서는 아래의 'BACKUP WALLET'버튼을 클릭한다.(이미 서버에 백업된 지갑이라면 버튼이 보이지 않는다)
4. 지갑의 라벨(이름)부분을 클릭하면 지갑의 주소들과 보유량을 볼수있는 화면으로 넘어간다.
~~~
![kmp-wallet_04](https://user-images.githubusercontent.com/36718155/48330401-d0a53580-e68f-11e8-8027-6d7f3f89803d.png)


##### 5. 지갑 주소과 보유량 보기
~~~
1. 최초 지갑이 생성되면 기본적으로 1개의 주소가 보인다. 이 주소를 상대방에게 전달하고 상대방의  주소로부터 코인을 받을수 있다.
2. 지갑에서 1개의 주소 외에 추가적인 주소를 얻으려 한다면 화면 아래 'NEW ADDR'버튼을 클릭하면 주소가 하나 더 추가된다.
3. 지갑 안의 주소에 보유한 코인이 있고 다른 주소로 송금하기 위해서는 해당 주소를 클릭하면 송금화면으로 넘어간다.
~~~
![kmp-wallet_05](https://user-images.githubusercontent.com/36718155/48330402-d0a53580-e68f-11e8-987f-a7741d508da9.png)


##### 6. 송금
~~~
1. 송금 화면에서 'input send satoshi' 입력란에 보내고자 하는 수량을 입력한다.(BTC단위가 아닌 satoshi단위이다. 1 BTC = 100,000,000 satoshi)
2. 아래의 'to address'에 받을 주소를 입력하고 'SEND'버튼을 클릭하여 보낸다.
3. 송금 수수료는 100000 satoshi(0.001 BTC)고정이며 따라서 송금시 잔고가 (보내는 금액+수수료)만큼 있어야 송금이 된다.
4. 보낸 수량은 송금화면 아래에 1보여지게 되며 최초 0 confirm에서 6 confirm까지 보여진다.
5. 6 confirm이 넘어가면 더이상 보이지 않는다.
~~~
![kmp-wallet_06](https://user-images.githubusercontent.com/36718155/48330393-cf740880-e68f-11e8-8044-e392c8beebab.png) ![kmp-wallet_07](https://user-images.githubusercontent.com/36718155/48330394-cf740880-e68f-11e8-8cca-f836a24efcf4.png)
