(ns pjmusic.routes.service
  (:require [pjmusic.routes.music.core :as music]
            [cheshire.core :as json]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]))

(defn suggest-artists [term]
  (-> (json/generate-string (music/api-suggest-artists term))
      (response/ok)
      (response/content-type "application/json")))

(defroutes service-routes
           (GET "/api/artists" [term]
                (suggest-artists term)))
