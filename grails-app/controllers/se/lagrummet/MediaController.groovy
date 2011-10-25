package se.lagrummet

import grails.plugins.springsecurity.Secured
import org.apache.commons.io.IOUtils
import javax.imageio.*
import org.springframework.web.multipart.commons.CommonsMultipartFile

class MediaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def index = {
        redirect(action: "list", params: params)
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def mediaInstances = (params.parentId) ? Page.get(params.parentId).media : Media.list(params)
		 
		def mediaInstancesCount =  mediaInstances.size()
		
		if (params.ajax) {
			render "var tinyMCEImageList = new Array("
			
			mediaInstances.eachWithIndex() { mI, i ->
				render '["' + mI.title + '", "' + grailsApplication.getConfig().grails.serverURL + '/' + mI.filename + '"]'
				if ((i+1) != mediaInstancesCount) {
					render ","
				}
			}
			
			render ");"
		} else {
			[mediaInstanceList: mediaInstances, mediaInstanceTotal: Media.count()]
		}
        
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def create = {
        def mediaInstance = new Media()
        return [mediaInstance: mediaInstance]
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def save = {
		params.parent = Page.get(params.parentId)
		
        def mediaInstance = new Media(params)
		
		def CommonsMultipartFile uploadedFile = params.mediaFile
		def contentType = uploadedFile.contentType
		def filename = uploadedFile.originalFilename
		def size = uploadedFile.size
		
		def is = uploadedFile.getInputStream()
		def parentDir = (contentType == "image/jpeg" || contentType == "image/gif" || contentType == "image/png") ? "images" : "files"
		def today = new Date()
		
		// Create a directory
//		String.format('%tF', new Date())
		
		mediaInstance.filename = grailsApplication.config.lcms.upload.dir + parentDir+ "/" + filename
		def fos = new FileOutputStream(new File("web-app/" + mediaInstance.filename));
		IOUtils.copy(is, fos);
		fos.close();
		is.close();
		
        if (mediaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'media.label', default: 'Media'), mediaInstance.id])}"
            if (mediaInstance.parent) {
				redirect(controller: "page", action: "edit", id: mediaInstance.parent.id)
			} else {
				redirect(action: "edit", id: mediaInstance.id)
			}
        }
        else {
            render(view: "create", model: [mediaInstance: mediaInstance])
        }
    }

    def show = {
        def mediaInstance = Media.get(params.id)
        if (!mediaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
        else {
            [mediaInstance: mediaInstance]
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def edit = {
        def mediaInstance = Media.get(params.id)
        if (!mediaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [mediaInstance: mediaInstance]
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def update = {
        def mediaInstance = Media.get(params.id)
        if (mediaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (mediaInstance.version > version) {
                    mediaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'media.label', default: 'Media')] as Object[], "Another user has updated this Media while you were editing")
                    render(view: "edit", model: [mediaInstance: mediaInstance])
                    return
                }
            }
            mediaInstance.properties = params
            if (!mediaInstance.hasErrors() && mediaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'media.label', default: 'Media'), mediaInstance.id])}"
                redirect(action: "show", id: mediaInstance.id)
            }
            else {
                render(view: "edit", model: [mediaInstance: mediaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def delete = {
        def mediaInstance = Media.get(params.id)
        if (mediaInstance) {
            try {
                mediaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
    }
}
