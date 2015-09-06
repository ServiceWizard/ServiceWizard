package com.servicewizard.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.servicewizard.locator.JerseyResourceLocator;
import com.servicewizard.model.Service;
import com.servicewizard.transformer.HtmlDocTransformer;

public abstract class DocumentationResource {

    protected String getDocumentation() {
        return documentation;
    }

	public DocumentationResource(JerseyResourceLocator locator) {
        List<Service> services = locator.locate();
        cacheDocumentation(services);
    }

    private void cacheDocumentation(List<Service> services) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteStream);

        HtmlDocTransformer generator = new HtmlDocTransformer();
        try {
            generator.transform(services, printStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        documentation = byteStream.toString();
    }

    private String documentation;
}
