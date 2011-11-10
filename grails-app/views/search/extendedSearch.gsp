<html>
<head>
	<title>Utökade sökresultat för ${query.encodeAsHTML()}</title>
	<meta name="layout" content="extendedSearchMain"/>
</head>
<body>
<div id="content">
	<article id="extendedSearch">
		<h1><g:message code="extendedSearch.label" default="Utökad sökning" /></h1>
		<g:form mapping="advancedSearch" method="GET" name="extendedSearch">
			<fieldset class="category">
				<div class="legend"><g:message code="extendedSearch.chooseCategory.label" default="Välj en kategori" /></div>
				<g:each in="['Lagar', 'Rattsfall', 'Utredningar']">
					<g:if test="${params?.kategori == it}"><div class="rGroup"><input type="radio" checked="checked" value="${it}" name="kategori" id="cat${it}" /><label for="cat${it}"><p><strong><g:message code="category.${it}"/></strong></p><p><g:message code="category.description.${it}"/></p></label></div></g:if>
					<g:else><div class="rGroup"><input type="radio" value="${it}" name="kategori" id="cat${it}" /><label for="cat${it}"><p><strong><g:message code="category.${it}"/></strong></p><p><g:message code="category.description.${it}"/></p></label></div></g:else>
				</g:each>
			</fieldset>
			
			
			<label for="typ"><g:message code="extendedSearch.chooseType.label" default="Välj typ" /></label>
			<select id="typ" name="typ" value="${params?.typ}"></select>
			
			<label for="titel"><g:message code="extendedSearch.titel.label" default="Titel" /></label>
			<g:textField name="titel" size="26" value="${params?.titel}" />
			
			<label for="beteckning"><g:message code="extendedSearch.sfs.label" default="SFS" /></label>
			<g:textField name="beteckning" size="12" value="${params?.beteckning}" />
			
			<label for="skapare"><g:message code="extendedSearch.skapare.label" default="Skapare" /></label>
			<g:textField name="skapare" size="26" value="${params?.skapare}" />
			
			<label for="query"><g:message code="extendedSearch.fritext.label" default="Fritext" /></label>
			<g:textField name="query" size="26" value="${params?.query}" />
			
			<div class="buttons"><g:submitButton name="extendedSearchSubmit" value="Sök"/></div>
		</g:form>
	</article>

    <article id="searchResults" class="searchResults">
    	<g:if test="${searchResult?.errorMessages?.size > 0}">
    		<div class="message">
    			<ul>
    				<g:each in="${searchResult.errorMessages}" var="message">
    					<li>${message}</li>
    				</g:each>
    			</ul>
    		</div>
    	</g:if>

		<g:if test="${searchResult?.totalResults}">
			<h2 id="sokresultat">Sökresultat</h2>
		
			<p>Visar ${1+(offset ?: 0)  }-${(offset ?: 0)+searchResult.items[(cat)].size()} av ${searchResult.totalResults} träffar för <span class="query">"${params.query.encodeAsHTML()}"</span> i <strong><g:message code="category.${cat}"/></strong></p>
			
			<table>
				<tr>
					<th><a href="#">Titel</a></th>
					<g:if test="${cat == 'Lagar' }"><th><a href="#">SFS-nummer</a></th></g:if>
					<g:elseif test="${cat != 'Ovrigt' }"><th><a href="#">Identifierare</a></th></g:elseif>
				</tr>
				<g:each in="${searchResult.items[(cat)]}" var="item">
					<tr>
						<td>
							<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
							<g:if test="${item.matches}">
								<p>${item.matches} ...</p>
							</g:if>
						</td>
						<g:if test="${cat != 'Ovrigt' }"><td>${item.identifier}</td></g:if>
					</tr>
				</g:each>
			</table>
			<g:paginate total="${searchResult.totalResults}" max="20" params="${[query: query, cat: cat]}"/>
		</g:if>
	</article>
</div>
</body>
</html>