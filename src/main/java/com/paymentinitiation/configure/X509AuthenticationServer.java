package com.paymentinitiation.configure;

import com.paymentinitiation.exception.UnknownCertificateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.paymentinitiation.constant.PaymentInitiationConstant.CN_$;
import static com.paymentinitiation.constant.PaymentInitiationConstant.SANDBOX_TPP;

@EnableWebSecurity
public class X509AuthenticationServer extends WebSecurityConfigurerAdapter {

  Logger logger = LoggerFactory.getLogger(X509AuthenticationServer.class);

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    logger.debug("Started configure");
    http.x509().subjectPrincipalRegex(CN_$).and().authorizeRequests().anyRequest().authenticated()
        .and().userDetailsService(userDetailsService()).csrf().disable();
    logger.debug("Exiting configure");
  }

  @Bean
  public UserDetailsService userDetailsService() {
    logger.debug("Started UserDetailsService");
    return new UserDetailsService() {
      @Override
      public User loadUserByUsername(String username) {
        if (username != null) {
          if (SANDBOX_TPP.equalsIgnoreCase(username)) {
            logger.debug("Valid user");
            return new User(username, "",
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));

          } else {
            logger.debug("<------UNKNOWN_CERTIFICATE------>");
            throw new UnknownCertificateException("UNKNOWN_CERTIFICATE");
          }
        }
        logger.debug("<------UNKNOWN_CERTIFICATE------>");
        throw new UnknownCertificateException("UNKNOWN_CERTIFICATE");
      }
    };
  }
}
