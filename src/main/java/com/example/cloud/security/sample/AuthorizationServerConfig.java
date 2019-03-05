package com.example.cloud.security.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager manager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clientConfig) throws Exception {
        final PasswordEncoder encoder = new BCryptPasswordEncoder();

        clientConfig
                .inMemory()
                .withClient("fake-client-id")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read")
                .secret("{bcrypt}" + encoder.encode("fake-client-secret"))
                .authorities("oauth2")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfig) throws Exception {
        endpointsConfig
                .tokenStore(getTokenStore())
                .authenticationManager(manager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
        ;
    }

    private TokenStore getTokenStore() {
        return new InMemoryTokenStore();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer config) throws Exception {
        config.allowFormAuthenticationForClients();
    }
}
