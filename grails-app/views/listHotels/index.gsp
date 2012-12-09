<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Hotel auswählen</title>
    <meta name="layout" content="main"/>
</head>

<body>

<div class="content">
    <h1>Hotels bewerten</h1>

    <div style="margin: 2em">
        <p>Wählen Sie ein Hotel aus, das Sie bewerten möchten:</p>
        <ul>
            <g:each in="${hotels}" var="hotel">
                <li><g:link controller="rating" action="show" params="[uri: hotel.uri]">${hotel.name}</g:link></li>
            </g:each>
        </ul>
    </div>
</div>

</body>
</html>