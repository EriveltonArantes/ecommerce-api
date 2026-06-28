# Ecommerce Api

API REST Spring Boot gerada automaticamente por FabricaIA.

## Pré-requisitos

- Java 21+
- Maven 3.9+

## Rodar localmente

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Documentação interativa (Swagger UI)

Acesse: `http://localhost:8080/swagger-ui/index.html`

## Quick Start (curl)

```bash
# 1. Obter token
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' | python -c "import sys,json; print(json.load(sys.stdin)['token'])")

# 2. Listar registros
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/carrinhos

# 3. Resumo dashboard
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/dashboard/resumo
```

## Endpoints gerados

## Autenticação JWT

| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/api/auth/registrar` | POST | Cria conta com username/password |
| `/api/auth/login` | POST | Retorna token JWT |

**Admin padrão** (criado automaticamente na primeira execução):
- Username: `admin` (ou env var `ADMIN_USERNAME`)
- Password: `admin123` (ou env var `ADMIN_PASSWORD`)

Para endpoints protegidos, adicione o header: `Authorization: Bearer <token>`

| Endpoint | Métodos | Entidade |
|----------|---------|---------|
| `/api/carrinhos` | GET / POST / PUT / DELETE | CRUD de Carrinho |
| `/api/clientes` | GET / POST / PUT / DELETE | CRUD de Cliente |
| `/api/cupoms` | GET / POST / PUT / DELETE | CRUD de Cupom |
| `/api/itemcarrinhos` | GET / POST / PUT / DELETE | CRUD de ItemCarrinho |
| `/api/itempedidovendas` | GET / POST / PUT / DELETE | CRUD de ItemPedidoVenda |
| `/api/pedidos` | GET / POST / PUT / DELETE | CRUD de Pedido |
| `/api/pedidovendas` | GET / POST / PUT / DELETE | CRUD de PedidoVenda |
| `/api/produtos` | GET / POST / PUT / DELETE | CRUD de Produto |
| `/api/usuarios` | GET / POST / PUT / DELETE | CRUD de Usuario |
| `/api/dashboard/resumo` | GET | KPIs e totais |

## Deploy no Render

1. Faça push para o GitHub
2. Conecte o repositório no [Render](https://render.com)
3. O `render.yaml` configura o serviço automaticamente

**Variáveis de ambiente para produção:**

| Variável | Descrição |
|----------|-----------|
| `DATABASE_URL` | URL JDBC do PostgreSQL (ex: `jdbc:postgresql://host:5432/db`) |
| `DATABASE_USERNAME` | Usuário do banco |
| `DATABASE_PASSWORD` | Senha do banco |
| `ADMIN_USERNAME` | Username do admin (padrão: admin) |
| `ADMIN_PASSWORD` | Senha do admin (padrão: admin123) |

## Console H2 (apenas desenvolvimento)

Acesse `http://localhost:8080/h2-console` com JDBC URL `jdbc:h2:mem:testdb`.
