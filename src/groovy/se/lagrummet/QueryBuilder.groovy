package se.lagrummet

import java.text.Format;


class QueryBuilder {
	
	private queryParams = [:]
	
	public QueryBuilder() {
		
	}
	
	public QueryBuilder(Map params) {
		
		setQuery(params.fritext)
		setBeslutsdatum(params.beslutsdatum)
		setBeslutsdatumFrom(params.beslutsdatumMin)
		setBeslutsdatumTo(params.beslutsdatumMax)
		setUtfardandedatum(params.utfardandedatum)
		setUtfardandedatumFrom(params.utfardandedatumMin)
		setUtfardandedatumTo(params.utfardandedatumMax)
		setIkraft(params.ikraftDatum)
		setIkraftFrom(params.ikraftDatumMin)
		setIkraftTo(params.ikraftDatumMax)
		setUtkomFranTryck(params.utkomfrantryck)
		setUtkomFranTryckFrom(params.utkomfrantryckMin)
		setUtkomFranTryckTo(params.utkomfrantryckMax)
		setAvgorandedatum(params.avgorandeDatum)
		setAvgorandedatumFrom(params.avgorandedatumMin)
		setAvgorandedatumTo(params.avgorandedatumMax)
		setIdentifier(params.beteckning)
		setTitle(params.titel)
		setMalnummer(params.malnummer)
		setType(params.typ)
		setPublisher(params.utgivare)
		setCreator(params.skapare)
		setReferatrubrik(params.referatrubrik)
		setParagrafnummer(params.paragrafnummer)
		setPageAndPageSize(params.page, params.itemsPerPage)
		setSubject(params.sokord)
		setForfattningssamling(params.forfattningssamling)
		setLagrum(params.sfs, params.kapitel, params.paragraf)
		
	}
	
	public QueryBuilder setParam(String key, String value) {
		if(value) {
			queryParams[(key)] = value
		}
		return this
	}
	
	private void setBlankParam(String key, String value) {
		queryParams[(key)] = value
	}
	
	private void setParam(String key, Integer value) {
		if(value != null) {
			queryParams[(key)] = value
		}
	}
	
	public QueryBuilder setQuery(String query) {
		setParam('q', query)
		return this
	}
	
	public QueryBuilder setBeslutsdatum(String beslutsdatum){
		setParam('beslutsdatum', beslutsdatum)	
		return this
	}
	public QueryBuilder setBeslutsdatumFrom(String beslutsdatum) {
		setParam('min-beslutsdatum',beslutsdatum)
		return this
	}
	public QueryBuilder setOrBeslutsdatumFrom(String beslutsdatum) {
		setParam('or-min-beslutsdatum',beslutsdatum)
		return this
	}
	public QueryBuilder setBeslutsdatumTo(String beslutsdatum) {
		setParam('max-beslutsdatum', beslutsdatum)
		return this
	}
	public QueryBuilder setOrBeslutsdatumTo(String beslutsdatum) {
		setParam('or-max-beslutsdatum', beslutsdatum)
		return this
	}
	
	public QueryBuilder setUtfardandedatum(String utfardandedatum) {
		setParam('utfardandedatum', utfardandedatum)
		return this
	}
	public QueryBuilder setUtfardandedatumFrom(String utfardandedatum) {
		setParam('min-utfardandedatum', utfardandedatum)
		return this
	}
	public QueryBuilder setOrUtfardandedatumFrom(String utfardandedatum) {
		setParam('or-min-utfardandedatum', utfardandedatum)
		return this
	}
	public QueryBuilder setUtfardandedatumTo(String utfardandedatum) {
		setParam('max-utfardandedatum', utfardandedatum)
		return this
	}
	public QueryBuilder setOrUtfardandedatumTo(String utfardandedatum) {
		setParam('or-max-utfardandedatum', utfardandedatum)
		return this
	}
	
