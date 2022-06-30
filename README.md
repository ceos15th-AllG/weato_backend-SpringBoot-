# weato_backend   -SpringBoot-


### 1. 개발 계획
웹 기반의 아토피 관련 커뮤니티 + 뉴스레터, 뉴스레터 자동 발송 시스템

### 2. 사용할 framework 및 libraries
<ul>
<li>Spring boot(gradle)</li><br>
<li>Spring data JPA</li><br>
<li>Spring security</li><br>
<li>QueryDsl - 이거는 완전히 적용 안할지도..차후 리팩토링 예정</li><br>
<li>Spring-REST-Docs - api 문서 (적용해보고 좀 익숙해지지 않는다면 swagger 사용 예정)</li>
</ul>

### 3. 개발 계획
<ol>
<li>api 문서작업, ERD 수정해서 완성, 아키텍쳐 확정 및  ~ (7월 3일)</li>
<li>도메인(Entity) 작업 완성 및 repository 계층, Service 계층 작업. ~ (7월 8일)</li>
<li>Controller 계층에서의 API 작업 ~ (7월 13일)</li>
<li>프론트엔드 연동 및 배포준비 ~ (7월 20일)</li>
<li>마지막 서비스 테스트 및 리팩토링. 작업 완료  ~ (해커톤)</li>
</ol>


22.07.01
#### domain 계층 설계
Entity들과 그들의 연관관계에 대한 설계(진행중). 연관관계 편의 메소드 구상.
핵심 비즈니스 로직에 대한 구체화
인력 및 실력 부족으로 인한 계획 변경. api 문서 Spring-REST-Docs -> Swagger
REST-docs의 경우 매 api 마다 testcode를 작성해야 하는데, 현재 상황에서는 시간의 부족을 느낌.

