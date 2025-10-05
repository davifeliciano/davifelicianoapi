# davifelicianoapi

## Visão Geral

Esta aplicação é uma API RESTful desenvolvida em Java com Spring Boot, focada na
gestão de boletos bancários, pagamentos e encargos. Ela oferece funcionalidades
para cadastro, consulta, atualização e exclusão de boletos, pagamentos e
encargos, além de integração com uma API externa para consulta de feriados
nacionais do Brasil para prorrogação de vencimentos, caso esse ocorra em dia não
útil.

## Funcionalidades Principais

- **Gestão de Boletos:** Cadastro, consulta, atualização e exclusão de boletos bancários, com associação a cedente e sacado (pessoas físicas ou jurídicas).
- **Pagamentos:** Registro de pagamentos de boletos, com cálculo automático de encargos e validação de datas.
- **Encargos:** Cálculo e gerenciamento de encargos relacionados a boletos em atraso.
- **Feriados:** Consulta de feriados nacionais via integração com a [BrasilAPI](https://brasilapi.com.br/).
- **Segurança:** Autenticação básica HTTP com controle de acesso por papel (ADMIN, USER).
- **Validações:** Diversas validações de dados, como formatos de datas, CPF/CNPJ, valores e regras de negócio.

## Endpoints Implementados

Para mais detalhes sobre o uso de cada endpoint, o arquivo
`davifelicianoapi.postman_collection.json` pode ser importado no Postman.

### Boletos

- `GET /api/boletos`
  Lista todos os boletos cadastrados.

- `GET /api/boletos/{id}`
  Consulta um boleto pelo seu ID.

- `POST /api/boletos`
  Cria um novo boleto.

- `PUT /api/boletos/{id}`
  Atualiza um boleto existente.

- `DELETE /api/boletos/{id}`
  Remove um boleto pelo ID.

- `PATCH /api/boletos/{id}/pagar`
  Marca um boleto como pago

- `GET /api/boletos/{id}/pagamentos`
  Lista os pagamentos de um boleto

- `GET /api/boletos/{id}/encargos`
  Realiza o calulo de encargos projetados até a
  data atual, com base nas informações de encargos (juros e multa) cadastradas
  para o boleto (via `POST /api/encargos`)

### Pagamentos

- `GET /api/pagamentos`
  Lista todos os pagamentos registrados.

- `GET /api/pagamentos/{id}`
  Consulta um pagamento pelo ID.

- `POST /api/pagamentos`
  Registra um novo pagamento para um boleto existente.

- `DELETE /api/pagamentos/{id}`
  Remove um pagamento pelo ID.

### Encargos

- `GET /api/encargos`
  Lista todos os encargos cadastrados.

- `GET /api/encargos/{id}`
  Consulta um encargo pelo ID.

- `POST /api/encargos`
  Cria um novo encargo associado a um boleto.

- `PUT /api/encargos/{id}`
  Atualiza um encargo existente.

- `DELETE /api/encargos/{id}`
  Remove um encargo pelo ID.

### Feriados

- `GET /api/feriados/{ano}`
  Consulta os feriados nacionais do Brasil para o ano informado (integração com BrasilAPI).
