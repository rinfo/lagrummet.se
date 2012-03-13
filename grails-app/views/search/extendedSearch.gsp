<html>
<head>
	<g:if test="${!params.query}"><title>Utökad sökning</title></g:if>
	<g:else><title>Utökade sökresultat för ${params.query.encodeAsHTML()}</title></g:else>
	<meta name="layout" content="extendedSearchMain"/>
</head>
<body>
	<article id="extendedSearch">
		<h1><g:message code="extendedSearch.label" default="Utökad sökning" /></h1>
		<fieldset class="category" id="extSearchCats">
			<div class="legend"><g:message code="extendedSearch.chooseCategory.label" default="Välj en kategori" /></div>
			<g:each in="['Forfattningar', 'Rattsfall', 'Forarbeten']">
				<g:if test="${cat == it}"><div class="inputGroup"><input type="radio" checked="checked" value="${it}" name="kategori" id="cat${it}" /><label for="cat${it}"><p><strong><g:message code="category.extendedSearch.${it}"/></strong></p><p><g:message code="category.extendedSearch.description.${it}"/></p></label></div></g:if>
				<g:else><div class="inputGroup"><input type="radio" value="${it}" name="kategori" id="cat${it}" /><label for="cat${it}"><p><strong><g:message code="category.extendedSearch.${it}"/></strong></p><p><g:message code="category.extendedSearch.description.${it}"/></p></label></div></g:else>
			</g:each>
		</fieldset>

		<g:if test="${cat == 'Forfattningar'}">
			<g:set var="hidden" value="" />
			<g:set var="forfattningarParams" value="${params}" />
		</g:if>
		<g:else><g:set var="hidden" value="hidden" /></g:else>
		<g:form mapping="extendedSearch" fragment="searchResults" method="GET" class="extendedSearch ${hidden}" name="Forfattningar">
			<input type="hidden" name="kategori" value="Forfattningar" />

			<label for="typ"><g:message code="extendedSearch.chooseType.label" default="Välj typ av författning" /></label>
			<g:select name="typ" from="${['Alla författningar', 'Lagar', 'Förordningar', 'Myndigheters föreskrifter']}"
									value="${forfattningarParams?.typ}" />

			<fieldset id="lagstatus">
				<div class="inputGroup">
					<g:checkBox name="gallande" checked="${forfattningarParams?.gallande == 'on' || !params.containsKey('_gallande') }" />
					<label for="gallande"><p><strong><g:message code="extendedSearch.gallande.label" default="Gällande författningar" /></strong></p><p><g:message code="extendedSearch.description.gallande.label" default="Sök på gällande författningar" /></p></label>
				</div>
				
				<div class="inputGroup">
					<g:checkBox name="upphavda" checked="${forfattningarParams?.upphavda == 'on' }"/>
					<label for="upphavda"><p><strong><g:message code="extendedSearch.upphavda.label" default="Upphävda författningar" /></strong></p><p><g:message code="extendedSearch.description.upphavda.label" default="Sök på upphävda författningar"/></p></label>
				</div>
				
				<div class="inputGroup">
					<g:checkBox name="kommande" checked="${forfattningarParams?.kommande == 'on' }" />
					<label for="kommande"><p><strong><g:message code="extendedSearch.kommande.label" default="Kommande författningar" /></strong></p><p><g:message code="extendedSearch.description.kommande.label" default="Sök på kommande författningar"/></p></label>
				</div>
			</fieldset>
					
			<label for="titel"><g:message code="extendedSearch.titel.label" default="Titel" /></label>
			<g:textField name="titel" size="26" value="${forfattningarParams?.titel}" />
			
			<label for="beteckning"><g:message code="extendedSearch.sfs.label" default="Beteckning" /></label>
			<g:textField name="beteckning" size="12" value="${forfattningarParams?.beteckning}" />
			
			<label for="beslutandeMyndighet"><g:message code="extendedSearch.beslutandeMyndighet.label" default="Beslutande myndighet" /></label>
			<g:textField name="utgivare" id="beslutandeMyndighet" size="26" value="${forfattningarParams?.utgivare}" />
			
			<label for="departement"><g:message code="extendedSearch.departement.label" default="Ansvarigt departement (SFS)" /></label>
			<g:textField name="skapare" id="departement" size="26" value="${forfattningarParams?.skapare}" />			
			
			<label for="query"><g:message code="extendedSearch.fritext.label" default="Fritext" /></label>
			<g:textField name="fritext" size="26" value="${forfattningarParams?.fritext}" />
			
			<fieldset id="dateSelect">
				<div class="legend"><g:message code="extendedSearch.chooseDate.label" default="Välj datum" /></div>
				
				<div class="dateTopCol">
					<div class="dateTab ikraft">
						<g:radio checked="${forfattningarParams?.datum == null || forfattningarParams?.datum != 'utfardande'}" value="ikraft" name="datum" id="ikraftDatum" /><label for="ikraftDatum"><p><strong><g:message code="extendedSearch.ikraftDatum.label" default="Ikrafttädandedatum"/></strong></p><p><g:message code="extendedSearch.description.ikraftDatum.label" default="Datum då lagen trädde i kraft"/><br /></p></label>
					</div>
					<div class="dateTab utfardandeDatum">
						<g:radio checked="${forfattningarParams?.datum == 'utfardande'}" value="utfardande" name="datum" id="utfardandeDatum" /><label for="utfardandeDatum"><p><strong><g:message code="extendedSearch.utfardandeDatum.label" default="Utfärdandedatum/Beslutandedatum"/></strong></p><p><g:message code="extendedSearch.description.utfardandeDatum.label" default="Datum då lagen utfärdades / föreskriften beslutades"/></p></label>
					</div>
				</div>
				
				<div class="dateBottomCol">
					<div>
						<label for="fromDate" class="date"><g:message code="extendedSearch.datumMin.label" default="Från:"/></label>
						<input type="text" name="fromDate" size="10" value="${forfattningarParams?.fromDate}" placeholder="åååå-mm-dd" class="dateinput"/>
						<g:hasErrors bean="${extendedSearchCommand}" field="fromDate">
							<div class="error">
								<g:renderErrors bean="${extendedSearchCommand}" field="fromDate" />
							</div>
						</g:hasErrors>
					</div>
				
					<div>
						<label for="toDate" class="date"><g:message code="extendedSearch.datumMax.label" default="Till:"/></label>
						<input type="text" name="toDate" size="10" value="${forfattningarParams?.toDate}" placeholder="åååå-mm-dd" class="dateinput"/>
						<g:hasErrors bean="${extendedSearchCommand}" field="toDate">
							<div class="error">
								<g:renderErrors bean="${extendedSearchCommand}" field="toDate" />
							</div>
						</g:hasErrors>
					</div>					
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
			
			<label for="utgivare"><g:message code="extendedSearch.domstol.label" default="Välj domstol"/></label>
			<select name="utgivare">
				<g:each in="${grailsApplication.config.lagrummet.search.courtList}">
					<option value="${it.value}"
						<g:if test="${it.disabled}">disabled="disabled"</g:if>
						<g:if test="${it.value && it.value == rattsfallParams?.utgivare}">selected="selected"</g:if>
					>
					${it.title}
					</option>
				</g:each>
			</select>
										
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
				<div class="legend"><g:message code="extendedSearch.lagrum.label" default="Lagrum" /></div>
				<div class="inputGroup">
					<label for="sfs" class="text"><g:message code="extendedSearch.sfs.label" default="SFS"/></label>
					<g:textField name="sfs" value="${rattsfallParams?.sfs}" />
				</div>
				<div class="inputGroup">
					<label for="kapitel" class="text"><g:message code="extendedSearch.kapitel.label" default="Kapitelnummer" /></label>
					<g:textField name="kapitel" value="${rattsfallParams?.kapitel}" /> 
				</div>
				<div class="inputGroup">
					<label for="paragraf" class="text"><g:message code="extendedSearch.paragraf.label" default="Paragrafnummer" /></label>
					<g:textField name="paragraf" value="${rattsfallParams?.paragraf}" />
				</div>
			</fieldset>
			
			<fieldset>
				<div class="legend"><g:message code="extendedSearch.avgorandeDatum.label" default="Avgörandedatum" /></div>
				<input type="hidden" name="datum" id="avgorandeDatum" value="avgorande" />
				<div class="inputGroup">
					<label for="fromDate" class="date"><g:message code="extendedSearch.datumMin.label" default="Från:"/></label>
					<input type="text" name="fromDate" size="10" value="${rattsfallParams?.fromDate}"  placeholder="åååå-mm-dd" class="dateinput"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="fromDate">
						<div class="error">
							<g:renderErrors bean="${extendedSearchCommand}" field="fromDate" />
						</div>
					</g:hasErrors>
				</div>
				
				<div class="inputGroup">
					<label for="toDate" class="date"><g:message code="extendedSearch.datumMax.label" default="Till:"/></label>
					<input type="text" name="toDate" size="10" value="${rattsfallParams?.toDate}" placeholder="åååå-mm-dd" class="dateinput"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="toDate">
						<div class="error">
							<g:renderErrors bean="${extendedSearchCommand}" field="toDate" />
						</div>
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
						<div class="error">
							<g:renderErrors bean="${extendedSearchCommand}" field="fromDate" />
						</div>
					</g:hasErrors>
				</div>
				
				<div class="inputGroup">
					<label for="toDate" class="date"><g:message code="extendedSearch.datumMax.label" default="Till:"/></label>
					<input type="text" name="toDate" size="10" value="${forarbeteParams?.toDate}" placeholder="åååå-mm-dd" class="dateinput"/>
					<g:hasErrors bean="${extendedSearchCommand}" field="toDate">
						<div class="error">
							<g:renderErrors bean="${extendedSearchCommand}" field="toDate" />
						</div>
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
    					<li><g:message code="${message}" /></li>
    				</g:each>
    			</ul>
    		</div>
    	</g:if>

		<g:if test="${searchResult?.totalResults}">
			<p class="printLabel"><a href="javascript:if(window.print)window.print()">Skriv ut</a></p>
	    	<p class="showAllLabel"><a href="${createLink(mapping:'extendedSearch', fragment: "searchResults", params:params + [max: searchResult?.totalResults, offset: 0])}">Visa alla ${searchResult?.totalResults} träffar</a></p>
	    	
			<h2 id="sokresultat">Sökresultat</h2>
		
			<p>Visar ${1+(offset ?: 0)  }-${(offset ?: 0)+searchResult.itemsList.size()} av ${searchResult.totalResults} träffar i <strong><g:message code="category.extendedSearch.${cat}"/></strong></p>
			
			<table>
				<tr>
					<th>Titel</th>
					<g:if test="${cat == 'Lagar' }"><th>SFS-nummer</th></g:if>
					<g:elseif test="${cat != 'Ovrigt' }"><th>Identifierare</th></g:elseif>
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
			<div id="searchHelpPuff">
				<strong>Hittade du inte vad du sökte?</strong>
				<p><a href="/sokhjalp">Sökhjälp</a> - Hjälpsida som ger dig tips på hur du kan söka på bästa sätt</p>
			</div>
		</g:if>
	</article>
</body>
</html>