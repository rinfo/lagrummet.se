package se.lagrummet

import grails.plugins.springsecurity.Secured
import grails.plugin.gson.converters.GSON
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
		
		def mediaInstances = (params.parentId) ? Media.findAllByParentIsNull() : Media.list(params)
		if (params.ajax) {
            def media = [] as List
            def pageMediaInstances = Page.get(params.parentId).media
            if (pageMediaInstances) {
                media << [title: "- Page-specific media", value: "#"]
                pageMediaInstances.each {
                    media << [title: it.title, value: resource(absolute: true) + '/' + it.filename]
                }
            }
            if (mediaInstances) {
                media << [title: "- Sitewide media", value: "#"]
                mediaInstances.each {
                    media << [title: it.title, value: resource(absolute: true) + '/' + it.filename]
                }
            }
            render media as GSON
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
        withForm {
            params.parent = Page.get(params.parentId)

            def mediaInstance = new Media(params)

            def uploadedFile = request.getFile('mediaFile')
            def contentType = uploadedFile.contentType
            def filename = uploadedFile.originalFilename

            def parentDir = (contentType == "image/jpeg" || contentType == "image/gif" || contentType == "image/png") ? "images" : "files"
            def today = new Date()

            mediaInstance.filename = mediaInstance.filename = grailsApplication.config.lagrummet.upload.dir + parentDir+ "/" + filename
            def mediaContent = new MediaContent(content: uploadedFile.getBytes())
            mediaInstance.content = mediaContent


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
        }.invalidToken {
            response.status = 403
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
        withForm {
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
                bindData(mediaInstance, params)
                if (!mediaInstance.hasErrors() && mediaInstance.save(flush: true)) {
                    flash.message = "${message(code: 'default.updated.message', args: [message(code: 'media.label', default: 'Media'), mediaInstance.id])}"
                    redirect(action: "edit", id: mediaInstance.id)
                }
                else {
                    render(view: "edit", model: [mediaInstance: mediaInstance])
                }
            }
            else {
                flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                redirect(action: "list")
            }
        }.invalidToken {
            response.status = 403
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def delete = {
        withForm {
            def mediaInstance = Media.get(params.id)
            if (mediaInstance) {
                try {
                    mediaInstance.delete(flush: true)
                    flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                    redirect(action: "list")
                }
                catch (org.springframework.dao.DataIntegrityViolationException e) {
                    flash.message = "${message(code: 'media.not.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.title])}"
                    redirect(action: "edit", id: params.id)
                }
            }
            else {
                flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                redirect(action: "list")
            }
        }.invalidToken {
            response.status = 403
        }
    }

	def viewMediaContent = {
		def fileName = grailsApplication.config.lagrummet.upload.dir + params.filename
		def media = Media.findByFilename(fileName)
		if(media?.content) {
            lastModified (media.lastUpdated ?: media.dateCreated)
            cache validFor: 864000, shared:true

			response.setContentLength(media.content.content.length)
			response.outputStream.write(media.content.content)
		} else {
			response.sendError(404)
		}
	}
}
