<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${name}</title>
    <meta  name="layout" content="main">
</head>

<body>

<div class="content">
    <g:link controller="listHotels">Zurück zur Übersicht</g:link>
    <h1>${name.encodeAsHTML()} bewerten</h1>
    <ul style="margin: 10px 0 10px 50px">
        <li>Ort: <g:link url="${city.uri}">${city.name.encodeAsHTML()}</g:link></li>
        <li>Anzahl Zimmer: ${numberOfRooms}</li>
    </ul>
    <h2>Ihre Bewertung:</h2>
    <table>
        <g:form action="save">
            <input type="hidden" name="uri" value="${uri}"/>
        <tr>
            <td>Bewertung:</td><td><g:select name="rating" from="${1..10}" /></td>
        </tr>
        <tr>
            <td>Ihr Kommentar:</td><td><g:textArea name="comment" maxlength="140"
                                                  style='width: 300px; height: 80px;' /></td>
        </tr>
        <tr><td></td><td><g:submitButton name="Bewertung abgeben" /></td></tr>

    </g:form>
    </table>

    <h2>Bisher ${ratings.size()} Bewertungen:</h2>

    <g:each in="${ratings}" var="rating">
        <div style="margin: 10px; background-color: #EEE">
            <strong>${rating.rating}/10</strong>: ${rating.comment?.encodeAsHTML() ?: '&lt;Ohne Kommentar&gt;'}
        </div>
    </g:each>
</div>

</body>
</html>