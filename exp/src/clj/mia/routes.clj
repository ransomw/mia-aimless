(ns mia.routes
  (:require
   [clojure.java.io :as io]
   [compojure.core
    :refer [ANY GET PUT POST DELETE
            routes context]]
   [compojure.route :refer [resources]]
   [ring.util.response :refer [response]]
   ))

(def html-headers
  {"Content-Type" "text/html; charset=utf-8"})

(defn make-edn-resp
  ([data] (make-edn-resp data 200))
  ([data status]
   {:status status
    :headers {"Content-Type" "application/edn"}
    :body (pr-str data)}))

(defn api-routes [{db :db}]
  (routes
   (context
    "/" {{exp-id :exp-id} :session}
    (POST
     "/exp" _
     (-> (make-edn-resp {})
         (update-in [:session :exp-id]
                    1 ;; todo: db
                    )
         )
     )
    )))

(defn home-routes [{db :db :as endpoint}]
  (routes
   (GET "/" _
     (-> "public/index.html"
         io/resource
         io/input-stream
         response
         (assoc :headers html-headers)))
   (context
    "/api" _
    (api-routes {:db db}))
   (resources "/")))

(defn sente-handler [{db :db}]
  (fn [{:keys [event id ?data send-fn ?reply-fn uid ring-req client-id]
        :as ev-msg}]
    (let [session (:session ring-req)
          headers (:headers ring-req)
          [id data :as ev] event]

      (println "top sente-handler")
      (println (:uri ring-req) event uid)

      )))
