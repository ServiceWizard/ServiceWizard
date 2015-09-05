package com.servicewizard.generation;

import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

public class HtmlDocGenerator {

    public void generate(List<Service> services, String fileName) {
        try {
            PrintStream fileWriter = new PrintStream(fileName);
            generate(services, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate(List<Service> services, PrintStream output) throws IOException {
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
