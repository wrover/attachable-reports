package me.wrover.cuba.attachablereports.domain;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = AttachmentTypedConstraintValidator.class)
public @interface AttachmentTypedConstraint {

    String message() default "{msg://me.wrover.cuba.attachablereports.domain/AttachmentTypedConstraint.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
