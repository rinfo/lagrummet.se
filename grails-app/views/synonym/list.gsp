
<%@ page import="se.lagrummet.Synonym" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'synonym.label', default: 'Synonym')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="synonym.list.label" args="[entityName]"  /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form name="editSynonyms" action="updateList" controller="synonym">
            <div class="buttons">
            <input type="button" value="lägg till synonym" id="addSynonym" class="add" />
            </div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="synonym" title="${message(code: 'synonym.synonym.label', default: 'Populärnamn')}" />
                        
                            <g:sortableColumn property="baseTerm" title="${message(code: 'synonym.baseTerm.label', default: 'Formellt namn')}" />
                        	
                        	<th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${synonymInstanceList}" status="i" var="synonymInstance">
                        <tr>
                        
                            <td><g:textField name="synonyms[${i}].synonym" value="${synonymInstance?.synonym}" size="50" disabled="disabled" /></td>
                        
                            <td><g:textField name="synonyms[${i}].baseTerm" value="${synonymInstance?.baseTerm}" size="50" disabled="disabled" /></td>
                        
                        	<td>
                        		<div class="buttons"><input type="button" class="editSynonym update" value="&nbsp;" /> 
                        			<a href="${createLink( controller:"synonym", action:"delete", params:[id: synonymInstance?.id])}"><input type="button" class="deleteSynonym delete" value="&nbsp;" /></a></div>
                        		<g:hiddenField name="synonyms[${i}].id" value="${synonymInstance?.id}" disabled="disabled" />
                        	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <script type="text/javascript">
			    var next_index = ${synonymInstanceList.size()}
			</script>
			<div class="buttons">
				<span class="button">
	            	<g:submitButton name="submitSynonym" value="Spara ändringar" class="save"/>
	            </span>
            </div>
            </g:form>
        </div>
    </body>
</html>
