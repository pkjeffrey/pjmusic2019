(ns pjmusic.core)

(defn mount-components []
  (let [content (js/document.getElementById "app")]
    (while (.hasChildNodes content)
      (.removeChild content (.-lastChild content)))
    (.appendChild content (js/document.createTextNode "Welcome to pjmusic"))))

(defn init! []
  (mount-components))
