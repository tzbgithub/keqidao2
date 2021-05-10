package com.qidao.application.model.validator;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator;

import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义邮箱验证
 **/
@Slf4j
public class CustomEmailValidator extends AbstractEmailValidator<CustomEmail> {
    private Pattern pattern = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
    ;

    public CustomEmailValidator() {
        log.info("CustomEmailValidator -> 自定义邮箱验证初始化");
    }

    @Override
    public void initialize(CustomEmail emailAnnotation) {
        super.initialize(emailAnnotation);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else {
            boolean isValid = super.isValid(value, context);
            if (isValid) {
                Matcher m = this.pattern.matcher(value);
                return !m.find();
            } else {
                return isValid;
            }
        }
    }
}
