(ns sample.handler
  (:use 
        [compojure.core :only (defroutes GET POST PUT)]
        [hiccup.page :only [html5]]
        [compojure.handler :only [api]]
        [ring.middleware.multipart-params :only [wrap-multipart-params]]
        [ring.middleware.session.memory :only [memory-store]]
        [ring.middleware.reload :only (wrap-reload)])
  (:require [ring.adapter.jetty :as ring]
      			[compojure.handler :as handler]
            [compojure.route :as route]
            [ayah.client :as ayah]))

(def AYAH_PUBLISHER_KEY "00000")
(def AYAH_SCORING_KEY   "11111")

(defn get-page-html
  [show-form message params]
  (html5
    [:head
      [:title "Are You A Human example"]
    ]
    [:body
      [:h1 "Are You A Human Example"]
      [:div message]
      (if show-form
        [:form {:method "POST"}
          [:div "First Name:" [:input {:name "firstname" :id "firstname"}]]
          [:div "Last Name:" [:input {:name "lastname" :id "lastname"}]]
          [:div (ayah/get-publisher-html AYAH_PUBLISHER_KEY)]
          [:input {:type "submit" :value "Submit"}]
        ]
        [:h2 "Thank you for submitting the form " (params :firstname) "."])
    ]))


(defroutes home-routes
  (GET "/" []
    (get-page-html true "" {}))
  (POST "/" [:as {params :params}]
    (let [score (ayah/score-result AYAH_SCORING_KEY (params :session_secret))]
      (get-page-html (not score) (if score "You are a human!" "Try again.") params))))

(defroutes app-routes
  home-routes
  ; (route/resources "/")
  (route/not-found "Not Found"))

; http://mmcgrana.github.com/2010/07/develop-deploy-clojure-web-applications.html
(defn wrap-if [handler pred wrapper & args]
  (if pred
      (apply wrapper handler args)
      handler))

(def app (-> #'app-routes
      api
      wrap-multipart-params
      (wrap-if true ; development? 
               wrap-reload {:dirs ["src"]})))

(defn start [port]
  (ring/run-jetty app {:port port :join? false}))

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (start port)))
