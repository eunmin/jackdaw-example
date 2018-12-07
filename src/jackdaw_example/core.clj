(ns jackdaw-example.core
  (:require [jackdaw.serdes.edn :as jse]
            [jackdaw.streams :as j]))

(defn topic-config [topic-name]
  {:topic-name topic-name
   :partition-count 1
   :replication-factor 1
   :key-serde (jse/serde)
   :value-serde (jse/serde)})

(defn app-config []
  {"application.id" "word-count"
   "bootstrap.servers" "localhost:9092"
   "cache.max.bytes.buffering" "0"})

(defn build-topology [builder]
  (-> (j/kstream builder (topic-config "input"))
      (j/peek (fn [[k v]]
                (println (str {:key k :value v}))))
      (j/to (topic-config "output")))
  builder)

(defn start-app [app-config]
  (let [builder (j/streams-builder)
        topology (build-topology builder)
        app (j/kafka-streams topology app-config)]
    (j/start app)
    (println "pipe is up")
    app))

(defn stop-app [app]
  (j/close app)
  (println "pipe is down"))

(defn -main [& args]
  (start-app (app-config)))
