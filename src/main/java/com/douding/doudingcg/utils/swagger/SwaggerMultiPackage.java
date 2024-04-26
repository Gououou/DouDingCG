package com.douding.doudingcg.utils.swagger;

import springfox.documentation.RequestHandler;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Guo
 * @create 2024-04-26 14:37
 */
public class SwaggerMultiPackage {
    protected static Predicate<RequestHandler> basePackage(String... basePackages){
        return input -> declaringClass(input).map(handlerPackage(basePackages)::apply).orElse(true);
    }

    protected static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

    protected static Function<Class<?>, Boolean> handlerPackage(String[] basePackages) {
        return input -> {
            for (String basePackage : basePackages) {
                boolean with = input.getPackage().getName().startsWith(basePackage);
                if(with){
                    return true;
                }
            }
            return false;
        };
    }

}
