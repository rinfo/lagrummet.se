<g:if test="${value}">
<tr><td class="label">${label}:</td><td><g:if test="${value instanceof List || value instanceof Map}">${value.join(", ")}</g:if><g:else>${value}</g:else></td></tr>
</g:if>