# Conversor

Este projeto é uma aplicação de linha de comando desenvolvida em Clojure que converte o valor de uma moeda em outra, por exemplo de real para dolar e vice e verça.

Para realizar a converção a aplicação consulta a api [currency converter](https://free.currencyconverterapi.com/api/v6/convert) para obter a cotação atual da moeda a ser convertida.

Este projeto foi desenvolvido durante a leitura do livro _Programação Funcional: Uma introdução em Clojure_

## Pré requisitos

- Java 8+
- Leiningen
- Ter uma apiKey para o concurrency converter, clique [aqui](https://free.currencyconverterapi.com/free-api-key) para gerar uma.

## Executando a aplicação

- Dentro da pasta do projeto, execute o comando abaixo no terminal:

```
> API_KEY=<valor da chave gerada> lein run --de=USD --para=BRL
;; exemplo de retorno
;; "1 USD equivale a 5.53 BRL"
```