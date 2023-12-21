/* (C)2023 */
package com.springbootbooks.api.adnotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface PatchAllowed {
    boolean isPatchAllowed() default false;
}
