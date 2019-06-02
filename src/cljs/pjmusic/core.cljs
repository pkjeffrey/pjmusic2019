(ns pjmusic.core
  (:require [cljsjs.jquery-ui :as jqui]))

(defn init-artist-search-autocomplete []
  (.autocomplete (js/$ "#artist-search")
                 (clj->js {:source "/api/artists"
                           :minLength 2
                           :select (fn [_ ui]
                                     (js/window.location.assign (str "/artist/" js/ui.item.id)))
                           :autoFocus true})))

(defn ^:export init-navbar []
  (init-artist-search-autocomplete))