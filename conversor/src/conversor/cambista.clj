(ns conversor.cambista
  (:require [clj-http.client :as http-client]
            [cheshire.core :refer [parse-string]]))

;; definição da chave para acessa a api free concurrency converter
(def api-key (System/getenv "API_KEY"))
(def api-url "https://free.currencyconverterapi.com/api/v6/convert")

(defn parametrizar-moedas [de para]
  (str de "_" para))

(defn obter-cotacao [de para]
  (let [moedas (parametrizar-moedas de para)]
    (->
      (:body (http-client/get api-url {:query-params {"q" moedas "apiKey" api-key}}))
      (parse-string)
      (get-in ["results" moedas "val"]))))
