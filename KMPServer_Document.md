# KMP-Server setting

## 준비하기
---
### Server  info
|  <center>구분</center> |  <center>설명</center> |
|:--------------------:|:-------------------:|
| <center>**운영체제**</center> | <center>Ubuntu 16.04.5 LTS</center> |
|**<center>기술스택</center>** | <center>Java(openJDK11), kotlin, springframework5, jHipster</center> |
|**<center>DB</center>** | <center>postgresql</center> |

---
### 설치방법
#### openJDK11 설치
~~~
$ sudo apt-get update
$ sudo apt install openjdk-11-jdk
$ java -version
$ javac -version
~~~

#### postgresql 설치
~~~
$ sudo apt-get update
$ sudo apt-get install postgresql
$ dpkg -l | grep postgres
~~~

---
### DB info
|  <center>DB이름</center> |  <center>관련정보</center> |
|:--------------------:|:-------------------:|
| <center>bitcoin_core_testnet</center> | <center>bitcoin-core 블록&트랜젝션 정보 </center> |
|<center>kmp</center> | <center>kmp  서비스를 위한  account 정보</center> |
|<center>secret_share_first</center> | <center>암호화된 wallet seed를 복호화 하기위한 key를 비밀 분산 방식으로 저장(1저장소)</center> |
|<center>secret_share_second</center> | <center>암호화된 wallet seed를 복호화 하기위한 key를 비밀 분산 방식으로 저장(2저장소)</center> |

---
#### bitcoin_core_testnet db스키마
<table>
  <thead>
    <tr>
      <th>table name</th><th>column name</th><th>data type</th><th>설명</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td rowspan=6><center>headers</center></td>
    </tr>
    <tr>
      <td>hash</td><td>bytea</td><td>block hash</td>
    </tr>
    <tr>
      <td>chainwork</td><td>bytea</td><td>난이도 계산에 사용</td>
    </tr>
    <tr>
      <td>height</td><td>integer</td><td>block height</td>
    </tr>
    <tr>
      <td>header</td><td>bytea</td><td>block header data</td>
    </tr>
    <tr>
      <td>wasundoable</td><td>boolean</td><td>고아블록이 되지않은 확정된 블록여부</td>
    </tr>
  </tbody>
</table>

<table>
  <thead>
    <tr>
      <th>table name</th><th>column name</th><th>data type</th><th>설명</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td rowspan=9><center>openoutputs</center></td>
    </tr>
    <tr>
      <td>hash</td><td>bytea</td><td>tx hash</td>
    </tr>
    <tr>
      <td>index</td><td>integer</td><td>트랜젝션 안에서의 인덱스 번호(0,1,2...)</td>
    </tr>
    <tr>
      <td>height</td><td>integer</td><td>트랜젝선이 실린 블록 height</td>
    </tr>
    <tr>
      <td>value</td><td>bigint</td><td>트랜젝션 전송 값</td>
    </tr>
    <tr>
      <td>scriptbytes</td><td>bytea</td><td>값을 전송하기 위한 스크립트</td>
    </tr>
    <tr>
      <td>toaddress</td><td>character varying(35)</td><td>값이 전송된 주소</td>
    </tr>
    <tr>
      <td>addresstargetable</td><td>smallint</td><td>주소 종류 0(비정상주소),1(공개키 해쉬에서 얻은 주소),2(deprecated),3(pay to script 해쉬에서 얻은 p2sh주소)</td>
    </tr>
    <tr>
      <td>coinbase</td><td>boolean</td><td>true(마이닝 보상값 전송 tx), false(마이닝 보상값 외 전송 tx)</td>
    </tr>
  </tbody>
</table>

