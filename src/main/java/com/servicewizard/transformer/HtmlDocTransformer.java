package com.servicewizard.transformer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

public class HtmlDocTransformer implements Transformer {

	@Override
	public void transform(String moduleName, String baseUrl, List<Service> services, File outputRoot) throws IOException {
        try {
			transform(services, new PrintStream(new File(outputRoot, "index.html")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transform(List<Service> services, PrintStream output) throws IOException {
        for (Service service : services) {
            output.println(String.format("<h1>%s<h1/>", service.getName()));

            for (ServiceMethod method : service.getMethods()) {
                String verbName = "[" + method.getVerb() + "]";

                // Join the query parameters
                String paramString = "";
                if (!method.getQueryParameters().isEmpty()) {
                    paramString = method.getQueryParameters()
                            .stream()
                            .collect(Collectors.joining(",", "(", ")"));
                }

                output.println(String.format("<h2>%s %s %s</h2>", verbName, method.getRelativePath(), paramString));

                if (method.getTitle() != null)
                    output.println(String.format("<strong>%s</strong>", method.getTitle()));

                if (method.getDescription() != null)
                    output.println(String.format("<p>%s</p>", method.getDescription()));

                output.println();
            }
        }
    }
}
