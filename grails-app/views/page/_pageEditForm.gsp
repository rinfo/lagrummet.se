<table>
	<tbody>

		<tr class="prop">
			<td valign="top" class="name"><label for="title"><g:message
						code="page.title.label" default="Title" />
			</label></td>
			<td valign="top"
				class="value ${hasErrors(bean: pageInstance, field: 'title', 'errors')}">
				<g:textField name="title" value="${pageInstance?.title}" /></td>
		</tr>

		<tr class="prop">
			<td valign="top" class="name"><label for="h1"><g:message
						code="page.h1.label" default="H1" />
			</label></td>
			<td valign="top"
				class="value ${hasErrors(bean: pageInstance, field: 'h1', 'errors')}">
				<g:textField name="h1" value="${pageInstance?.h1}" /></td>
		</tr>

		<tr class="prop">
			<td valign="top" class="name"><label for="permalink"><g:message
						code="page.permalink.label" default="Permalink" />
			</label></td>
			<td valign="top"
				class="value ${hasErrors(bean: pageInstance, field: 'permalink', 'errors')}">
				<g:textField name="permalink" value="${pageInstance?.permalink}" />
			</td>
		</tr>

		<tr class="prop">
			<td valign="top" class="name"><label for="content"><g:message
						code="page.content.label" default="Content" />
			</label></td>
			<td valign="top"
				class="value ${hasErrors(bean: pageInstance, field: 'content', 'errors')}">
				<g:textArea name="content" value="${pageInstance?.content}" /></td>
		</tr>

		<tr class="prop">
			<td valign="top" class="name"><label for="parent"><g:message
						code="page.parent.label" default="Parent" />
			</label></td>
			<td valign="top"
				class="value ${hasErrors(bean: pageInstance, field: 'parent', 'errors')}">
				<g:select name="parent.id" from="${se.lagrummet.Page.list()}"
					optionKey="id" value="${pageInstance?.parent?.id}"
					noSelection="['null': '']" /></td>
		</tr>

		<tr class="prop">
			<td valign="top" class="name"><label for="status"><g:message
						code="page.status.label" default="Status" />
			</label></td>
			<td valign="top"
				class="value ${hasErrors(bean: pageInstance, field: 'status', 'errors')}">
				<g:select name="status" from="['draft', 'pending', 'published']"
					value="${pageInstance?.status}" /></td>
		</tr>

		<tr class="prop">
			<td valign="top" class="name"><label for="publishStart"><g:message
						code="page.publishStart.label" default="Publish Start" />
			</label></td>
			<td valign="top"
				class="value ${hasErrors(bean: pageInstance, field: 'publishStart', 'errors')}">
				<g:datePicker name="publishStart" precision="day"
					value="${pageInstance?.publishStart}" default="none"
					noSelection="['': '']" /></td>
		</tr>

		<tr class="prop">
			<td valign="top" class="name"><label for="publishStop"><g:message
						code="page.publishStop.label" default="Publish Stop" />
			</label></td>
			<td valign="top"
				class="value ${hasErrors(bean: pageInstance, field: 'publishStop', 'errors')}">
				<g:datePicker name="publishStop" precision="day"
					value="${pageInstance?.publishStop}" default="none"
					noSelection="['': '']" /></td>
		</tr>

		<tr class="prop">
			<td valign="top" class="name"><label for="pageOrder"><g:message
						code="page.pageOrder.label" default="Page Order" />
			</label></td>
			<td valign="top"
				class="value ${hasErrors(bean: pageInstance, field: 'pageOrder', 'errors')}">
				<g:textField name="pageOrder"
					value="${fieldValue(bean: pageInstance, field: 'pageOrder')}" /></td>
		</tr>

	</tbody>
</table>