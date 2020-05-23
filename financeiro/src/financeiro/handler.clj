(ns financeiro.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [financeiro.db :as db]
            [financeiro.transacoes :as transacoes]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defn como-json [conteudo & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (json/generate-string conteudo)})

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/saldo" [] (como-json {:saldo (db/saldo)}))

  (GET "/transacoes" {filtros :params}
    (como-json {:transacoes
      (if (empty? filtros)
        (db/transacoes)
        (db/transacoes-com-filtro filtros))}))

  (GET "/receitas" request []
    (como-json {:transacoes (db/transacoes-do-tipo "receita")}))

  (GET "/despesas" request []
    (como-json {:transacoes (db/transacoes-do-tipo "despesa")}))

  (POST "/transacoes" request
    (if (transacoes/valida? (:body request))
      (-> (db/registrar (:body request))
        (como-json 201))
      (como-json {:mensagem "Requisição inválida"} 422)))

  (route/not-found "Not Found"))

(def app
  (->
    (wrap-defaults app-routes api-defaults)
    (wrap-json-body {:keywords? true :bigdecimals? true})))
