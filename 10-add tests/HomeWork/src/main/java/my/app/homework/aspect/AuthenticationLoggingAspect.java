package my.app.homework.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuthenticationLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationLoggingAspect.class);

    @Pointcut("execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))")
    public void authenticationAttempt() {}

    @AfterReturning(value = "authenticationAttempt()", returning = "authResult")
    public void logSuccessfulAuthentication(JoinPoint joinPoint, Authentication authResult) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authResult;
        String username = token.getName();
        LocalDateTime now = LocalDateTime.now();

        logger.info("User '{}' successfully authenticated at {}", username, now);
    }

    @AfterThrowing(value = "authenticationAttempt()", throwing = "ex")
    public void logFailedAuthentication(JoinPoint joinPoint, AuthenticationException ex) {
        Object[] args = joinPoint.getArgs();
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) args[0];
        String username = token.getName();
        LocalDateTime now = LocalDateTime.now();

        logger.warn("Unauthorized attempt for user '{}' at {}", username, now);
    }
}
