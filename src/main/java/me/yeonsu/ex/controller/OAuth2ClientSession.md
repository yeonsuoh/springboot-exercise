# 스프링 OAuth2 클라이언트 세션
### 동작 플로우
* [로그인 시도] : /oauth2/authorization/서비스명 
* Oauth2AuthorizationRequestRedirectFilter
* (외부 소셜 로그인 지원 서비스 - 인증 서버) 해당 서비스 로그인 페이지 응답
  * 성공하면 미리 등록한 우리 서버 특정 경로로 리다이렉트
* [로그인 성공 리다이렉트] : /login/oauth2/code/서비스명 
  * 외부 서비스 인증 서버에서 Code를 보내줌
* OAuth2LoginAuthenticationFilter
* OAuth2LoginAuthenticationProvider
  * 외부 서비스 인증서버로부터 Code 및 등록 정보로 AccessToken 발급 받음
  * AccessToken으로 외부 서비스 리소스 서버로부터 유저 정보 획득
* OAuth2UserDetails / OAuth2UserDetailsService
  * 세션 저장과 같은 나머지 시큐리티 로직 동작
---
### 각각의 필터가 동작하는 주소 (관습)
* /oauth2/authorization/서비스명 
* /login/oauth2/code/서비스명 (외부 인증 서버에 설정할 redirect url)
---
### OAuth2 인증 및 동작을 위한 변수
* 변수 설정만 진행하면 OAuth2AuthorizationRedirectFilter -> OAuth2LoginAuthenticationFilter -> OAuth2LoginAuthenticationProvider 까지의 과정을 추가 설정하지 않아도 자동으로 진행한다.
* 따라서 사용자는 UserDetailsService, UserDetails만 구현하면 됨
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          서비스명:
            client-name: 서비스명
            client-id: 서비스에서 발급받은 아이디
            client-secret: 서비스에서 발급받은 비밀번호
            redirect-uri: 서비스에 등록한 우리쪽 로그인 성공 URI
            authorization-grant-type: authorization_code
            scope: 리소스 서버에서 가져올 데이터 범위
        provider:
          서비스명:
            authorization-uri: 서비스 로그인 창 주소
            token-uri: 토큰 발급 서버 주소 (인증 서버)
            user-info-uri: 사용자 정보 획득 주소 (리소스 서버)
            user-name-attribute: 응답 데이터 변수

```
* Registration은 외부 서비스에서 우리 서비스를 특정하기 위해 등록하는 정보여서 등록이 필수적
* 하지만 Provider의 경우 서비스별로 정해진 값 존재, 유명 서비스의 경우 내부적으로 데이터 가지고 있음 (구글, 페이스북, 깃허브 등)
