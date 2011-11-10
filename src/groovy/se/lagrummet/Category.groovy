package se.lagrummet

public enum Category {
	RATTSFALL("Rattsfall"), LAGAR("Lagar"), PROPOSITIONER("Propositioner"), UTREDNINGAR("Utredningar"), OVRIGT("Ovrigt"), OKAND("Okand")
	private final String title
	Category(String title) { this.title = title }
	public String toString() { return title }
	
	public static String getCategoryForType(String type) {
		//Lagar, Rattsfall, Propositioner, Utredningar, Ovrigt
		return typeToCategory[(type)] ?: Category.OKAND
		
	}
	
	public static Category getFromString(String cat) {
		for(c in Category.values()) {
			if(c.toString().equals(cat)) {
				return c
			}
		}
		return null
	}
	
	public List getTypes() {
		def types = []
		typeToCategory.each { type, category ->
			if(this.equals(category)){
				types.add(type)
			}
		}
		
		return types
	}
	
	static typeToCategory = [
		'Rattsfallsnotis' : Category.RATTSFALL,
		'Rattsfallsreferat' : Category.RATTSFALL,
		'VagledandeDomstolsavgorande' : Category.RATTSFALL,
		'VagledandeMyndighetsavgorande' : Category.RATTSFALL,
		
		'Forordning' : Category.LAGAR,
		'Grundlag' : Category.LAGAR,
		'KonsolideradGrundforfattning' : Category.LAGAR,
		'Lag' : Category.LAGAR,
		'Myndighetsforeskrift' : Category.LAGAR,
		'Tillkannagivande' : Category.LAGAR,
		'Rattelseblad' : Category.LAGAR,
		'Brev' : Category.LAGAR,
		'Cirkular' : Category.LAGAR,
		'AllmannaRad' : Category.LAGAR,
		
		'Proposition' : Category.PROPOSITIONER,
		
		'Kommittedirektiv' : Category.UTREDNINGAR,
		'Utredningsbetankande' : Category.UTREDNINGAR,
		'Utredningsserie' : Category.UTREDNINGAR,
		
		'Lagrummet.Artikel' : Category.OVRIGT
		]
	
	static extendedSearchTypes = [
		'Lagar' : ['Lag', 'Grundlag'],
		'Myndigheters föreskrifter' : ['Myndighetsforeskrift'],
		'Förordningar' : ['Forordning'],
	]
}
