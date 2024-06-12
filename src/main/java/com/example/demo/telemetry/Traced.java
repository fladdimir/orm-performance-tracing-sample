package com.example.demo.telemetry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * marks a spring bean method call to be intercepted and traced for performance
 * analysis.
 * corresponding traces are tagged with "appPerformanceTrace"
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Inherited
public @interface Traced {
}
