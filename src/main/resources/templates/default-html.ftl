<!DOCTYPE html>
<html>
    <head>
        <title>${apiName} Documentation</title>
        <meta charset="UTF-8" />
    </head>
    <body>
        <h1>${apiName} Documentation</h1>
        <hr/>
        <#if introText??><p>
            ${introText}
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
            </#list>
        </#list>
    </body>
</html>
