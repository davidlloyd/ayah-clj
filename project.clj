(defproject ayah-clj "0.7.0"
  :description "Clojure sample for using Are You A Human test."
  :url "http://www.touchsoftware.cc/"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.1.8"]
                 [ring/ring-core "1.1.8"]
                 [hiccup "1.0.3"]
                 ; [enlive/enlive "1.1.1"]
                 ; [lib-noir "0.5.1"]
                 ; [markdown-clj "0.9.20"]
                 ; [clj-time "0.4.4"]
                 ;[jayq "2.3.0"]	
                 [org.clojure/data.json "0.2.2"] 
            		 [clj-http "0.7.6"]
                ]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler sample.handler/app :auto-reload true}
  :main sample.handler
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
