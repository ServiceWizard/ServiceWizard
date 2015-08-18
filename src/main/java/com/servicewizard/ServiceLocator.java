
package com.servicewizard;

import com.servicewizard.model.HttpVerb;
import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public class ServiceLocator {

	public List<Service> locate() {
		// Find all classes annotated
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(ServiceWizardService.class);

		List<Service> services = new LinkedList<>();
		for (Class<?> serviceClass : serviceClasses)
			services.add(processService(serviceClass));

		return services;
	}

	public ServiceLocator(String packageName) {
		this.packageName = packageName;
	}

	private Service processService(Class<?> serviceClass) {
		String serviceName = serviceClass.getAnnotation(ServiceWizardService.class).name();
		Service service = new Service(serviceName);

		// Path annotation at resource level
		String resourcePath = "";
		if (serviceClass.isAnnotationPresent(Path.class))
			resourcePath = serviceClass.getAnnotation(Path.class).value();

		// Look for methods annotated with HTTP verbs
		for (Method classMethod : serviceClass.getMethods()) {
			ServiceMethod serviceMethod = processMethod(resourcePath, classMethod);

			if (serviceMethod.getVerb() != null)
				service.addMethod(serviceMethod);
		}

		return service;
	}

	private ServiceMethod processMethod(String resourcePath, Method classMethod) {
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
		serviceMethod.hasRequestBody(hasRequestBody);

		return serviceMethod;
	}

	private String packageName;
}
