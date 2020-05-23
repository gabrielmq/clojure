(ns financeiro.transacoes)

(defn valida? [transacao]
  (and (contains? transacao :valor)
       (number? (:valor transacao))
       (pos? (:valor transacao))
       (contains? transacao :tipo)
       (or (= "receita" (:tipo transacao))
           (= "despesa" (:tipo transacao)))))