package contracts.tracking

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    request {
        method GET()
        url("/artist/tracking/url")
        headers {
            header(authorization(), "failed_status_access_token")
            contentType "application/json"
        }
    }

    response {
        status OK()
        headers {
            contentType("application/json")
        }
        body(
                [

                        status         : "pending",
                        "error_details": [
                                "code"       : "unacceptable_artist_name",
                                "description": "The artist name violates laws of Sonata"
                        ],
                        artist         : [
                                id           : "m1ku",
                                name         : "odeyalooobeatzzzz",
                                genres       : ["Lofi", "Rap", "Rock"],
                                social_medias: [
                                        [platform: "Twitter", url: "https://twitter.com/odeyalobeatzzz"],
                                        [platform: "Instagram", url: "https://instagram.com/odeyal0beatzzz"]
                                ]
                        ]
                ]
        )
    }
}