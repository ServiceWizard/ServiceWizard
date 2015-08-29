
package com.servicewizard;

import com.servicewizard.model.HttpVerb;
import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Scans source classes for @ServiceWizardService annotations and represents them as Service objects
 * for code generation to act upon.
 *
 * When a class is found with the @ServiceWizardService annotation, all its methods are analyzed as
 * potential web service endpoints. If a method has any web service annotations present (@GET,
 * @POST, etc) a ServiceMethod corresponding to that method will be created.
 *
 * A class doesn't need @ServiceWizardMethod present on its methods, but this annotation helps
 * generate more readable code with better documentation. It is recommended to annotate each web
 * service method with @ServiceWizardMethod.
 */
public class ServiceLocator {

	public List<Service> locate() {
		// Find all classes annotated
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(ServiceWizardService.class);

		// Build each class into a Service object
		return serviceClasses.stream()
			.map(this::buildService)
			.collect(Collectors.toList());
	}

	/**
	 * Creates a ServiceLocation that will scan the given package for annotated classes
	 *
	 * @param packageName the java package to scan
	 */
	public ServiceLocator(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Creates a Service object based on the annotations present on the given class
	 */
	private Service buildService(Class<?> serviceClass) {
		String serviceName = serviceClass.getAnnotation(ServiceWizardService.class).name();
		Service service = new Service(serviceName);

		// Path annotation at resource level
		String resourcePath = "";
		if (serviceClass.isAnnotationPresent(Path.class))
			resourcePath = serviceClass.getAnnotation(Path.class).value();

		// Look for methods annotated with HTTP verbs
		for (Method classMethod : serviceClass.getMethods()) {
			ServiceMethod serviceMethod = buildMethod(resourcePath, classMethod);

			if (serviceMethod.getVerb() != null)
				service.addMethod(serviceMethod);
		}

		return service;
	}

	/**
	 * Creates a ServiceMethod based on the annotations present on the given method
	 *
	 * @param resourcePath the path that this method's path should be relative to
	 * @param classMethod the reflected Java method to scan for annotations
	 */
	private ServiceMethod buildMethod(String resourcePath, Method classMethod) {
		ServiceMethod serviceMethod = new ServiceMethod(classMethod.getName());

		// Pull documentation from annotation
		if (classMethod.isAnnotationPresent(ServiceWizardMethod.class)) {
			ServiceWizardMethod methodAnnotation = classMethod.getAnnotation(ServiceWizardMethod.class);
			serviceMethod.setTitle(methodAnnotation.title());
			serviceMethod.setDescription(methodAnnotation.description());
		}

		// Look for verb annotations
		if (classMethod.isAnnotationPresent(GET.class))
			serviceMethod.setVerb(HttpVerb.GET);
		if (classMethod.isAnnotationPresent(POST.class))
			serviceMethod.setVerb(HttpVerb.POST);
		if (classMethod.isAnnotationPresent(DELETE.class))
			serviceMethod.setVerb(HttpVerb.DELETE);

		// Path
		if (classMethod.isAnnotationPresent(Path.class)) {
			Path path = classMethod.getAnnotation(Path.class);
			serviceMethod.setRelativePath(resourcePath + path.value());
		} else {
			// TODO - does Path have a default value?
		}

		// Query parameters and request body
		boolean hasRequestBody = false;
		for (Annotation[] paramAnnotations : classMethod.getParameterAnnotations()) {
			boolean paramIsQueryParam = false;
			for (Annotation paramAnnotation : paramAnnotations) {
				if (paramAnnotation.annotationType().equals(QueryParam.class)) {
					paramIsQueryParam = true;
					QueryParam queryParam = (QueryParam)paramAnnotation;
					serviceMethod.addQueryParameter(queryParam.value());
				}
			}

			// If this parameter is not a query param, it is the request body
			if (!paramIsQueryParam)
				hasRequestBody = true;
		}
		serviceMethod.setHasRequestBody(hasRequestBody);

		return serviceMethod;
	}

	private String packageName;
}
