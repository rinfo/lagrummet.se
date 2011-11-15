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
			<g:each in="['Lagar', 'Rattsfall', 'Utredningar']">
				<g:if test="${cat == it}"><div class="inputGroup"><input type="radio" checked="checked" value="${it}" name="kategori" id="cat${it}" /><label for="cat${it}"><p><strong><g:message code="category.${it}"/></strong></p><p><g:message code="category.description.${it}"/></p></label></div></g:if>
				<g:else><div class="inputGroup"><input type="radio" value="${it}" name="kategori" id="cat${it}" /><label for="cat${it}"><p><strong><g:message code="category.${it}"/></strong></p><p><g:message code="category.description.${it}"/></p></label></div></g:else>
			</g:each>
		</fieldset>
		
		<g:if test="${cat == 'Lagar'}"><g:set var="hidden" value="" /></g:if><g:else><g:set var="hidden" value="hidden" /></g:else>
		<g:form mapping="extendedSearch" fragment="searchResults" method="GET" class="extendedSearch ${hidden}" name="Lagar">
			<input type="hidden" name="kategori" value="Lagar" />
			
			<label for="typ"><g:message code="extendedSearch.chooseType.label" default="Välj typ" /></label>
			<g:select name="typ" from="${['Alla typer', 'Lagar', 'Myndigheters föreskrifter', 'Förordningar']}"
									keys="${['', 'Lagar', 'Myndigheters föreskrifter', 'Förordningar'] }" 
									value="${params?.typ}" />
			
			<label for="titel"><g:message code="extendedSearch.titel.label" default="Titel" /></label>
			<g:textField name="titel" size="26" value="${params?.titel}" />
			
			<label for="beteckning"><g:message code="extendedSearch.sfs.label" default="SFS" /></label>
			<g:textField name="beteckning" size="12" value="${params?.beteckning}" />
			
			<label for="skapare"><g:message code="extendedSearch.departement.label" default="Departement" /></label>
			<g:textField name="skapare" size="26" value="${params?.skapare}" />
			
			<label for="query"><g:message code="extendedSearch.fritext.label" default="Fritext" /></label>
			<g:textField name="query" size="26" value="${params?.query}" />
			
			<fieldset>
				<div class="legend"><g:message code="extendedSearch.chooseDate.label" default="Välj datum" /></div>
				
				
				<div class="inputGroup"><g:radio checked="${params?.datum == null || params?.datum =='ikraft'}" value="ikraft" name="datum" id="ikraftDatum" /><label for="cat${it}"><p><strong><g:message code="extendedSearch.ikraftDatum.label" default="Ikrafttädandedatum"/></strong></p><p><g:message code="extendedSearch.description.ikraftDatum.label" default="Datum då lagen trädde i kraft"/></p></label></div>
				<div class="inputGroup"><g:radio checked="${params?.datum == 'utfardande'}" value="utfardande" name="datum" id="utfardandeDatum" /><label for="utfardandeDatum"><p><strong><g:message code="extendedSearch.utfardandeDatum.label" default="Utfärdandedatum/Beslutandedatum"/></strong></p><p><g:message code="extendedSearch.description.utfardandeDatum.label" default="Datum då lagen utfärdades/beslutades"/></p></label></div>
				
				<div class="inputGroup break ">
					<label for="fromDate" class="date"><g:message code="extendedSearch.datumMin.label" default="Från:"/></label>
					<input type="date" name="fromDate" size="10" value="${params?.fromDate}"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="fromDate">
						<g:renderErrors bean="${extendedSearchCommand}" field="fromDate" />
					</g:hasErrors>
				</div>
				
				<div class="inputGroup">
					<label for="toDate" class="date"><g:message code="extendedSearch.datumMax.label" default="Till:"/></label>
					<input type="date" name="toDate" size="10" value="${params?.toDate}"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="toDate">
						<g:renderErrors bean="${extendedSearchCommand}" field="toDate" />
					</g:hasErrors>
				</div>
			</fieldset>
			
			
			<div class="buttons"><g:submitButton name="extendedSearchSubmit" value="Sök"/></div>
		</g:form>
		
		<g:if test="${cat == 'Rattsfall'}"><g:set var="hidden" value="" /></g:if><g:else><g:set var="hidden" value="hidden" /></g:else>
		<g:form mapping="extendedSearch" fragment="searchResults" method="GET" class="extendedSearch ${hidden}" name="Rattsfall">
			<input type="hidden" name="kategori" value="Rattsfall" />
			
			<label for="typ"><g:message code="extendedSearch.Rattsfall.typ.label" default="Domstol/myndighet" /></label>
			<g:select name="" from="${['Allmänna domstolar'] }" />
			
			<label for="referatrubrik"><g:message code="extendedSearch.referatrubrik.label" default="Rubrik" /></label>
			<g:textField name="referatrubrik" size="26" value="${params?.referatrubrik}" />
			
			<label for="beteckning"><g:message code="extendedSearch.beteckning.label" default="Beteckning" /></label>
			<g:textField name="beteckning" size="26" value="${params?.referatrubrik}" />
			
			<label for="sokord"><g:message code="extendedSearch.sokord.label" default="Sökord" /></label>
			<g:textField name="sokord" size="26" value="${params?.sokord}" />
			
			<label for="malnummer"><g:message code="extendedSearch.malnummer.label" default="Målnummer" /></label>
			<g:textField name="malnummer" size="12" value="${params?.malnummer}" />
			
			<label for="query"><g:message code="extendedSearch.fritext.label" default="Fritext" /></label>
			<g:textField name="query" size="26" value="${params?.query}" />
			
			<fieldset>
				<legend>Lagrum</legend>
				
				
				<div class="inputGroup">
					<label for="sfs"><g:message code="extendedSearch.sfs.label" default="SFS" /></label>
					<g:textField name="sfs" size="26" value="${params?.sfs}" />
				</div>
				
				<div class="inputGroup">
					<label for="paragrafnummer"><g:message code="extendedSearch.paragrafnummer.label" default="Paragrafnummer" /></label>
					<g:textField name="paragrafnummer" size="26" value="${params?.paragrafnummer}" />
				</div>
				
				<div class="inputGroup">
					<label for="kapitelnummer"><g:message code="extendedSearch.kapitelnummer.label" default="Kapitelnummer" /></label>
					<g:textField name="kapitelnummer" size="26" value="${params?.kapitelnummer}" />
				</div>
			</fieldset>
			
			<fieldset>
				<div class="legend"><g:message code="extendedSearch.avgorandeDatum.label" default="Avgörandedatum" /></div>
				<input type="hidden" name="datum" id="avgorandeDatum" value="avgorande" />
				<div class="inputGroup">
					<label for="fromDate" class="date"><g:message code="extendedSearch.datumMin.label" default="Från:"/></label>
					<input type="date" name="fromDate" size="10" value="${params?.fromDate}"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="fromDate">
						<g:renderErrors bean="${extendedSearchCommand}" field="fromDate" />
					</g:hasErrors>
				</div>
				
				<div class="inputGroup">
					<label for="toDate" class="date"><g:message code="extendedSearch.datumMax.label" default="Till:"/></label>
					<input type="date" name="toDate" size="10" value="${params?.toDate}"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="toDate">
						<g:renderErrors bean="${extendedSearchCommand}" field="toDate" />
					</g:hasErrors>
				</div>
			</fieldset>
			
			<div class="buttons"><g:submitButton name="extendedSearchSubmit" value="Sök"/></div>
		</g:form>
		
		<g:if test="${cat == 'Utredningar'}"><g:set var="hidden" value="" /></g:if><g:else><g:set var="hidden" value="hidden" /></g:else>
		<g:form mapping="extendedSearch" fragment="searchResults" method="GET" class="extendedSearch ${hidden}" name="Utredningar">
			<input type="hidden" name="kategori" value="Utredningar" />
			
			<label for="typ"><g:message code="extendedSearch.Utredningar.typ.label" default="Välj typ av förarbete" /></label>
			<g:select name="typ" from="${['Alla förarbeten', 'Propositioner', 'Utredningar'] }" 
									keys="${['Alla förarbeten', 'Propositioner', 'Utredningar'] }"
									value="${params?.typ}"/>
			
			<label for="beteckning"><g:message code="extendedSearch.beteckning.label" default="Beteckning" /></label>
			<g:textField name="beteckning" size="26" value="${params?.beteckning}" />
			
			<label for="titel"><g:message code="extendedSearch.titel.label" default="Titel" /></label>
			<g:textField name="titel" size="26" value="${params?.titel}" />
			
			<label for="query"><g:message code="extendedSearch.fritext.label" default="Fritext" /></label>
			<g:textField name="query" size="26" value="${params?.query}" />
			
			<fieldset>
				<div class="legend"><g:message code="extendedSearch.utgivandeDatum.label" default="Utgivandedatum" /></div>
				<input type="hidden" name="datum" id="utgivandeDatum" value="utgivande" />
				<div class="inputGroup" class="date">
					<label for="fromDate" class="date"><g:message code="extendedSearch.datumMin.label" default="Från:"/></label>
					<input type="date" name="fromDate" size="10" value="${params?.fromDate}"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="fromDate">
						<g:renderErrors bean="${extendedSearchCommand}" field="fromDate" />
					</g:hasErrors>
				</div>
				
				<div class="inputGroup">
					<label for="toDate" class="date"><g:message code="extendedSearch.datumMax.label" default="Till:"/></label>
					<input type="date" name="toDate" size="10" value="${params?.toDate}"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="toDate">
						<g:renderErrors bean="${extendedSearchCommand}" field="toDate" />
					</g:hasErrors>
				</div>
			</fieldset>
			
			<div class="buttons"><g:submitButton name="extendedSearchSubmit" value="Sök"/></div>
		</g:form>
	</article>

    <article id="searchResults" class="searchResults">
    	<p class="printLabel"><a href="javascript:if(window.print)window.print()">Print</a></p>
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