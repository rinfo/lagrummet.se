package se.lagrummet

class SitePropertiesFilters {

    def filters = {
        all(controller:'(rinfo|search)', action:'*') {
            before = {
                
            }
            after = { model ->
				model?.siteProps = SiteProperties.findByTitle("lagrummet.se")
            }
            afterView = {
                
            }
        }
    }
    
}
