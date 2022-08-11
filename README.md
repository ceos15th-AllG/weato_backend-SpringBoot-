# weato_backend-SpringBoot-


### 20220705
<strong>변경사항</strong>
<ul>
  <li>like 분리 -> PostLike, CommentLike</li>
  <li>tag class 정리 - Tag(class), TagType(enum)을 통한 멤버 - 태그 - 뉴스레터 간 연관관계 설정</li>
  <li> Domain layer 거의 마무리, </li>
  <li>기타 각종 엔티티의 메소드들 정리. </li>
</ul>
아쉬운 점 : DDD에 대한 이해 부족. Aggregate가 뭐지 정확히..? 이걸 어떻게 정확히 분류하고 Root 을 정하지??

아마존 s3 이용에 대하여 -> html을 파일이 아닌 body 부분을 string으로 저장할 계획. 굳이 s3에 html(정적파일) 저장..?

DB관련 -> 현재 고민중인 부분은 그래서 EC2에 DB를 MySQL로 바로 올릴지, AWS Aurora DB를 사용할 지 고민중입니다.

AWS 적극적으로 이용 예정
AWS EC2, S3, Lambda, Route53, (AuroraDB), (Docker), Cloudwatch, SES, 

SES는 현재 샌드박스 내부. 24시간 200통/ 각 메일 당 전송시간 1초 -> 사례를 조금 쌓아서 Bounces and complaints control 하고 샌드박스 해제 재신청




### 220715

리드미 제대로 작성 안하고 살았던 것 반성

진행 사항들

1. 웹사이트 api 관련 구현 거의 완료 -> 기능적 구현만을 의미. 쿼리 최적화 및 페이징 혹은 더보기 구현 안함

2. 소셜로그인 방식 SPA스럽게 변경해야함..

3. DB 실제로 쓸 MYSQL 변경 준비하기

4. 뉴스레터를 다루는 방식에 대해

5. 기타 사항들 잠깐씩 수정


### 220718

1. 나의 게으름과 능력부족에 대한 철저한 반성
2. 뉴스레터 및 게시글 페이징 구현. 댓글 페이징에 관해서는??
3. 뭐더냐.. 좋아요 구현 끝. 여기까지 하면 일단 커뮤니티 기능 부분 거의 끝
4. 해커톤 전까지는 단위 테스트 코드 작성 및 api 관련 사항 점검
5. 배포 및 연계 오류 등 확인 예정.


### 220720

~~힘이 빠집니다~~
1. 로그인 관련 처리 -> SPA니까 그거 관련 변경
2. CORS 문제 변경
3. 기본적인 코드들 수정
4. 뭐더라 또 기타 다양한 수정 사항들
5. CI/CD 지금까지 생각 안한거 반성..
6. AWS에 슬슬 배포 관련 찾아보는중
7. DOCKER도 알아보는중..

### 220731
#### 지금까지 한 것
1. GITHUB ACTION과 DOCKER를 이용한 CI/CD
2. NGINX를 이용한 무중단 배포 준비중 
3. 커뮤니티의 기본적 API들 마무리

#### 앞으로 할 것
1. API 성능 최적화
2. 이미지 업로드 관련 부분
3. 설계의 완벽성
4. OAuth2 - 소셜로그인 가능 계정 추가
5. 뉴스레터 수신 이메일 인증 구현

### 20220811
#### 지금까지 한 일 들
1. 조회 쿼리 확인 -> n+1 문제 관련
2. search 관련 repository, servie 계층 관련 마무리
3. 일단 API 서버 마무리 단계..

#### 앞으로 해야 할 일
1. LAMBDA 시작
2. SES-LAMBDA-AWS DB 연동 확인
3. 뉴스레터 저장 방식 다시 구상 -> 파일 스트림?
4. 기타 사항들 관련..
