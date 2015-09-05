package com.servicewizard.resource;

import com.servicewizard.ServiceLocator;
import com.servicewizard.generation.MarkdownGenerator;
import com.servicewizard.model.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public abstract class DocumentationResource {

    protected String getDocumentation() {
        return documentation;
    }

    public DocumentationResource(ServiceLocator locator) {
        List<Service> services = locator.locate();
        cacheDocumentation(services);
    }

    private void cacheDocumentation(List<Service> services) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteStream);

        MarkdownGenerator generator = new MarkdownGenerator();
        try {
            generator.generate(services, printStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        documentation = byteStream.toString();
    }

    private String documentation;
}
