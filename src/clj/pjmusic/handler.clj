(ns pjmusic.handler
  (:require [pjmusic.middleware :as middleware]
            [pjmusic.layout :refer [error-page]]
            [pjmusic.routes.home :refer [home-routes]]
            [pjmusic.routes.service :refer [service-routes]]
            [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [pjmusic.env :refer [defaults]]
            [mount.core :as mount]))

(mount/defstate init-app
  :start ((or (:init defaults) identity))
  :stop  ((or (:stop defaults) identity)))

(mount/defstate app
  :start
  (middleware/wrap-base
    (routes
      (-> #'home-routes
          (wrap-routes middleware/wrap-csrf)
          (wrap-routes middleware/wrap-formats))
      #'service-routes
      (route/not-found
        (:body
          (error-page {:status 404
                       :title "page not found"}))))))

