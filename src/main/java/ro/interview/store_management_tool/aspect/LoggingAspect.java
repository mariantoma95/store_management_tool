package ro.interview.store_management_tool.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Aspect
@Configuration
@Log4j2
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @Around("execution(public * ro.interview.store_management_tool.controller.*.*(..))")
    public Object loggingAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getResponse();

        String uri = request.getMethod() + " " + request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        String traceId = request.getHeader("X-Trace-Id");
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
        }
        if (response != null) {
            response.setHeader("X-Trace-Id", traceId);
        }
        MDC.put("traceId", traceId);

        String argsJson = objectMapper.writeValueAsString(args);
        log.info("Request made on url: {}, with args: {}", uri, argsJson);

        try {
            Object result = joinPoint.proceed();

            String resultJson = objectMapper.writeValueAsString(result);
            log.info("Successfully executed. Response: {}", resultJson);

            return result;
        } catch (Exception ex) {
            log.error("Error while executing request. Error message: {}", ex.getMessage());

            throw ex;
        } finally {
            MDC.clear();
        }
    }
}
