# Financeiro

Este projeto é uma API desenvolvida em Clojure que é um controle financeiro pessoal, onde é possível cadastrar despesas e receitas e consultar o saldo.

Este projeto foi desenvolvido durante a leitura do livro _Programação Funcional: Uma introdução em Clojure_

## Pré requisitos

- Java 8+
- Leiningen

## Executando a aplicação

- Dentro da pasta do projeto, execute o comando `lein ring server-headless` terminal para iniciar o servidor;
    - a aplicação será inicializada na porta 3000;

## Endpoints

### GET

- /transacoes [ retorna todas as transações ]

```
{
    "transacoes": [
        {"valor": 20, "tipo": "despesa", "rotulos": ["livro" "educação"]},
        {"valor": 200, "tipo": "despesa", "rotulos": ["curso" "educação"]},
        {"valor": 2000, "tipo": "receita", "rotulos": ["salario"]}
    ]
}
```

- /transacoes?rotulos=salário [ retornas as transações por rótulos ]

```
{
    "transacoes": [
        {"valor": 2000, "tipo": "receita", "rotulos": ["salario"]}
    ]
}
```

- /despesas [ retorna apenas as transações que são do tipo 'despesa' ]

```
{
    "transacoes": [
        {"valor": 20, "tipo": "despesa", "rotulos": ["livro" "educação"]},
        {"valor": 200, "tipo": "despesa", "rotulos": ["curso" "educação"]}
    ]
}
```

- /receitas [ retorna apenas as transações que são do tipo 'receita' ]

```
{
    "transacoes": [
        {"valor": 2000, "tipo": "receita", "rotulos": ["salario"]}
    ]
}
```

- /saldo [ retorna o saldo das transações ]

```
{
    "saldo": 1780
}
```

### POST

- /transacoes [ adiciona uma nova transação ]

```
;; exemplo de body
{"valor": 20, "tipo": "despesa", "rotulos": ["livro" "educação"]}
```