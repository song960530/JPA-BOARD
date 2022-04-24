## 사용 기술
- BE : springboot,spring-security, jpa(hibernate), spring-data-jpa, querydsl, jwt, oracle
- FE : thymeleaf (템플릿 엔진은 추후 변경할 수 있음)
- infra : 네이버클라우드, docker(현재 db만 띄워둠)



## 현재 구현된 부분
```
회원
  ㄴ 회원가입
  ㄴ 로그인
게시판
  ㄴ 게시글 등록 (파일업로드 포함)
  ㄴ 게시글 상세
  ㄴ 댓글 crud
```


## 패키지 구조
```
domain
  ㄴ board            -> 게시판 관련 패키지
      ㄴ controller
      ㄴ dto
      ㄴ entity
      ㄴ exception
      ㄴ repository
      ㄴ service
  ㄴ member           -> 회원 관련 패키지
  ㄴ model            -> 공통 엔티티 패키지
global
  ㄴ annotation       -> 커스텀 어노테이션 패키지
  ㄴ aop	      -> AOP 패키지
  ㄴ common
      ㄴ response     -> api 응답 전달시 공통으로 사용하는 dto
  ㄴ config
      ㄴ jwt	      -> JWT 설정
      ㄴ security     -> spring-security 설정
  ㄴ error	      -> error 핸들러
```
