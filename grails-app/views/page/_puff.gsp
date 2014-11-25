		
		<g:if test="${!puff.isEmpty()}">
		<div class="puff">
			<g:if test="${puff.image}">
				<g:linkConditional href="${puff.link}" class="image">
					<img src="${grailsApplication.config.grails.serverURL}/${puff.image?.filename}" alt="${puff.image?.title}"/>
				</g:linkConditional>
			</g:if>
			<g:if test="${puff.title}">
				<h3><g:linkConditional href="${puff.link}">${puff.title}</g:linkConditional></h3>
			</g:if>
			<p>${puff.description}</p>
		</div>
		</g:if>
