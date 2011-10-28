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
}
