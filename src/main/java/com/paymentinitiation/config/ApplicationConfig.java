package com.paymentinitiation.config;

import static com.paymentinitiation.constant.PaymentInitiationConstant.*;

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

import com.paymentinitiation.enums.ErrorReasonCode;
import com.paymentinitiation.exception.UnknownCertificateException;

@EnableWebSecurity
public class ApplicationConfig extends WebSecurityConfigurerAdapter {


  Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    logger.debug("Entering method name is : {}", "configure");
    http.x509().subjectPrincipalRegex(CN_REGEX).and().authorizeRequests().anyRequest()
        .authenticated().and().userDetailsService(userDetailsService()).csrf().disable();
    logger.debug("Exiting method name is : {}", "configure");
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    logger.debug("Entering method name is : {}", "UserDetailsService");
    return username -> {
      if (username != null) {
        if (SANDBOX_TPP.equalsIgnoreCase(username)) {
          logger.info("user name is : {}", username);
          return new User(username, PASSWORD,
              AuthorityUtils.commaSeparatedStringToAuthorityList(ROLE_ADMIN));

        } else {
          logger.info("UserDetailsService getting exception : {}",
              ErrorReasonCode.UNKNOWN_CERTIFICATE.getReasonCode());
          throw new UnknownCertificateException(
              ErrorReasonCode.UNKNOWN_CERTIFICATE.getReasonCode());
        }
      }
      logger.info("UserDetailsService getting exception : {}",
          ErrorReasonCode.UNKNOWN_CERTIFICATE.getReasonCode());
      throw new UnknownCertificateException(ErrorReasonCode.UNKNOWN_CERTIFICATE.getReasonCode());
    };
  }
}
