(ns pjmusic.routes.music.core
  (:require [clojure.string :as str]
            [pjmusic.db.core :as db])
  (:import (java.io ByteArrayInputStream)))

(defn api-suggest-artists [q]
  (let [qs (re-seq #"[a-z]+|[0-9]+" (str/lower-case q))
        artists (db/get-artists)]
    (filter #(str/starts-with?))))

(defn get-release-media-description [id]
  (let [descrs (map #(if (= 1 (:cnt %)) (:name %) (str (:cnt %) (:name %)))
                    (db/get-release-media-descrs {:id id}))]
    (apply str (first descrs) (map #(str "+" %) (rest descrs)))))

(defn get-recent-releases [num]
  (let [rels (db/get-recent {:num num})]
    (map #(assoc %
            :artist (db/get-artist {:id (:artist %)})
            :media-descr (get-release-media-description (:id %)))
         rels)))

(defn get-artist [id]
  (db/get-artist {:id id}))

(defn get-artist-releases [id]
  (let [rels (db/get-artist-releases {:id id})]
    (map #(assoc %
            :media-descr (get-release-media-description (:id %)))
         rels)))

(defn get-release [id]
  (let [rel (db/get-release {:id id})
        artist (db/get-artist {:id (:artist rel)})
        media-descr (get-release-media-description id)]
    (assoc rel
      :artist artist
      :media-descr media-descr)))

(defn get-release-medias [id]
  (let [medias (db/get-release-medias {:id id})]
    (map #(assoc %
            :tracks (db/get-media-tracks {:id (:id %)}))
         medias)))

(defn get-release-image [id]
  (ByteArrayInputStream. (db/get-release-image id)))
