
package com.servicewizard.locator;

import java.util.List;

import com.servicewizard.model.Service;

/**
 * Scans source classes for web API resources and represents them as Service instances for code
 * generation to act upon.
 *
 * The implementation will depend on the web service framework being targeted, Spring or Jersey for
 * example.
 *
 * Also depending on the targeted framework, a ServiceLocator may scan for classes by annotation, or
 * scan configuration XML.
 *
 */
public interface ServiceLocator {

	/**
	 * Creates Service instances representing the located web resources
	 */
	List<Service> locate();
}
