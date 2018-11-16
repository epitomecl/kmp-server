# kmp

# 도움말 찾기

~~~
.
├── README.md
├── KMP_Structure_diagram.pdf
├── KMPServer_Develop.md
├── KMPServer_Document.md
└── KMPWallet_Develop.md, HDWallet.md
~~~

문서 | 설명
---|---
[README.md](README.md) |  최상위 도움말
[KMP_Structure_diagram.pdf](KMP_Structure_diagram.pdf) |  KMP 구조 다이어그램
[KMPServer_Develop.md](KMPServer_Develop.md) | kmp-server 빌드, 개발 관련 도움말
[KMPServer_Document.md](KMPServer_Document.md) | kmp-server 설치 관련 도움말
[KMPWallet_Develop.md](KMPWallet_Develop.md) | kmp-wallet 빌드, 개발 관련 도움말
[HDWallet.md](https://github.com/epitomecl/kmp-wallet/blob/master/README.md) | HD Wallet과 관련된 BIP들의 내용 소개

## 모듈설명

### kmp-01-core
- kmp-wallet, kmp-server 등에서 공통으로 사용하는 클래스 관리 모듈
- 대표적으로 HDWalletData 클래스 등...

### kmp-31-api
- kmp-server 의 핵심 로직 관리 모듈
- 대부분의 코드가 여기에 있음

### kmp-32-blockstore
- PostgresFullPrunedBlockStore 구현모듈

### kmp-40-multictx
- 최종 서비스 모듈

### kmp-41-jh
- kmp-server 테스트를 위한 angular ui 모듈
- 특히, JHipster 를 사용하여, 대부분의 house keeping 코드들을 관리

### kmp-91-devtool-test
- 단위테스트를 위한 모듈

### kmp-92-devtool-release
- 패키징을 위한 모듈

### kmp-93-devtool-itest
- 통합테스트를 위한 모듈
