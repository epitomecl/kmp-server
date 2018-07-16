# kmp

## 모듈설명

### kmp-00-install
- 소스코드가 없는 라이브러리 설치 모듈
- bitcoinj-core-0.15-SNAPSHOT.jar

### kmp-00-libs
- 소스코드가 있는 라이브러리 설치 모듈
- api-client
- MyWalletHD

### kmp-01-core
- kmp-wallet, kmp-server 등에서 공통으로 사용하는 클래스 관리 모듈
- 대표적으로 HDWalletData 클래스 등...

### kmp-31-resource-server
- kmp-server 의 핵심 로직 관리 모듈
- 대부분의 코드가 여기에 있음

### kmp-35-ui-swing
- kmp-server 테스트를 위한 swing ui 모듈

### kmp-41-jh
- kmp-server 테스트를 위한 angular ui 모듈
- 특히, JHipster 를 사용하여, 대부분의 house keeping 코드들을 관리

### kmp-41-multictx
- 최종 모듈

### kmp-91-devtool-test
- 단위테스트를 위한 모듈

### kmp-92-devtool-release
- 패키징을 위한 모듈

### kmp-93-devtool-itest
- 통합테스트를 위한 모듈
