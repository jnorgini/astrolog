# 🌌 Astrolog — Cálculo de Mapa Astral e Trânsitos

Aplicação full-stack para cálculo preciso de posições planetárias, casas astrológicas (sistema Placidus), Ascendente, retrogradação e fases da Lua, construída com **Java + Spring Boot (Arquitetura Hexagonal)** no back-end e **Angular + Tailwind CSS** no front-end.

---

## ✨ Funcionalidades

- **Cálculo Astronômico de Alta Precisão:** Integração com a biblioteca **Swiss Ephemeris (`swisseph`)** para cálculo de Sol, Lua, planetas pessoais/geracionais, Nódulo Verdadeiro, Quíron e Lilith.
- **Casas e Ascendente:** Cálculo das 12 cúspides (Placidus) e determinação automática da casa de cada astro, com suporte a opção de *"Hora de nascimento desconhecida"*.
- **Fase Lunar e Movimento:** Identificação automática da fase da Lua no momento do mapa (Nova, Crescente, Cheia, Minguante) e detecção de movimento direto ou retrógrado.
- **Autocompletar de Localização:** Busca de cidades e obtenção automática de latitude/longitude via integração com a API **Photon (OpenStreetMap)**.

---

## 🏛️ Arquitetura do Projeto

O repositório está organizado como um monorepo contendo `backend/` e `frontend/`:

### Back-end (`backend/`) — Arquitetura Hexagonal (Ports & Adapters)
O núcleo da aplicação foi desenhado para isolar completamente as regras e modelos astrológicos de frameworks externos (Spring) e bibliotecas de terceiros (`swisseph`, clientes HTTP):

```text
src/main/java/com/norgini
├── domain/                  # Núcleo puro (Zero dependências de frameworks)
│   ├── model/               # Entidades de domínio, Enums e regras de cálculo de casas/fases
│   ├── exception/           # Exceções de domínio
│   └── port/
│       ├── in/              # Casos de uso (CalculateBirthChartUseCase, SearchLocationUseCase)
│       └── out/             # Contratos externos (AstrologyCalculatorPort, GeocodingPort)
│
├── application/             # Orquestração dos casos de uso
│   └── service/             # Implementação dos UseCases (AstrologApplicationService)
│
└── infrastructure/          # Adaptadores e configurações de framework
    ├── adapter/
    │   ├── in/web/          # Controllers REST, DTOs, WebMappers e GlobalExceptionHandler
    │   └── out/
    │       ├── ephemeris/   # Adaptador da Swiss Ephemeris (SwissEphAstrologyAdapter)
    │       └── geocoding/   # Adaptador de geolocalização (PhotonGeocodingAdapter)
    └── config/              # Injeção de dependências (@Bean) e configuração de CORS
```

### Front-end (`frontend/`)
- Desenvolvido com **Angular** (utilizando **Signals** e **RxJS** com `debounceTime` / `switchMap` para busca otimizada de locais).
- Estilizado com **Tailwind CSS** com layout responsivo.

---

## 🚀 Como Rodar Localmente

### Pré-requisitos
- **Java 17+** e **Maven**
- **Node.js 18+** e **Angular CLI**

### 1. Subindo o Back-end
```bash
cd backend
./mvnw spring-boot:run
```
A API estará disponível em `http://localhost:8080`.

### 2. Subindo o Front-end
```bash
cd frontend
npm install
ng serve
```
Acesse a aplicação em `http://localhost:4200`.

---

## 📡 Endpoints da API

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/astrolog/planets` | Calcula posições planetárias, casas e Ascendente. Params: `day`, `month`, `year`, `hour`, `minute`, `location`. |
| `GET` | `/astrolog/locations` | Retorna sugestões de cidades para autocompletar. Param: `query`. |
| `GET` | `/astrolog/ping` | Health check da aplicação. |

---

## 📄 Licença

Este projeto está sob a licença descrita no arquivo [LICENSE](LICENSE).
