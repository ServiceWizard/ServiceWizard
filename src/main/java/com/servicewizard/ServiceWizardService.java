
package com.servicewizard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class that represents a web service, allowing Service Wizard to identify this class when
 * generating code.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface ServiceWizardService {

	/**
	 * The name of the service, as it will appear in generated code.
	 *
	 * This should be in PascalCase and contain no spaces.
	 */
	public String name();
}
