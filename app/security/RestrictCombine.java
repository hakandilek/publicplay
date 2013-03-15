package security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import play.mvc.With;
import be.objectify.deadbolt.java.DeadboltHandler;

@With(RestrictCombineAction.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
public @interface RestrictCombine
{
    /**
     * The role names of roles with access to the target.
     *
     * @return the role names
     */
    String[] roles();

    /**
     * Indicates the expected response type.  Useful when working with non-HTML responses.  This is free text, which you
     * can use in {@link be.objectify.deadbolt.DeadboltHandler#onAccessFailure} to decide on how to handle the response.
     *
     * @return a content indicator
     */
    String content() default "";
    
    Class<? extends RequestPermission> with();

    /**
     * Use a specific {@link be.objectify.deadbolt.DeadboltHandler} for this restriction in place of the global one.
     *
     * @return the class of the DeadboltHandler you want to use
     */
    Class<? extends DeadboltHandler> handler() default DeadboltHandler.class;
}