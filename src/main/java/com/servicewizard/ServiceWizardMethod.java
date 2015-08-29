
package com.servicewizard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method with documentation for a web service method.
 *
 * Apply this annotation to a method of a class annotated with @ServiceWizardService. This
 * annotation then provides extra details about the method that will be used to generate
 * documentation.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ServiceWizardMethod {

	/**
	 * The title of the service method.
	 *
	 * This title appears in documentation and may contain spaces.
	 */
	public String title();

	/**
	 * A description of the service method, its behavior and intended use
	 */
	public String description();
}
