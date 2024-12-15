package my.app.homework.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class RestLoggingAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerMethods() {}

    @Around("restControllerMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            attributes.setAttribute("startTime", startTime, RequestAttributes.SCOPE_REQUEST);
            attributes.setAttribute("pjp", joinPoint, RequestAttributes.SCOPE_REQUEST);
        }

        try {
            Object result = joinPoint.proceed();

            return result;
        } catch (Throwable e) {
            log.error("Error during execution of {}: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()), e);
            throw e;
        }
    }

    @AfterReturning(pointcut = "restControllerMethods()", returning = "result")
    public void afterRestControllerMethodExecution(Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Optional<ProceedingJoinPoint> optionalPjp = Optional.ofNullable((ProceedingJoinPoint) attributes.getAttribute("pjp", RequestAttributes.SCOPE_REQUEST));
            Optional<Long> optionalStartTime = Optional.ofNullable((Long) attributes.getAttribute("startTime", RequestAttributes.SCOPE_REQUEST));

            if (optionalPjp.isPresent() && optionalStartTime.isPresent()) {
                ProceedingJoinPoint pjp = optionalPjp.get();
                Long startTime = optionalStartTime.get();

                if (result instanceof ResponseEntity<?> responseEntity) {
                    int statusCode = responseEntity.getStatusCode().value();

                    if (isSuccess(statusCode)) {
                        logInfo(pjp, result, startTime);
                    } else if (isWarning(statusCode)) {
                        logWarn(pjp, result, startTime);
                    } else if (responseEntity.getBody() == null && statusCode == 404) {
                        logWarn(pjp, result, startTime, statusCode);
                    }
                } else {
                    logInfo(pjp, result, startTime);
                }
            }
        }
    }

    private boolean isSuccess(int statusCode) {
        return statusCode >= 200 && statusCode <= 299;
    }

    private boolean isWarning(int statusCode) {
        return statusCode == 400 || statusCode == 404;
    }

    private void logInfo(ProceedingJoinPoint joinPoint, Object result, long startTime) {
        log.info("REST Method {} executed in {} ms with result {}",
                joinPoint.getSignature().toShortString(),
                System.currentTimeMillis() - startTime,
                result instanceof ResponseEntity ? ((ResponseEntity<?>) result).getBody() : result);
    }

    private void logWarn(ProceedingJoinPoint joinPoint, Object result, long startTime) {
        log.warn("REST Method {} executed in {} ms with result {}",
                joinPoint.getSignature().toShortString(),
                System.currentTimeMillis() - startTime,
                result instanceof ResponseEntity ? ((ResponseEntity<?>) result).getBody() : result);
    }

    private void logWarn(ProceedingJoinPoint joinPoint, Object result, long startTime, int statusCode) {
        log.warn("REST Method {} executed in {} ms with result {}. Status code: {}",
                joinPoint.getSignature().toShortString(),
                System.currentTimeMillis() - startTime,
                result instanceof ResponseEntity ? ((ResponseEntity<?>) result).getBody() : result,
                statusCode);
    }
}
