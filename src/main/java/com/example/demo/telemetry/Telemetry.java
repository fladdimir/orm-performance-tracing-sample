package com.example.demo.telemetry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;

@Aspect
// the aspect needs to run before / after the @Transactional-aspect, to also
// capture its executed behavior (e.g. flush / commit)
@Order(1) // (@Transactional is LOWEST_PRECEDENCE by default)
@Component
public class Telemetry {

    private static final String APP_PERFORMANCE_TRACE_TAG = "appPerformanceTrace";

    @Around(value = "@annotation(tracedAnnotation)")
    public Object aroundTraced(ProceedingJoinPoint pjp, Traced tracedAnnotation) throws Throwable {

        String method = pjp.getSignature().getDeclaringType().getSimpleName() + "#" + pjp.getSignature().getName();
        Span span = start(method);
        span.setAttribute(APP_PERFORMANCE_TRACE_TAG, true);

        Object retVal;
        try (Scope scope = span.makeCurrent()) {

            retVal = pjp.proceed();

            span.setStatus(StatusCode.OK);
        } catch (Throwable e) {
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
            throw e;
        } finally {
            span.end();
        }

        return retVal;
    }

    private static Span start(String name) {
        Tracer tracer = GlobalOpenTelemetry.getTracer("application");
        return tracer.spanBuilder(name).startSpan();
    }

}
