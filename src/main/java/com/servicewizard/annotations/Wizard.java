
package com.servicewizard.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a service class or method with information to be used in documentation and/or code
 * generation by ServiceWizard.
 *
 * Many of these do not apply to services - they will instead be applied as defaults for all methods
 * contained within. These are marked as such.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.METHOD })
public @interface Wizard {

	/**
	 * The title of the service or route method. This is to be used for documentation purposes only.
	 */
	public String title() default "";

	/**
	 * The name of the service or route method. This is to be used for code purposes only, such as
	 * the name of a generated function, or an HTML anchor.
	 * 
	 * This should contain only alphanumeric characters or underscores - no spaces.
	 * 
	 * If not set, a value will be chosen by the {@link com.servicewizard.locator.ServiceLocator}
	 * implementation.
	 */
	public String name() default "";

	/**
	 * An overview of the service or a description of the service method. For documentation
	 * purposes.
	 */
	public String description() default "";

	/**
	 * Flag noting that authentication credentials are required for this route method.
	 * 
	 * If applied to a service class, this is used as the default for every route method contained
	 * within.
	 * 
	 * T
	 */
	public String requiresAuthentication() default "";
	
	/**
	 * Notes that a certain permission or role is required for this route method (for example,
	 * "Administrator" or "Moderator").
	 * 
	 * If applied to a service class, this is used as the default for every route method contained
	 * within.
	 */
	public String permissionRequired() default "";
	
	/**
	 * Defines an ascending ordering for service classes or route methods that is used to sort them
	 * before transforming to code or documentation. Service classes are all sorted together, and
	 * route methods are sorted per service. Methods or classes without a set <code>ordering</code>
	 * are sorted after those that have one.
	 * 
	 * This is useful for both organizational purposes and stability purposes - without setting an
	 * ordering, the order of output may change between runs, resulting in confusion and messy
	 * diffs.
	 */
	public double ordering() default Double.MAX_VALUE;

}
