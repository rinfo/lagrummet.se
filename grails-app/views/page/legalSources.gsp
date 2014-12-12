<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}" />
</head>
<body>
    <article class="editorial legalSources">
	
        <header><h1>${page.h1}</h1></header>
	
        ${page.content}
	
        <div class="legalSourceContainer">
        
          <g:each in="${legalSourceGroups}" var="category">
            <g:if test="${category.value['sokbar'] || category.value['inteSokbar']}">
              
                <section class="${category.key}">
	      
                  <h2><g:message code="legalSource.category.${category.key}" /></h2>
	            
                  <g:each in="${category.value}">	          
                      <g:if test="${it.value}">                        
	            	<h3 id="${category.key}_${it.key}"><g:message code="legalSource.category.${it.key}" /></h3>
	            	
                                <g:each in="${it.value}" var="subCategory">                                                    
                                            <g:if test="${subCategory.key != ''}">
                                                <h4><g:message code="legalSource.subCategory.${subCategory.key}" /></h4>
                                            </g:if>                                    

                                            <g:set var="toggleId" value="${category.key.toLowerCase()}_${it.key.toLowerCase()}_${subCategory.key?.toLowerCase() ?: category.key.toLowerCase()}" />
                                            <g:set var="isExpanded" value="${params[(toggleId)]}" />

                                            <ul id="${category.key}_${it.key}_list">
                                              <g:each in="${subCategory.value}" var="lI" status="i">
                                                <g:if test="${i<=9}">
                                                  <li><a href="${lI.url}">${lI.name}</a></li>
                                                </g:if>
                                                <g:else>
                                                  <li class="${toggleId} ${isExpanded ? '' : 'hidden' }"><a href="${lI.url}">${lI.name}</a></li>
                                                </g:else>
                                              </g:each>
                                            </ul>

                                            <g:if test="${subCategory.value.size() > 10}">
                                              <g:toggleLink toggleId="${toggleId}" mapping='page'>
                                                <span class="${isExpanded ? 'hidden' : '' }">Visa alla ${subCategory.value.size()} rättskällor &#x25BC;</span>
                                                <span class="${!isExpanded ? 'hidden' : '' }">Dölj rättskällor &#x25b2;</span>
                                              </g:toggleLink>								
                                           </g:if>                          
                              </g:each>                        
                    </g:if>
                  </g:each>
	      </section>
              
              
            </g:if>
          </g:each>
	</div>
        
	<footer class="reviewed">Senast granskad: ${page.publishStart.format('yyyy-MM-dd')}</footer>
        
    </article>
  
</body>
</html>