(ns conversor.core
  (:require [conversor.formatador :refer [formatar]]
            [conversor.interpretador-de-opcoes :refer [interpretar-opcoes]]
            [conversor.cambista :refer [obter-cotacao]]))

;; funções declaradas como defn- são visiveis apenas para o namespace onde elas foram definidas
(defn- valores-em [argumento]
  (cond
    (.startsWith argumento "--de=") {:de (.substring argumento 5)}
    (.startsWith argumento "--para=") {:para (.substring argumento 7)}
    :else {}))

;; nome de função começando com -, indica que é uma função estatica
(defn -main
  ;; & indica uma quantidade indefinida de argumentos, tipo um varargs do java
  [& args]
  ;; faz um destructuring para obter "de para" das opções de linha de comando
  (let [{de :de para :para} (interpretar-opcoes args)]
    (->
      (obter-cotacao de para)
      (formatar de para)
      (prn))))
