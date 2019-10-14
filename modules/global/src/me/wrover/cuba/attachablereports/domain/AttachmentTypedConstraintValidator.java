package me.wrover.cuba.attachablereports.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AttachmentTypedConstraintValidator
        implements ConstraintValidator<AttachmentTypedConstraint, Attachment> {

    public void initialize(AttachmentTypedConstraint constraint) {
    }

    public boolean isValid(Attachment a, ConstraintValidatorContext context) {
        switch (a.getSource()) {
            case ATTACHED:
                return a.getType() != null;
            case GENERATED:
                return a.getReport() != null && a.getTemplateCode() != null;
            default:
                throw new RuntimeException("unsupported attachment type " + a.getSource().getId());
        }
    }
}