<table>
  <thead>
    <tr>
      <th>table name</th><th>column name</th><th>data type</th><th>설명</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td rowspan=9><center>openoutputs_used</center></td>
    </tr>
    <tr>
      <td>hash</td><td>bytea</td><td>tx hash</td>
    </tr>
    <tr>
      <td>index</td><td>integer</td><td>트랜젝션 안에서의 인덱스 번호(0,1,2...)</td>
    </tr>
    <tr>
      <td>height</td><td>integer</td><td>트랜젝선이 실린 블록 height</td>
    </tr>
    <tr>
      <td>value</td><td>bigint</td><td>트랜젝션 전송 값</td>
    </tr>
    <tr>
      <td>scriptbytes</td><td>bytea</td><td>값을 전송하기 위한 스크립트</td>
    </tr>
    <tr>
      <td>toaddress</td><td>character varying(35)</td><td>값이 전송된 주소</td>
    </tr>
    <tr>
      <td>addresstargetable</td><td>smallint</td><td>주소 종류 0(비정상주소),1(공개키 해쉬에서 얻은 주소),2(deprecated),3(pay to script 해쉬에서 얻은 p2sh주소)</td>
    </tr>
    <tr>
      <td>coinbase</td><td>boolean</td><td>true(마이닝 보상값 전송 tx), false(마이닝 보상값 외 전송 tx)</td>
    </tr>
  </tbody>
</table>

<table>
  <thead>
    <tr>
      <th>table name</th><th>column name</th><th>data type</th><th>설명</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td rowspan=4><center>settings</center></td>
    </tr>
    <tr>
      <td>name</td><td>character varying(32)</td><td>설정값 이름(chainhead, verifiedchainhead, version)</td>
    </tr>
    <tr>
      <td>value</td><td>bytea</td><td>설정값(블록해시, 확정된 블록해시, 버전)</td>
    </tr>
  </tbody>
</table>

<table>
  <thead>
    <tr>
      <th>table name</th><th>column name</th><th>data type</th><th>설명</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td rowspan=5><center>undoableblocks</center></td>
    </tr>
    <tr>
      <td>hash</td><td>bytea</td><td>block hash</td>
    </tr>
    <tr>
      <td>height</td><td>integer</td><td>block height</td>
    </tr>
    <tr>
      <td>txoutchanges</td><td>bytea</td><td>txouts</td>
    </tr>
    <tr>
      <td>transactions</td><td>bytea</td><td>transactions(deprecated? Every row has null)</td>
    </tr>
  </tbody>
</table>

---
#### kmp db스키마
<table>
  <thead>
    <tr>
      <th>table name</th><th>column name</th><th>data type</th><th>설명</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td rowspan=4><center>user_account</center></td>
    </tr>
    <tr>
      <td>index</td><td>integer</td><td>pk</td>
    </tr>
    <tr>
      <td>id</td><td>character(32)</td><td>user id</td>
    </tr>
    <tr>
      <td>pw</td><td>character(32)</td><td>user pw</td>
    </tr>
  </tbody>
</table>

<table>
  <thead>
    <tr>
      <th>table name</th><th>column name</th><th>data type</th><th>설명</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td rowspan=4><center>encrypted_data</center></td>
    </tr>
    <tr>
      <td>index</td><td>integer</td><td>user_account pk</td>
    </tr>
    <tr>
      <td>label</td><td>text</td><td>wallet label</td>
    </tr>
    <tr>
      <td>encrypted</td><td>text</td><td>encrypted wallet-seed hex</td>
    </tr>
  </tbody>
</table>

---
#### secret_share_first & secret_share_second db스키마
<table>
  <thead>
    <tr>
      <th>table name</th><th>column name</th><th>data type</th><th>설명</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td rowspan=5><center>sharing_data</center></td>
    </tr>
    <tr>
      <td>index</td><td>integer</td><td>user_account pk</td>
    </tr>
    <tr>
      <td>userindex</td><td>integer</td><td>kmp db user_account pk</td>
    </tr>
    <tr>
      <td>label</td><td>text</td><td>wallet label</td>
    </tr>
    <tr>
      <td>shareddata</td><td>text</td><td>secret sharing data part(wallet-seed encrypt key)</td>
    </tr>
  </tbody>
</table>
