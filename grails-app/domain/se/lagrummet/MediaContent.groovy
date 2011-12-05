package se.lagrummet

class MediaContent {
	
	byte[] content
	
	static belongsTo = [media: Media]

    static constraints = {
		content(maxSize:1073741824)
    }
}
