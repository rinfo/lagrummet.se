<html>
<head>
	<title>${docInfo.identifier} - ${docInfo.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article id="rinfo">    	
        <!--
        ********************************************************************************
          Rubrik
        ********************************************************************************
        -->
        <h1>${docInfo.title}</h1>  
        
        
	<div id="leftCol">
          <!--
          ********************************************************************************
           Dokument information
          ********************************************************************************
          -->
            <table>
            <g:if test="${docEntry*.link*.@type.join('|').contains('application/pdf') || docEntry*.content*.@type.join('|').contains('application/pdf')}">
                    <tr>
                            <td class="label">Lagtext:</td><td>
                                            <g:each in="${docEntry.link}" var="link">
                                                    <g:if test="${link.@type == 'application/pdf'}">
                                                            <a href="${grailsApplication.config.lagrummet.rdl.rinfo.baseurl + link.@href}">${docInfo.title} <img src="${resource() }/images/PDF.png" class="pdfIcon" /></a>
                                                    </g:if>
                                            </g:each>
                                            <g:each in="${docEntry.content}" var="content">
                                                    <g:if test="${content.@type == 'application/pdf'}">
                                                            <a href="${grailsApplication.config.lagrummet.rdl.rinfo.baseurl + content.@src}">${docInfo.title} <img src="${resource() }/images/PDF.png" class="pdfIcon" /></a>
                                                    </g:if>
                                            </g:each>
                            </td></tr>
                    </g:if>

                    <g:if test="${docInfo.identifier}">
                      <tr><td class="label">Beteckning:</td><td> ${docInfo.identifier}</td></tr>  
                    </g:if>                  

              
                    <tmpl:labelRow label="Ikraft" value="${docInfo.ikrafttradandedatum}" />
                    <tmpl:labelRow label="Målnummer" value="${docInfo.referatAvDomstolsavgorande?.malnummer}" />			
                    <tmpl:labelRow label="Målnummer" value="${docInfo.malnummer}" />	
                    <tmpl:labelRow label="Avgörandedatum" value="${docInfo.referatAvDomstolsavgorande?.avgorandedatum}" />		
                    <tmpl:labelRow label="Avgörandedatum" value="${docInfo.avgorandedatum}" />		

                    
                    <g:if test="${docInfo.forarbete}">
                            <tr><td class="label">Förarbeten: </td><td>
                                    <ul>
                                    <g:each in="${docInfo.forarbete.sort{ it.identifier } }" var="forarbete">
                                            <g:if test="${forarbete.identifier && forarbete.iri}">
                                                    <li><a href="${forarbete.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${forarbete.identifier}</a></li>
                                            </g:if>
                                            <g:elseif test="${forarbete.identifier}">
                                                    ${forarbete.identifier}
                                            </g:elseif>
                                    </g:each>
                                    </ul>
                            </td></tr>
                    </g:if>
            </table>                    
            <!--            
            ********************************************************************************
              Innehåll 
            ********************************************************************************
            -->
            <g:if test="${content}">
                <hr/>
                <div>${content }</div>
            </g:if>          
        </div>
		
        <aside id="rinfoSidebar">
                <!--
                ********************************************************************************
                  Grundförfattning som ändras (1)
                ********************************************************************************
                -->          
                <g:if test="${docInfo.andrar}">
                        <h3>Grundförfattning som ändras</h3>
                        <g:each in="${docInfo.andrar}" var="item">
                                <ul>
                                        <li class="label">Titel:</li>
                                        <li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title}</a></li>
                                        <li class="label">Beteckning:</li>
                                        <li>${item.identifier}</li>
                                        <li class="label">Ikraft:</li>
                                        <li>${item.ikrafttradandedatum}</li>
                                </ul>
                        </g:each>
                </g:if>
                <!--
                ********************************************************************************
                  Grundförfattning som ändras (2)
                ********************************************************************************
                -->
                <g:if test="${docInfo.konsoliderar}">
                        <h3>Grundförfattning som ändras</h3>
                        <ul>
                                <li class="label">Titel:</li>
                                <li><a href="${docInfo.konsoliderar.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${docInfo.konsoliderar.title}</a></li>
                                <li class="label">Beteckning:</li>
                                <li>${docInfo.konsoliderar.identifier}</li>
                                <li class="label">Ikraft:</li>
                                <li>${docInfo.konsoliderar.ikrafttradandedatum}</li>
                        </ul>
                </g:if>
                <!--
                ********************************************************************************
                  Senaste konsoliderade versionen
                ********************************************************************************
                -->
                <g:if test="${docInfo.rev?.konsoliderar}">
                        <h3>Senaste konsoliderade versionen</h3>
                        <g:latestConsolidated in="${docInfo.rev.konsoliderar}" var="item">
                                <ul>
                                        <li class="label">Titel:</li>
                                        <li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title}</a></li>
                                        <li class="label">Beteckning:</li>
                                        <li>${item.identifier}</li>
                                </ul>
                        </g:latestConsolidated>
                </g:if>
                <!--
                ********************************************************************************
                  Upphävande författning
                ********************************************************************************
                -->
                <g:if test="${docInfo.rev?.upphaver}">
                        <h3>Upphävande författning</h3>
                        <span>Författning som upphäver:</span><br/>
                        <span class="subtitle">${docInfo.title}</span>
                        <g:each in="${docInfo.rev.upphaver}" var="item" status="i">
                        <ul>
                                <li class="label">Titel:</li>
                                <li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title}</a></li>
                                <li class="label">Beteckning:</li>
                                <li>${item.identifier}</li>
                                <li class="label">Ikraft:</li>
                                <li>${item.ikrafttradandedatum}</li>
                        </ul>
                        </g:each>
                </g:if>
                <!--
                ********************************************************************************
                  Hänvisande rättsfall
                ********************************************************************************
                -->
                <g:if test="${docInfo.rev?.rattsfallshanvisning}">
                        <g:set var="toggleId" value="toggleHanvisning" />
                        <g:set var="isExpanded" value="${params[(toggleId)]}" />
                        <h3>Hänvisande rättsfall (${docInfo.rev.rattsfallshanvisning.size()})</h3>
                        <span>Rättsfall som hänvisar till:</span><br/>
                        <span class="subtitle">${docInfo.identifier}</span>
                        <g:each in="${docInfo.rev.rattsfallshanvisning}" var="item" status="i">
                                <g:if test="${i >= 4}">
                                <ul class="${toggleId} ${isExpanded ? '' : 'hidden' }">
                                </g:if>
                                <g:else>
                                <ul>
                                </g:else>
                                        <li class="label">Titel:</li>
                                        <g:each in="${item.rev?.referatAvDomstolsavgorande}" var="referat">
                                                <li><a href="${referat.iri?.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${referat.identifier}</a></li>
                                        </g:each>
                                        <li class="label">Målnummer:</li>
                                        <li>${item.malnummer}</li>
                                        <li class="label">Avgörandedatum:</li>
                                        <li>${item.avgorandedatum}</li>
                                </ul>
                        </g:each>
                        <g:if test="${docInfo.rev.rattsfallshanvisning.size() > 4}">
                        <g:toggleLink toggleId="${toggleId}" mapping="rinfo">
                                        <span>Visa alla ${docInfo.rev.rattsfallshanvisning.size()} hänvisande rättsfall &#x25BC;</span>
                                        <span class="hidden">Dölj hänvisande rättsfall &#x25b2;</span>
                        </g:toggleLink>
                        </g:if>
                </g:if>
                <!--
                ********************************************************************************
                  Ändringsförfattningar
                ********************************************************************************
                -->
                <g:if test="${docInfo.rev?.andrar}">
                        <g:set var="toggleId" value="toggleAndrar" />
                        <g:set var="isExpanded" value="${params[(toggleId)]}" />
                        <h3>Ändringsförfattningar (${docInfo.rev.andrar.size()})</h3>
                        <span>Författning som ändrar:</span><br/>
                        <span class="subtitle">${docInfo.title}</span>
                        <g:each in="${docInfo.rev.andrar.sort{ it.ikrafttradandedatum }}" var="item" status="i">
                                <g:if test="${i >= 4}">
                                <ul class="${toggleId} ${isExpanded ? '' : 'hidden' }">
                                </g:if>
                                <g:else>
                                <ul>
                                </g:else>
                                        <li class="label">Titel:</li>
                                        <li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title}</a></li>
                                        <li class="label">Beteckning:</li>
                                        <li>${item.identifier}</li>
                                        <li class="label">Ikraft:</li>
                                        <li>${item.ikrafttradandedatum}</li>
                                </ul>
                        </g:each>
                        <g:if test="${docInfo.rev.andrar.size() > 4}">
                        <g:toggleLink toggleId="${toggleId}" mapping="rinfo">
                                        <span class="${isExpanded ? 'hidden' : '' }">Visa alla ${docInfo.rev.andrar.size()} ändringsförfattningar &#x25BC;</span>
                                        <span class="${!isExpanded ? 'hidden' : '' }">Dölj ändringsförfattningar &#x25b2;</span>
                        </g:toggleLink>
                        </g:if>
                </g:if>
                <!--
                ********************************************************************************
                  Referat av domstolsavgörande
                ********************************************************************************
                -->
                <g:if test="${docInfo.rev?.referatAvDomstolsavgorande}">
                        <h3>Referat av domstolsavgörande:</h3>
                        <g:each in="${docInfo.rev?.referatAvDomstolsavgorande}" var="item">
                                <ul>
                                        <li class="label">Referatrubrik:</li>
                                        <li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.referatrubrik}</a></li>
                                        <li class="label">Beteckning:</li>
                                        <li>${item.identifier}</li>
                                        <li class="label">Utgivet:</li>
                                        <li>${item.issued}</li>
                                </ul>
                        </g:each>
                </g:if>
        </aside>
  </article>	
</body>
</html>