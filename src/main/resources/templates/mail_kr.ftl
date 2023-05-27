<!DOCTYPE html>
<html lang="${title?default("kr")}">
<head>
    <meta charset="UTF-8">
    <title>${title?default("")}</title>
</head>
<body>
<h1>${title?default("")}</h1>
<h1>${test?default("test is empty")}</h1>
<h1>${fromDate?string("MM/dd/yyyy HH:mm:ss")}</h1>
<h1>${zdtUs?string("MM/dd/yyyy HH:mm:ss")}</h1>

<ol>
    <#if userNames?has_content>
        <#list userNames as userName>
            <li>${userName}</li>
        </#list>
    </#if>
</ol>

<table>
    <thead>
    <tr>
        <td>name</td>
        <td>age</td>
    </tr>
    </thead>
    <tbody>
    <#if rows?has_content>
        <#list rows as row>
            <tr>
                <td>${row.name}</td>
                <td>${row.age}</td>
            </tr>
        </#list>
    <#else>
        <tr>
            <td>empty</td>
        </tr>
    </#if>
    </tbody>
</table>

</body>
</html>