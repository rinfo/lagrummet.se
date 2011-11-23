<html>
<head>
	<g:if test="${!params.query}"><title>Utökad sökning</title></g:if>
	<g:else><title>Utökade sökresultat för ${params.query.encodeAsHTML()}</title></g:else>
	<meta name="layout" content="extendedSearchMain"/>
</head>
<body>
<div id="content">
	<article id="extendedSearch">
		<h1><g:message code="extendedSearch.label" default="Utökad sökning" /></h1>
		<fieldset class="category" id="extSearchCats">
			<div class="legend"><g:message code="extendedSearch.chooseCategory.label" default="Välj en kategori" /></div>
			<g:each in="['Forfattningar', 'Rattsfall', 'Forarbeten']">
				<g:if test="${cat == it}"><div class="inputGroup"><input type="radio" checked="checked" value="${it}" name="kategori" id="cat${it}" /><label for="cat${it}"><p><strong><g:message code="category.${it}"/></strong></p><p><g:message code="category.description.${it}"/></p></label></div></g:if>
				<g:else><div class="inputGroup"><input type="radio" value="${it}" name="kategori" id="cat${it}" /><label for="cat${it}"><p><strong><g:message code="category.${it}"/></strong></p><p><g:message code="category.description.${it}"/></p></label></div></g:else>
			</g:each>
		</fieldset>

		<g:if test="${cat == 'Forfattningar'}">
			<g:set var="hidden" value="" />
			<g:set var="forfattningarParams" value="${params}" />
		</g:if>
		<g:else><g:set var="hidden" value="hidden" /></g:else>
		<g:form mapping="extendedSearch" fragment="searchResults" method="GET" class="extendedSearch ${hidden}" name="Forfattningar">
			<input type="hidden" name="kategori" value="Forfattningar" />
			
			<label for="typ"><g:message code="extendedSearch.chooseType.label" default="Välj typ" /></label>
			<g:select name="typ" from="${['Alla författningar', 'Lagar', 'Förordningar', 'Myndigheters föreskrifter']}"
									value="${forfattningarParams?.typ}" />
			
			<label for="titel"><g:message code="extendedSearch.titel.label" default="Titel" /></label>
			<g:textField name="titel" size="26" value="${forfattningarParams?.titel}" />
			
			<label for="beteckning"><g:message code="extendedSearch.sfs.label" default="SFS/Beteckning" /></label>
			<g:textField name="beteckning" size="12" value="${forfattningarParams?.beteckning}" />
			
			<label for="beslutandeMyndighet"><g:message code="extendedSearch.beslutandeMyndighet.label" default="Beslutande myndighet" /></label>
			<g:textField name="utgivare" id="beslutandeMyndighet" size="26" value="${forfattningarParams?.utgivare}" />
			
			<label for="departement"><g:message code="extendedSearch.departement.label" default="Departement" /></label>
			<g:textField name="skapare" id="departement" size="26" value="${forfattningarParams?.skapare}" />			
			
			<label for="query"><g:message code="extendedSearch.fritext.label" default="Fritext" /></label>
			<g:textField name="fritext" size="26" value="${forfattningarParams?.fritext}" />
			
			<label for="forfattningssamling"><g:message code="extendedSearch.forfattnintssamling.label" default="Författningssamling" /></label>
			<g:textField name="forfattningssamling" size="26" value="${forfattningarParams?.forfattningssamling}" />
			
			<fieldset>
				<div class="legend"><g:message code="extendedSearch.chooseDate.label" default="Välj datum" /></div>
				
				
				<div class="inputGroup"><g:radio checked="${forfattningarParams?.datum == null || forfattningarParams?.datum != 'utfardande'}" value="ikraft" name="datum" id="ikraftDatum" /><label for="ikraftDatum"><p><strong><g:message code="extendedSearch.ikraftDatum.label" default="Ikrafttädandedatum"/></strong></p><p><g:message code="extendedSearch.description.ikraftDatum.label" default="Datum då lagen trädde i kraft"/></p></label></div>
				<div class="inputGroup"><g:radio checked="${forfattningarParams?.datum == 'utfardande'}" value="utfardande" name="datum" id="utfardandeDatum" /><label for="utfardandeDatum"><p><strong><g:message code="extendedSearch.utfardandeDatum.label" default="Utfärdandedatum/Beslutandedatum"/></strong></p><p><g:message code="extendedSearch.description.utfardandeDatum.label" default="Datum då lagen utfärdades/beslutades"/></p></label></div>
				
				<div class="inputGroup break ">
					<label for="fromDate" class="date"><g:message code="extendedSearch.datumMin.label" default="Från:"/></label>
					<input type="text" name="fromDate" size="10" value="${forfattningarParams?.fromDate}" placeholder="åååå-mm-dd" class="dateinput"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="fromDate">
						<br/><g:renderErrors bean="${extendedSearchCommand}" field="fromDate" />
					</g:hasErrors>
				</div>
				
				<div class="inputGroup">
					<label for="toDate" class="date"><g:message code="extendedSearch.datumMax.label" default="Till:"/></label>
					<input type="text" name="toDate" size="10" value="${forfattningarParams?.toDate}" placeholder="åååå-mm-dd" class="dateinput"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="toDate">
						<br/><g:renderErrors bean="${extendedSearchCommand}" field="toDate" />
					</g:hasErrors>
				</div>
			</fieldset>
			
			
			<div class="buttons"><g:submitButton name="extendedSearchSubmit" value="Sök"/></div>
		</g:form>
		
		<g:if test="${cat == 'Rattsfall'}">
			<g:set var="hidden" value="" />
			<g:set var="rattsfallParams" value="${params}" />
		</g:if><g:else><g:set var="hidden" value="hidden" /></g:else>
		<g:form mapping="extendedSearch" fragment="searchResults" method="GET" class="extendedSearch ${hidden}" name="Rattsfall">
			<input type="hidden" name="kategori" value="Rattsfall" />
			
			<label for="utgivare"><g:message code="extendedSearch.domstol.label" default="Domstol"/></label>
			<g:select name="utgivare" from="['Alla domstolar','Högsta domstolen', 'Regeringsrätten']"
										keys="['', 'hoegsta_domstolen', 'regeringsraetten' ]" value="${rattsfallParams?.utgivare}" />
			
			<label for="referatrubrik"><g:message code="extendedSearch.referatrubrik.label" default="Rubrik" /></label>
			<g:textField name="referatrubrik" size="26" value="${rattsfallParams?.referatrubrik}" />
			
			<label for="beteckning"><g:message code="extendedSearch.beteckning.label" default="Beteckning" /></label>
			<g:textField name="beteckning" size="26" value="${rattsfallParams?.beteckning}" />
			
			<label for="malnummer"><g:message code="extendedSearch.malnummer.label" default="Målnummer" /></label>
			<g:textField name="malnummer" size="12" value="${rattsfallParams?.malnummer}" />
			
			<label for="query"><g:message code="extendedSearch.fritext.label" default="Fritext" /></label>
			<g:textField name="fritext" size="26" value="${rattsfallParams?.fritext}" />
			
			<label for="sokord"><g:message code="extendedSearch.sokord.label" default="Sökord" /></label>
			<g:textField name="sokord" size="26" value="${rattsfallParams?.sokord}" />
			
			<fieldset>
				<div class="legend"><g:message code="extendedSearch.avgorandeDatum.label" default="Avgörandedatum" /></div>
				<input type="hidden" name="datum" id="avgorandeDatum" value="avgorande" />
				<div class="inputGroup">
					<label for="fromDate" class="date"><g:message code="extendedSearch.datumMin.label" default="Från:"/></label>
					<input type="text" name="fromDate" size="10" value="${rattsfallParams?.fromDate}"  placeholder="åååå-mm-dd" class="dateinput"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="fromDate">
						<br/><g:renderErrors bean="${extendedSearchCommand}" field="fromDate" />
					</g:hasErrors>
				</div>
				
				<div class="inputGroup">
					<label for="toDate" class="date"><g:message code="extendedSearch.datumMax.label" default="Till:"/></label>
					<input type="text" name="toDate" size="10" value="${rattsfallParams?.toDate}" placeholder="åååå-mm-dd" class="dateinput"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="toDate">
						<br/><g:renderErrors bean="${extendedSearchCommand}" field="toDate" />
					</g:hasErrors>
				</div>
			</fieldset>
			
			<div class="buttons"><g:submitButton name="extendedSearchSubmit" value="Sök"/></div>
		</g:form>
		
		<g:if test="${cat == 'Forarbeten'}">
			<g:set var="hidden" value="" />
			<g:set var="forarbeteParams" value="${params}" />
		</g:if><g:else><g:set var="hidden" value="hidden" /></g:else>
		<g:form mapping="extendedSearch" fragment="searchResults" method="GET" class="extendedSearch ${hidden}" name="Forarbeten">
			<input type="hidden" name="kategori" value="Forarbeten" />
			
			<label for="typ"><g:message code="extendedSearch.Forarbeten.typ.label" default="Välj typ av förarbete" /></label>
			<g:select name="typ" from="${['Alla förarbeten', 'Propositioner', 'Utredningar'] }" 
									keys="${['Alla förarbeten', 'Propositioner', 'Utredningar'] }"
									value="${forarbeteParams?.typ}"/>
			
			<label for="beteckning"><g:message code="extendedSearch.beteckning.label" default="Beteckning" /></label>
			<g:textField name="beteckning" size="26" value="${forarbeteParams?.beteckning}" />
			
			<label for="titel"><g:message code="extendedSearch.titel.label" default="Titel" /></label>
			<g:textField name="titel" size="26" value="${forarbeteParams?.titel}" />
			
			<label for="query"><g:message code="extendedSearch.fritext.label" default="Fritext" /></label>
			<g:textField name="fritext" size="26" value="${forarbeteParams?.fritext}" />
			
			<fieldset>
				<div class="legend"><g:message code="extendedSearch.utgivandeDatum.label" default="Utgivandedatum" /></div>
				<input type="hidden" name="datum" id="utgivandeDatum" value="utgivande" />
				<div class="inputGroup" class="date">
					<label for="fromDate" class="date"><g:message code="extendedSearch.datumMin.label" default="Från:"/></label>
					<input type="text" name="fromDate" size="10" value="${forarbeteParams?.fromDate}" placeholder="åååå-mm-dd" class="dateinput"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="fromDate">
						<br/><g:renderErrors bean="${extendedSearchCommand}" field="fromDate" />
					</g:hasErrors>
				</div>
				
				<div class="inputGroup">
					<label for="toDate" class="date"><g:message code="extendedSearch.datumMax.label" default="Till:"/></label>
					<input type="text" name="toDate" size="10" value="${forarbeteParams?.toDate}" placeholder="åååå-mm-dd" class="dateinput"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="toDate">
						<br/><g:renderErrors bean="${extendedSearchCommand}" field="toDate" />
					</g:hasErrors>
				</div>
			</fieldset>
			
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
			<p class="printLabel"><a href="javascript:if(window.print)window.print()">Skriv ut</a></p>
	    	<p class="showAllLabel"><a href="${createLink(mapping:'extendedSearch', fragment: "searchResults", params:params + [max: searchResult?.totalResults, offset: 0])}">Visa alla ${searchResult?.totalResults} träffar</a></p>
	    	
			<h2 id="sokresultat">Sökresultat</h2>
		
			<p>Visar ${1+(offset ?: 0)  }-${(offset ?: 0)+searchResult.itemsList.size()} av ${searchResult.totalResults} träffar i <strong><g:message code="category.${cat}"/></strong></p>
			
			<table>
				<tr>
					<th><a href="#">Titel</a></th>
					<g:if test="${cat == 'Lagar' }"><th><a href="#">SFS-nummer</a></th></g:if>
					<g:elseif test="${cat != 'Ovrigt' }"><th><a href="#">Identifierare</a></th></g:elseif>
				</tr>
				<g:each in="${searchResult.itemsList}" var="item">
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
			<g:paginate total="${searchResult.totalResults}" max="20" params="${params}"/>
		</g:if>
	</article>
</div>
</body>
</html>