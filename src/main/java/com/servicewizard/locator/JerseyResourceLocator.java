
package com.servicewizard.locator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.reflections.Reflections;

import com.servicewizard.annotations.Wizard;
import com.servicewizard.annotations.WizardDesc;
import com.servicewizard.annotations.WizardIgnore;
import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;
import com.servicewizard.model.ServiceMethodParameter;

/**
 * {@link com.servicewizard.locator.ServiceLocator} implementation integrating ServiceWizard with
 * Jersey.
 * 
 * Service classes are discovered by having <code>@Wizard</code> applied. Route methods are
 * discovered in those classes by having one of the <code>java.ws.rs</code> verb annotations
 * applied.
 */
public class JerseyResourceLocator implements ServiceLocator {

	@Override
	public List<Service> locate() {
		// Find all classes annotated
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Wizard.class);

		// Build each non-ignored class into a Service object
		return serviceClasses.stream()
				.filter(c -> !c.isAnnotationPresent(WizardIgnore.class))
				.map(this::buildService)
				.collect(Collectors.toList());
	}

	/**
	 * Creates a ServiceLocation that will scan the given package for annotated classes
	 *
	 * @param packageName the java package to scan
	 */
	public JerseyResourceLocator(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Creates a Service object based on the annotations present on the given class
	 */
	private Service buildService(Class<?> serviceClass) {
		Wizard serviceMeta = serviceClass.getAnnotation(Wizard.class);

		Service service = new Service();

		service.setName(serviceMeta.name().isEmpty() ? serviceClass.getCanonicalName() : serviceMeta.name());
		service.setTitle(serviceMeta.title().isEmpty() ? service.getName() : serviceMeta.title());
		service.setDescription(serviceMeta.description());
		service.setOrdering(serviceMeta.ordering());

		// Path annotation at resource level
		// strictly speaking, jersey *should* freak out if a resource doesn't have this
		String resourcePath = "/";
		if (serviceClass.isAnnotationPresent(Path.class))
			resourcePath = serviceClass.getAnnotation(Path.class).value();

		for (Method classMethod : serviceClass.getMethods())
			if (!classMethod.isAnnotationPresent(WizardIgnore.class)) {
				String verb = getVerb(classMethod);
				if (verb != null)
					service.addMethod(buildMethod(verb, classMethod, resourcePath, serviceMeta));
			}

		return service;
	}

	/**
	 * Returns a string representation of the HTTP verb the provided method uses, or null.
	 */
	private static String getVerb(Method method) {
		// the javax.ws.rs annotations are all annotated with HttpMethod for some reason, which
		// includes the verb as a string.
		for (Annotation a : method.getAnnotations())
			// we have to go deeper
			for (Annotation b : a.annotationType().getAnnotations())
				if (b instanceof HttpMethod)
					return ((HttpMethod) b).value();

		return null;
	}

	/**
	 * Creates a ServiceMethod based on the annotations present on the given method
	 *
	 * @param resourcePath the path that this method's path should be relative to
	 * @param classMethod the reflected Java method to scan for annotations
	 */
	private ServiceMethod buildMethod(String verb, Method classMethod, String basePath,
			Wizard serviceMeta) {
		ServiceMethod serviceMethod = new ServiceMethod(classMethod.getName(), verb);
		
		Wizard methodMeta = classMethod.getAnnotation(Wizard.class);
		
		if (methodMeta != null && !methodMeta.name().isEmpty())
			serviceMethod.setName(methodMeta.name());
		
		if (methodMeta != null && !methodMeta.title().isEmpty())
			serviceMethod.setTitle(methodMeta.title());
		else
			serviceMethod.setTitle(serviceMethod.getName());
		
		if (methodMeta != null && !methodMeta.description().isEmpty())
			serviceMethod.setDescription(methodMeta.description());
		
		String path = basePath;
		if (classMethod.isAnnotationPresent(Path.class))
			path += classMethod.getAnnotation(Path.class).value();
		serviceMethod.setPath(path);

		if (methodMeta != null && !methodMeta.requiresAuthentication().isEmpty())
			serviceMethod.setRequiresAuthentication(Boolean.valueOf(methodMeta.requiresAuthentication()));
		else if (!serviceMeta.requiresAuthentication().isEmpty())
			serviceMethod.setRequiresAuthentication(Boolean.valueOf(serviceMeta.requiresAuthentication()));
		
		if (methodMeta != null && !methodMeta.permissionRequired().isEmpty())
			serviceMethod.setPermissionRequired(methodMeta.permissionRequired());
		else if (!serviceMeta.permissionRequired().isEmpty())
			serviceMethod.setPermissionRequired(serviceMeta.permissionRequired());
		
		serviceMethod.setOrdering(methodMeta == null ? Double.MAX_VALUE : methodMeta.ordering());

		// parameters and request body
		for (Parameter param : classMethod.getParameters())
			if (param.isAnnotationPresent(PathParam.class))
				serviceMethod.addPathParameter(buildParameter(
						param.getAnnotation(PathParam.class).value(), param));
			else if (param.isAnnotationPresent(QueryParam.class))
				serviceMethod.addQueryParameter(buildParameter(
						param.getAnnotation(QueryParam.class).value(), param));
			else if (!param.isAnnotationPresent(Context.class))
				serviceMethod.setHasRequestBody(true);

		return serviceMethod;
	}

	private static ServiceMethodParameter buildParameter(String name, Parameter param) {
		ServiceMethodParameter serviceMethodParam = new ServiceMethodParameter(name);
		if (param.isAnnotationPresent(WizardDesc.class))
			serviceMethodParam.setDescription(param.getAnnotation(WizardDesc.class).value());
		if (param.isAnnotationPresent(DefaultValue.class))
			serviceMethodParam.setDefaultValue(param.getAnnotation(DefaultValue.class).value());
		return serviceMethodParam;
	}

	private String packageName;
}
