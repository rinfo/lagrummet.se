package se.lagrummet

import java.text.Format;


class QueryBuilder {
	
	private queryParams = [:]
	
	public QueryBuilder(Map params) {
		
		setBeslutsdatum(params.beslutsdatum)
		setBeslutsdatumFrom(params.beslutsdatumMin)
		setBeslutsdatumTo(params.beslutsdatumMax)
		setIkraft(params.ikraftDatum)
		setIkraftFrom(params.ikraftDatumMin)
		setIkraftTo(params.ikraftDatumMax)
		setIdentifier(params.beteckning)
		setTitle(params.titel)
		setMalnummer(params.malnummer)
		setType(params.typ)
		setPublisher(params.utgivare)
		setCreator(params.skapare)
		setReferatrubrik(params.referatrubrik)
		setPageAndPageSize(params.page, params.itemsPerPage)
		
	}
	
	private void setParam(String key, String value) {
		if(value) {
			queryParams[(key)] = value
		}
	}
	
	private void setParam(String key, Integer value) {
		if(value) {
			queryParams[(key)] = value
		}
	}
	
	public QueryBuilder setBeslutsdatum(String beslutsdatum){
		setParam('beslutsdatum', beslutsdatum)	
		return this
	}
	public QueryBuilder setBeslutsdatumFrom(String beslutsdatum) {
		setParam('min-beslutsdatum',beslutsdatum)
		return this
	}
	public QueryBuilder setBeslutsdatumTo(String beslutsdatum) {
		setParam('max-beslutsdatum', beslutsdatum)
		return this
	}
	
	public QueryBuilder setIkraft(String ikraft) {
		setParam('ikrafttradandedatum', ikraft)
		return this
	}
	public QueryBuilder setIkraftFrom(String ikraft) {
		setParam('min-ikrafttradandedatum', ikraft)
		return this
	}
	public QueryBuilder setIkraftTo(String ikraft) {
		setParam('max-ikrafttradandedatum', ikraft)
		return this
	}
	
	public QueryBuilder setIdentifier(String identifier) {
		setParam('identifier', identifier)
		return this
	}
	
	public QueryBuilder setTitle(String title) {
		setParam('title', title)
		return this
	}
	
	public QueryBuilder setMalnummer(String malnummer) {
		setParam('malnummer', malnummer)
		return this
	}
	
	public QueryBuilder setType(type) {
		if(type) {
			def key = 'type'
			def typeList = [type].flatten()
			queryParams[(key)] = typeList
		}
		return this
	}
	
	public QueryBuilder setPublisher(publisher) {
		if(publisher) {
			def key = "publisher.iri"
			def prefix = "*/"
			def publisherList = [publisher].flatten().collect { prefix + it }
			queryParams[(key)] = publisherList
		}
		return this
	}
	
	public QueryBuilder setCreator(creator) {
		if(creator) {
			def key = "creator.iri"
			def prefix = "*/"
			def creatorList = [creator].flatten().collect { prefix + it }
			queryParams[(key)] = creatorList
		}
		return this
	}
	
	
	public QueryBuilder setReferatrubrik(String referatrubrik) {
		setParam('referatrubrik', referatrubrik)
		return this
	}

	public QueryBuilder setPageAndPageSize(Integer page, Integer pageSize) {
		setParam('_page', page)
		setParam('_pageSize', pageSize)
		return this
	}
	
	public Map getQueryParams() {
		return queryParams
	}
	
	public String toString() {
		return queryParams.toString()
	}
}
