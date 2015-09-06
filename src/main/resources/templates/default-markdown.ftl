# ${apiName}

<#if introText??>${introText}</#if>

<#list services as service>
## ${service.name}

<#list service.methods as method>
### ${method.verb} ${method.relativePath!"/"} <#if method.queryParameters?size &gt; 0>(${method.queryParameters?join(", ")})</#if>

${method.title!"(no name)"}

<#if method.description??>${method.description}</#if>
</#list>

</#list>