	public QueryBuilder setIkraft(String ikraft, String... operators) {
		def op = operators ? operators.join('-') + "-" : ""
		setParam(op + 'ikrafttradandedatum', ikraft)
		return this
	}
	public QueryBuilder setIkraftFrom(String ikraft) {
		setIkraft(ikraft, 'min')
		return this
	}
	public QueryBuilder setIkraftTo(String ikraft) {
		setIkraft(ikraft, 'max')
		return this
	}
	public QueryBuilder setIkraftIfExists(String ikraft) {
		setBlankParam('ifExists-ikrafttradandedatum', ikraft)
	}
	
	public QueryBuilder setUtkomFranTryck(String utkomfrantryck) {
		setParam('utkomFranTryck', utkomfrantryck)
		return this
	}
	public QueryBuilder setUtkomFranTryckFrom(String utkomfrantryck) {
		setParam('min-utkomFranTryck', utkomfrantryck)
		return this
	}
	public QueryBuilder setUtkomFranTryckTo(String utkomfrantryck) {
		setParam('max-utkomFranTryck', utkomfrantryck)
		return this
	}
	
	
	public QueryBuilder setAvgorandedatum(String avgorandedatum) {
		setParam('avgorandedatum', avgorandedatum)
		return this
	}
	public QueryBuilder setAvgorandedatumFrom(String avgorandedatum) {
		setParam('min-avgorandedatum', avgorandedatum)
		return this
	}
	public QueryBuilder setAvgorandedatumTo(String avgorandedatum) {
		setParam('max-avgorandedatum', avgorandedatum)
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
			def publisherList = [publisher].flatten().collect { getIriSearchString(it) }
			queryParams[(key)] = publisherList
		}
		return this
	}
	
	public QueryBuilder setCreator(creator) {
		if(creator) {
			def key = "creator.iri"
			def creatorList = [creator].flatten().collect { getIriSearchString(it) }
			queryParams[(key)] = creatorList
		}
		return this
	}
	
	private String getIriSearchString(String searchParam) {
		String iri = searchParam.toLowerCase()
		iri = iri.replace('å', 'aa')
		iri = iri.replace('ä', 'ae')
		iri = iri.replace('ö', 'oe')
		iri = iri.replace(' ', '_')
		
		iri = "*/" + iri
		return iri
	}
	
	
	public QueryBuilder setReferatrubrik(String referatrubrik) {
		setParam('referatrubrik', referatrubrik)
		return this
	}
	
	public QueryBuilder setParagrafnummer(String paragrafnummer) {
		setParam("lagrum.paragrafnummer", paragrafnummer)
		return this
	}

	public QueryBuilder setPageAndPageSize(Integer page, Integer pageSize) {
		setParam('_page', page)
		setParam('_pageSize', pageSize)
		return this
	}
	
	public QueryBuilder setSubject(String subject) {
		setParam('subject', subject)
		return this
	}
	
	public QueryBuilder setForfattningssamling(String forfattningssamling) {
		setParam('forfattningssamling.prefLabel', forfattningssamling)
		return this
	}
	
	public QueryBuilder setLagUpphavdAt(String date){
		setIkraftTo(date)
		setParam('maxEx-rev.upphaver.ikrafttradandedatum', date)
	}
	public QueryBuilder setLagGallandeAt(String date) {
		setIkraftTo(date)
		setParam('ifExists-minEx-rev.upphaver.ikrafttradandedatum', date)
		return this
	}
	public QueryBuilder setLagKommandeAt(String date) {
		setIkraftFrom(date)
		return this
	}
	
	public QueryBuilder setLagrum(String sfs, String chapter, String section) {
//		http://rinfo.lagrummet.se/publ/sfs/1972:207#k_5-p_7
		String lagrum 
		
		if(sfs) {
			lagrum = "*/" + sfs
		}
		
		if(chapter){
			chapter = "k_" + chapter.replace(' ', '_')
		}
		if(section) {
			section = "p_" + section
		}
		
		if(lagrum && (chapter || section)) {
			lagrum += "#"
			lagrum += chapter
			if(chapter && section) {
				lagrum += "-"
			}
			lagrum += section
		}
		
		setParam('lagrum.iri', lagrum)
		return this
		
	}
	
	public Map getQueryParams() {
		return queryParams
	}
	
	public String toString() {
		return queryParams.toString()
	}
	
	public boolean isEmpty() {
		return queryParams.size() == 0
	}
}
