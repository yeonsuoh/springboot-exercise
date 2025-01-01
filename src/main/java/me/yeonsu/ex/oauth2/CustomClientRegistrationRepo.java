package me.yeonsu.ex.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

// 참고용, 실제로 적용하지 않음
//@Configuration
public class CustomClientRegistrationRepo {
    private final SocialClientRegistration socialClientRegistration;

    public CustomClientRegistrationRepo(SocialClientRegistration socialClientRegistration) {
        this.socialClientRegistration = socialClientRegistration;
    }

    public ClientRegistrationRepository clientRegistrationRepository() {

        return new InMemoryClientRegistrationRepository(
                socialClientRegistration.naverClientRegistration(),
                socialClientRegistration.googleClientRegistration()
        );
    }
}
