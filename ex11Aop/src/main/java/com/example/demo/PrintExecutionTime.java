package com.example.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 런타임에 리플렉션으로 사용 가능
@Target(ElementType.METHOD) // 메서드에만 적용 가능
public @interface PrintExecutionTime {

}
