# Kingbbode Chat Bot Framework

*작성일 : 2017-02-07*

## 왜 개발하는가?

- 만들고 있는 Chat Bot을 알아가고 있는 지식을 적용한 리펙토링 및 라이브러리화 하고 싶어서
- 챗봇 개발의 진입 장벽을 낮춰주기 위해서
- 메신저 상관없는 통합 Chat Bot은 반드시 필요할 것 같아서

## 기반 기술은?

- Spring Framework 위에서 만드는 프레임워크?(프레임워크 in 프레임워크?);는 아니고 그냥 Spring Framework 쓴 개발임.
- Java Reflection과 Spring AOP를 활발히 사용
- Generic과 Interface를 통한 다형성
- Java8 적극 활용

## 컨셉

- 봇의 뇌와 뇌세포라는 의미로, 구현체 객체를 `Brain` , 구현체 객체 내부에 하나의 명령어와 Mapping되는 기능을 `BrainCell` 이라고 컨셉을 잡음.
- Annotaion 기반의 구현체 개발(Spring의 Controller를 모방, @Conroller - @Brain, @RequestMapping - @BrainCell) 
- 모든 요청과 반환을 담당하는 `DispatcherBrain`
- 모든 Brain의 생성 및 반환 역할을 하는 `BrainFactory`

## 현재 상태는?

- 작년 3월 경부터 개발을 배워가면서 내가 해보고 싶은 모든 것을 해보았던 장난감 프로젝트(1년 동안 꾸준히 추가만 되었던..)의 리펙토링 필요성을 강하게 느껴 깨부수기 시작
- 기존 장난감 프로젝트에서 기능 로직을 제외한 Base 로직만 분리하여 이 프로젝트를 생성
- 최초 커밋시에 이미 1차 리펙토링 및 모듈화가 완료
- 구조를 잡기만 하고 리펙토링을 못한 상태

## 앞으로 계획

- 테스트 케이스 작성
- 리펙토링
- 라이브러리화 위한 META-INF 정보 작성
- 기존 팀업 메신저 구현부를 주요 메신저를 통합하는 라이브러리 적용(스터디 모임에서 개발 중)하여 재작성
- 메이븐 레파지토리 등록(?)