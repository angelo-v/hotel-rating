class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

//		"/"(view:"/index")
        "/" {
            controller = "listHotels"
        }
        "500" (view: '/error')
    }
}
