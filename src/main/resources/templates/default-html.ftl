<!DOCTYPE html>
<html>
    <head>
        <title>${apiName} Documentation</title>
        <meta charset="UTF-8" />
    </head>
    <body>
        <h1>${apiName} Documentation</h1>
        <hr/>
        <#if overview??><p>
            ${overview}
        </p></#if>
        <#list services as service>
            <h1>${service.name}</h1>
            <#list service.methods as method>
                <h2>
                    [${method.verb}]
                    ${method.path!"/"}
                    <#if method.queryParameters?size &gt; 0>
                        (${method.queryParameterNames?join(",")})
                    </#if>
                </h2>
                <strong>${method.title!"(no name)"}</strong>
                <#if method.description??><p>
                    ${method.description}
                </p></#if>

                <#if method.pathParameters?size &gt; 0>
                    <p>
                    <strong>Path Parameters</strong>
                    <ul>
                        <#list method.pathParameters as parameter>
                            <li>${parameter.name}
                                <#if parameter.description??> - ${parameter.description}</#if>
                            </li>
                        </#list>
                    </ul>
                    </p>
                </#if>

                <#if method.queryParameters?size &gt; 0>
                    <p>
                    <strong>Query Parameters</strong>
                    <ul>
                        <#list method.queryParameters as parameter>
                            <li>${parameter.name}
                                <#if parameter.defaultValue??>=${parameter.defaultValue}</#if>
                                <#if parameter.description??> - ${parameter.description}</#if>
                            </li>
                        </#list>
                    </ul>
                    </p>
                </#if>
            </#list>
        </#list>
    </body>
</html>
