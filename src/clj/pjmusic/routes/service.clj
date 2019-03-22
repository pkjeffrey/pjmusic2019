(ns pjmusic.routes.service
  (:require [cheshire.core :as json]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]))

(defn suggest-artists [q]
  (-> (json/generate-string '({:id 1 :name "One"}
                            {:id 2 :name "Two"}))
      (response/ok)
      (response/content-type "application/json")))

(defroutes service-routes
           (GET "/api/artists/:q" [q]
                (suggest-artists q)))
