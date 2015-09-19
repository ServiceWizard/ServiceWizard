package com.servicewizard.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.servicewizard.locator.JerseyResourceLocator;
import com.servicewizard.transformer.TemplateTransformer;

/**
 * A default concrete subclass of <code>AbstractDocumentationResource</code>. This will serve docs
 * at <code>/docs</code>, using the default built-in template. For any behavior other than this, use
 * a custom subclass of <code>AbstractDocumentationResource</code>.
 * 
 * @author forana
 */
@Path("/docs")
@Produces(MediaType.TEXT_HTML)
public final class DefaultHTMLDocumentationResource extends AbstractDocumentationResource {
	public DefaultHTMLDocumentationResource(String apiName, String urlBase, String packagePath) {
		super(apiName, urlBase, TemplateTransformer.getDefaultHTMLTransformer(), new JerseyResourceLocator(packagePath));
	}
	
	@GET
	public String getDocumentationHTML() {
		return getDocumentation();
	}
}
