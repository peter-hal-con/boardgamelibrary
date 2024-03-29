package com.halcon.gaming.boardgamelibrary

import grails.util.Environment

class UrlMappings {

    static mappings = {
        if (Environment.current == Environment.TEST) {
            "/testOnly/$action?/$id?(.$format)?"(controller: "testOnly")
        } else {
            "/testOnly/$action?/$id?(.$format)?"(view: '/notFound')
        }

        delete "/$controller/$id(.$format)?"(action: "delete")
        get "/$controller(.$format)?"(action: "index")
        get "/$controller/$id(.$format)?"(action: "show")
        post "/$controller(.$format)?"(action: "save")
        put "/$controller/$id(.$format)?"(action: "update")
        patch "/$controller/$id(.$format)?"(action: "patch")

        '/'(uri: '/index.html')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
