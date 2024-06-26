

<%@ page import="se.lagrummet.Page" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
        	<g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:each in="${flash.messages}">
            <div class="message">${it}</div>
            </g:each>
            <g:hasErrors bean="${pageInstance}">
            <div class="errors">
                <g:renderErrors bean="${pageInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" mapping="pageAdmin" params="[action: 'edit', id: pageInstance.id]" name="pageEditForm" useToken="true">
                <g:hiddenField name="version" value="${pageInstance?.version}" />
                <g:render template="pageEditForm" />
            </g:form>    
            
            <div class="media">
            	<h2>${message(code: 'default.page.media.label', default: 'Ladda upp media')}</h2>
	            <g:form action="save" controller="media" method="post" enctype="multipart/form-data" useToken="true">
	            	<g:hiddenField name="parentId" value="${pageInstance?.id}" />
					<div class="input ${hasErrors(bean: mediaInstance, field: 'imageFile', 'errors')}">
						<label for="mediaFile"><g:message code="user.file.label" default="Fil" /></label>
						<input type="file" name="mediaFile" />
					</div>
					
					<div class="input ${hasErrors(bean: mediaInstance, field: 'title', 'errors')}">
						<label for="title"><g:message code="media.title.label" default="Titel" /></label>
						<g:textField name="title" value="${mediaInstance?.title}" />
					</div>
						
				    <div class="buttons">
				    	<g:actionSubmit name="save" action="save" class="save" value="${message(code: 'default.button.upload.label', default: 'Ladda upp')}" />
				    	<p><em>Kom ihåg att spara sidan innan en bild laddas upp</em>
				    </div>
				</g:form>
				
				<h3>${message(code: 'default.page.mediaList.label', default: 'Media tillhörande denna sida')}</h3>
				<ul>
					<g:each in="${pageInstance.media}" status="i" var="mediaInstance">
                        <li><g:link controller="media" action="edit" id="${mediaInstance.id}">${fieldValue(bean: mediaInstance, field: "title")}</g:link> (<a href="${resource() + "/" + mediaInstance?.filename}">${mediaInstance?.filename}</a>)</li>
                    </g:each>
				</ul>
            </div>
            
            <div class="revisions">
            	<h3>${message(code: 'default.page.revisions.label', default: 'Previous Revisions')}</h3>
	            <g:if test="${revisions}">
	            	<ul>
		            	<g:each in="${revisions}" var="r">
		            		<li>
		            			<g:link action="edit" id="${r.id}">
		            				<g:if test="${r.status == 'published'}"><strong><g:formatDate format="yyyy-MM-dd HH:mm" date="${r.dateCreated}" /> av ${r.author?.username}</strong></g:if>
		            				<g:else><i>Utkast <g:formatDate format="yyyy-MM-dd HH:mm" date="${r.dateCreated}" /> av ${r.author?.username}</i></g:else>
		            			</g:link>
		            		</li>
		            	</g:each>
		            </ul>
	            </g:if>
            <g:elseif test="${pageInstance.masterRevision}">
            	${message(code: 'default.page.masterRevision.label', default: 'This is a revision of ')}<g:link action="edit" id="${pageInstance.masterRevision.id}">${pageInstance.masterRevision.title}</g:link>
            </g:elseif>
            </div>
        </div>
    </body>
</html>
