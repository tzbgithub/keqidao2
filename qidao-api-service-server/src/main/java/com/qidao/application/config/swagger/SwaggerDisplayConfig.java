package com.qidao.application.config.swagger;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.Annotations;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

@Component
@Primary
@Slf4j
public class SwaggerDisplayConfig implements ModelPropertyBuilderPlugin {

    /**
     * 是否允许swagger
     */
    @Value("${knife4j.enable:true}")
    private Boolean enableSwagger;

    @Override
    public void apply(ModelPropertyContext context) {
        //如果不支持swagger的话，直接返回
        if (!enableSwagger) {
            return;
        }

        //为枚举字段设置注释
        descForEnumFields(context);
    }

    /**
     * 为枚举字段设置注释
     */
    private void descForEnumFields(ModelPropertyContext context) {
        Optional<ApiModelProperty> annotation = Optional.empty();

        if (context.getAnnotatedElement().isPresent()) {
            annotation = ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get());
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = Annotations.findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class);
        }

        // 没有@ApiModelProperty 或者 reference 未定义类，直接返回
        if (!annotation.isPresent() || StrUtil.isBlank(annotation.map(ApiModelProperty::reference).get())) {
            return;
        }

        // @ApiModelProperties中的 reference 指定的class类型
        Class rawPrimaryType;
        try {
            rawPrimaryType = Class.forName((annotation.get()).reference());
        } catch (ClassNotFoundException e) {
            // 无法转化，直接忽略
            return;
        }

        // 检查是否有关键的 type 和 val 方法
        Method getType = ReflectUtil.getPublicMethod(rawPrimaryType, "getType");
        if(null == getType) {
            return;
        }
        Method getVal = ReflectUtil.getPublicMethod(rawPrimaryType, "getVal");
        if (null == getVal) {
            return;
        }

        // 逆向获取枚举中所有的值
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Object[] enumConstants = rawPrimaryType.getEnumConstants();
            for (Object obj : enumConstants) {
                String getTypeStr = String.valueOf(getType.invoke(obj));
                String getValStr = String.valueOf(getVal.invoke(obj));
                stringBuilder
                        .append(getTypeStr)
                        .append(" : ")
                        .append(getValStr)
                        .append("<br/>");
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            return;
        }

        context.getSpecificationBuilder().description(stringBuilder.toString());
    }


    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
