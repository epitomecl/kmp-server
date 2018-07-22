# kmp

## 모듈설명

### kmp-00-install
- 소스코드가 없는 라이브러리 설치 모듈
- 부득이한 경우만 사용하고, 가능한 최소화 해야함
- bitcoinj-core-custom-0.15-SNAPSHOT.jar

### kmp-00-libs
- https://github.com/blockchain/api-v1-client-java
- https://github.com/blockchain/My-Wallet-V3-Android
- 소스코드가 있는 라이브러리 설치 모듈
- api-client
- MyWalletHD (AGPLv3 라이선스, kmp-00-install로 옮겨야 함)

### kmp-01-core
- kmp-wallet, kmp-server 등에서 공통으로 사용하는 클래스 관리 모듈
- 대표적으로 HDWalletData 클래스 등...

### kmp-31-blockexplorer
- kmp-server 의 핵심 로직 관리 모듈
- 대부분의 코드가 여기에 있음

### kmp-35-ui-swing
- kmp-server 테스트를 위한 swing ui 모듈

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
