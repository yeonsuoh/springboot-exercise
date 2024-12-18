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

```
