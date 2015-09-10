package com.servicewizard.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows a description to be set for a parameter to a route method.
 * 
 * @author forana
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.PARAMETER)
public @interface WizardDesc {
	public String value();
}
