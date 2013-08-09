(ns ayah.client
  (require [clojure.data.json :as json]
           [clj-http.client :as http])
  (:import (java.net URLEncoder)
))

"
Author: david lloyd, http://www.touchsoftware.cc, 
Copyright 2013, All rights reserved.
Licensed under Creative Commons BY-ND http://creativecommons.org/licenses/by-nd/3.0/legalcode

  add [org.clojure/data.json "0.2.2"] [clj-http "0.7.6"] to Leiningen 
"

(def WSHOST "ws.areyouahuman.com")

(defn get-publisher-html
  "Sets the publisher key and scoring key needed to make subsequent calls to the areyouahuman API. Call this function
    once when your application initializes.

    publisher-key
        Identifies you and your application to areyouahuman.com.
    ws-host (optional)
        Web service host for areyouahuman calls (no trailing slash). Defaults to 'ws.areyouahuman.com'.
  "
  ([publisher-key] 
    (get-publisher-html publisher-key WSHOST))
  ([publisher-key ws-host]
    (str 
      "<div id=\"AYAH\"></div><script type=\"text/javascript\" src=\"https://"
      ws-host,
      "/ws/script/"
      (URLEncoder/encode publisher-key "UTF-8")
      "\"></script>")))

(defn score-result
  "Check whether the user is a human. True means the user passed the test, i.e. Is  A Human

    scoring-key
        Used to retrieve pass or fail results from areyouahuman.com.
    session-secret
        Value from the 'session_secret' hidden input parameter.
    ws-host (optional)
        Web service host for areyouahuman calls (no trailing slash). Defaults to 'ws.areyouahuman.com'.
  "
  ([scoring-key session-secret] 
    (score-result scoring-key session-secret WSHOST))
  ([scoring-key session-secret ws-host]
    (let [server-result (http/post (str "https://" ws-host "/ws/scoreGame") {:form-params {:session_secret session-secret :scoring_key scoring-key}})          
          result-map (json/read-str (server-result :body) :key-fn keyword)]
      (= (result-map :status_code) 1))))

(defn record-conversion
  "Records a conversion
   Called on the goal page that A and B redirect to
   A/B Testing Specific Function"
  ([session-secret]
    (record-conversion session-secret WSHOST))
  ([session-secret ws-host]
    (if session-secret (str "<iframe style=\"border: none;\" height=\"0\" width=\"0\" src=\"https://" ws-host "/ws/recordConversion/" 
                            (URLEncoder/encode session-secret "UTF-8") "\"></iframe>"))))

