(ns pjmusic.routes.home
  (:require [pjmusic.layout :as layout]
            [pjmusic.routes.music.core :as music]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render "recent.html" {:releases (music/get-recent-releases 10)}))

(defn artist-page [id]
  (layout/render "artist.html" {:artist (music/get-artist id)
                                :releases (music/get-artist-releases id)}))

(defn release-page [id]
  (layout/render "release.html" {:release (music/get-release id)
                                 :medias (music/get-release-medias id)}))

(defn get-release-image [id]
  (-> (music/get-release-image id)
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
             (get-release-image id))
           (GET "/docs" []
             (-> (response/ok (-> "docs/docs.md" io/resource slurp))
                 (response/header "Content-Type" "text/plain; charset=utf-8"))))
