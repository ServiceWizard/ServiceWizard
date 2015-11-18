# ${apiName}

<#if overview??>${overview}</#if>

<#list services as service>
## ${service.name}

<#list service.methods as method>
### ${method.verb} ${method.path!"/"} <#if method.queryParameters?size &gt; 0>(${method.queryParameterNames?join(", ")})</#if>

${method.title!"(no name)"}

<#if method.description??>
${method.description}
</#if>

<#if method.pathParameters?size &gt; 0>
##### Path parameters

<#list method.pathParameters as parameter>
${parameter.name}<#if parameter.description??> - ${parameter.description}</#if>

</#list>
</#if>

<#if method.queryParameters?size &gt; 0>
##### Query parameters

<#list method.queryParameters as parameter>
${parameter.name}<#if parameter.defaultValue??>=${parameter.defaultValue}</#if><#if parameter.description??> - ${parameter.description}</#if>

</#list>
</#if>

</#list>

</#list>
