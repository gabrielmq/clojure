(ns financeiro.saldo-aceitacao-test
  (:require [midje.sweet :refer :all]
            [cheshire.core :as json]
            [clj-http.client :as http-client]
            [financeiro.db :as db]
            [financeiro.auxiliares :refer :all]))

(against-background [(before :facts [(iniciar-servidor porta-padrao) (db/limpar)])
                     (after :facts (parar-servidor))]
  (fact "O saldo inicial é 0" :aceitacao
        (json/parse-string (conteudo "/saldo") true) => {:saldo 0})

  (fact "O saldo é 10 quando a única transação é uma receita de 10" :aceitacao
        (http-client/post (endereco-para "/transacoes")
                          {:content-type :json
                           :body (json/generate-string {:valor 10 :tipo "receita"})})
        (json/parse-string (conteudo "/saldo") true) => {:saldo 10})

  (fact "O saldo é 1000 quando criamos duas receitas de 2000 e uma despesa de 3000" :aceitacao
        (http-client/post (endereco-para "/transacoes") (receita 2000))
        (http-client/post (endereco-para "/transacoes") (receita 2000))
        (http-client/post (endereco-para "/transacoes") (despesa 3000))
        (json/parse-string (conteudo "/saldo") true) => {:saldo 1000})

  (fact "Rejeita uma transação sem valor" :aceitacao
        (let [response (http-client/post (endereco-para "/transacoes")
                                         (conteudo-como-json {:tipo "receita"}))]
          (:status response) => 422))

  (fact "Rejeita uma transação com valor negativo" :aceitacao
        (let [response (http-client/post (endereco-para "/transacoes")
                                         (receita -100))]
          (:status response) => 422))

  (fact "Rejeita uma transação com valor que não é um número" :aceitacao
        (let [response (http-client/post (endereco-para "/transacoes")
                                         (receita "mil"))]
          (:status response) => 422))

  (fact "Rejeita uma transação sem tipo" :aceitacao
        (let [response (http-client/post (endereco-para "/transacoes")
                                         (conteudo-como-json {:valor 70}))]
          (:status response) => 422))

  (fact "Rejeita uma transação com tipo desconhecido" :aceitacao
        (let [response (http-client/post (endereco-para "/transacoes")
                                         (conteudo-como-json {:valor 70 :tipo "investimento"}))]
          (:status response) => 422)))