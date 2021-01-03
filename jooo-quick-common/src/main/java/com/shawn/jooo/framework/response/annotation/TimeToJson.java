package com.shawn.jooo.framework.response.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shawn.jooo.framework.response.convert.TimeToJsonDeserializer;
import com.shawn.jooo.framework.response.convert.TimeToJsonSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = TimeToJsonSerializer.class)
@JsonDeserialize(using = TimeToJsonDeserializer.class)
public @interface TimeToJson {

    TimeUnit unit() default TimeUnit.MILLISECONDS;

    String format() default "yyyy-MM-dd HH:mm:ss";
}
