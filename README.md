# 잉게임 - 앙앙앙 서버 

## 프로젝트 라이프 사이클...

> 1. **아이디어가 떠오름**  
> 2. 코드 작성  
> 3. 최종 테스트

* **CI/CD 에서 자동으로 빌드, 릴리즈 후 시스템이 자동 업데이트 진행...**

> 1. Commit & Push 
> 2. Auto Build && Release
> 3. execute minecraft in-game command `/mg udpate`
>    * Auto download latest release 
>    * Auto plugin self-reload by PlugManX

## 적용한 기술 
* 단일 리포지토리로 모든 프로젝트의 코드를 한 버전 관리 시스템으로 함
* 디렉토리 구조가 곧 모듈 구성을 나타내므로 모듈을 만들기 매우 간단함. 반면 단점으로는 약 1분 정도의 빌드 시간으로 개발자 경험에 좋지 않음. 디렉토리 구조로써의 하위 모듈은 상위 모듈에 포함됨  
* 소포트웨어 버저닝은 빌드 넘버를 사용함
* 컨벤셔널 커밋의 규칙을 적용하려고 노력함
* GitFlow 는 브랜치를 분리해야할 경우가 없다면, 메인 브랜치나 develop 브랜치에 주로 푸시함.
* GitHub Issue 로 아이디어나 버그를 기록함