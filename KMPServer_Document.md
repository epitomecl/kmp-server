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
#### DB 정보
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

##### Create query
~~~
CREATE TABLE public.headers
(
    hash bytea NOT NULL,
    chainwork bytea NOT NULL,
    height integer NOT NULL,
    header bytea NOT NULL,
    wasundoable boolean NOT NULL,
    CONSTRAINT headers_pk PRIMARY KEY (hash)
)
~~~
~~~
CREATE TABLE public.openoutputs
(
    hash bytea NOT NULL,
    index integer NOT NULL,
    height integer NOT NULL,
    value bigint NOT NULL,
    scriptbytes bytea NOT NULL,
    toaddress character varying(35) COLLATE pg_catalog."default",
    addresstargetable smallint,
    coinbase boolean,
    CONSTRAINT openoutputs_pk PRIMARY KEY (hash, index)
)

CREATE INDEX openoutputs_addresstargetable_idx
    ON public.openoutputs USING btree
    (addresstargetable)

CREATE INDEX openoutputs_hash_idx
    ON public.openoutputs USING btree
    (hash)

CREATE INDEX openoutputs_hash_index_num_height_toaddress_idx
    ON public.openoutputs USING btree
    (hash, index, height, toaddress COLLATE pg_catalog."default")

CREATE INDEX openoutputs_toaddress_idx
    ON public.openoutputs USING btree
    (toaddress COLLATE pg_catalog."default")

CREATE TRIGGER "REMOVE_UTXO"
    AFTER DELETE
    ON public.openoutputs
    FOR EACH ROW
    EXECUTE PROCEDURE public."REMOVE_UTXO"();
~~~

~~~
CREATE TABLE public.openoutputs_used
(
    hash bytea NOT NULL,
    index integer NOT NULL,
    height integer NOT NULL,
    value bigint NOT NULL,
    scriptbytes bytea NOT NULL,
    toaddress character varying(35) COLLATE pg_catalog."default",
    addresstargetable smallint,
    coinbase boolean,
    CONSTRAINT openoutputs_used_pk PRIMARY KEY (hash, index)
)

CREATE INDEX openoutputs_used_addresstargetable_idx
    ON public.openoutputs_used USING btree
    (addresstargetable)

CREATE INDEX openoutputs_used_hash_idx
    ON public.openoutputs_used USING btree
    (hash)

CREATE INDEX openoutputs_used_hash_index_num_height_toaddress_idx
    ON public.openoutputs_used USING btree
    (hash, index, height, toaddress COLLATE pg_catalog."default")

CREATE INDEX openoutputs_used_toaddress_idx
    ON public.openoutputs_used USING btree
    (toaddress COLLATE pg_catalog."default")
~~~

~~~
CREATE TABLE public.settings
(
    name character varying(32) COLLATE pg_catalog."default" NOT NULL,
    value bytea,
    CONSTRAINT setting_pk PRIMARY KEY (name)
)
~~~

~~~
CREATE TABLE public.undoableblocks
(
    hash bytea NOT NULL,
    height integer NOT NULL,
    txoutchanges bytea,
    transactions bytea,
    CONSTRAINT undoableblocks_pk PRIMARY KEY (hash)
)

CREATE INDEX undoableblocks_height_idx
    ON public.undoableblocks USING btree
    (height)
~~~
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

##### Create query
~~~
CREATE TABLE public.user_account
(
    index integer NOT NULL DEFAULT nextval('user_account_index_seq'::regclass),
    id character(32) COLLATE pg_catalog."default" NOT NULL,
    pw character(32) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_account_pkey PRIMARY KEY (index),
    CONSTRAINT user_account_id_key UNIQUE (id)
)

CREATE TABLE public.encrypted_data
(
    index integer NOT NULL,
    label text COLLATE pg_catalog."default" NOT NULL,
    encrypted text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT encrypted_data_index_label_key UNIQUE (index, label)
)
~~~
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
      <td>index</td><td>integer</td><td>pk</td>
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

##### Create query
~~~
CREATE TABLE public.sharing_data
(
    index integer NOT NULL DEFAULT nextval('sharing_data_index_seq'::regclass),
    userindex integer NOT NULL,
    label text COLLATE pg_catalog."default" NOT NULL,
    shareddata text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT sharing_data_pkey PRIMARY KEY (index),
    CONSTRAINT sharing_data_userindex_label_key UNIQUE (userindex, label)
)

CREATE INDEX userindex_idx
    ON public.sharing_data USING btree
    (userindex)
~~~
