(ns pjmusic.routes.home
  (:require [pjmusic.layout :as layout]
            [pjmusic.db.core :as db]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io])
  (:import (java.io ByteArrayInputStream)))

(defn home-page []
  (layout/render "recent.html" {:releases (db/get-recent-releases 8)}))

(defn artist-page [id]
  (layout/render "artist.html" (assoc {:artist (db/get-artist {:id id})} :releases (db/get-artist-releases {:id id}))))

(defn release-page [id]
  (layout/render "release.html" {:release (db/get-release-tracks id)}))

(defn get-release-art [id]
  (-> (ByteArrayInputStream. (db/get-art id))
      (response/ok)
      (response/content-type "image/jpeg")))

(defroutes home-routes
           (GET "/" []
             (home-page))
           (GET "/artist/:id" [id]
             (artist-page id))
           (GET "/release/:id" [id]
             (release-page id))
           (GET "/release-art/:id" [id]
             (get-release-art id))
           (GET "/docs" []
             (-> (response/ok (-> "docs/docs.md" io/resource slurp))
                 (response/header "Content-Type" "text/plain; charset=utf-8"))))
