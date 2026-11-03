# Requisitos do Corneteiro

Documento vivo. Registra o que a aplicação deve fazer (funcionais), como deve se comportar
(não funcionais), como medimos isso e em que ordem entregamos.

**Objetivo do projeto:** aprendizado e portfólio. Escala pequena, foco em boas práticas e
arquitetura demonstrável.

**Fora do escopo por enquanto:** usuários, login e times salvos.

---

## 1. O que a API da Cartola já entrega (verificado)

| Endpoint | Conteúdo |
|----------|----------|
| `/atletas/mercado` | Atletas com `pontos_num` (última rodada), `media_num`, `preco_num`, `variacao_num`, `jogos_num`, `status_id`, `posicao_id`, `clube_id` e `scout`. Também traz os nomes de posições e status |
| `/mercado/status` | `rodada_atual`, `status_mercado` e `cartoleta_inicial` |
| `/rodadas` | As 38 rodadas, com início e fim |
| `/atletas/pontuados/{rodada}` | Pontuação e scout de qualquer rodada já disputada |

Limites conhecidos:
- O histórico de **preço** não existe na API; só há a `variacao_num` da última rodada.
  O histórico de preços depende dos nossos snapshots.
- O campo `craque` do mercado veio `0` para todos os atletas e **não deve ser usado**.
- Rodadas encerradas não mudam, então seus dados podem ser guardados sem expiração.

---

## 2. Requisitos funcionais

| ID | Requisito | Prioridade | Depende de banco |
|----|-----------|:----------:|:----------------:|
| RF01 | Listar e buscar atletas e clubes | Feito | Não |
| RF02 | **Custo-benefício:** ranking de atletas por `média ÷ preço` | Alta | Não |
| RF02.1 | Filtrar por **posição** (`posicaoId`) | Alta | Não |
| RF02.2 | Filtrar por **clube** (`clubeId`) | Alta | Não |
| RF02.3 | **Mínimo de jogos** configurável por requisição; padrão **3** | Alta | Não |
| RF02.4 | Excluir atletas com preço 0 e filtrar por status (padrão: `Provável`) | Alta | Não |
| RF02.5 | **Ranking de clubes** pela média do custo-benefício dos atletas elegíveis | Alta | Não |
| RF03 | **Craque da rodada:** maiores pontuadores, geral e por posição, ordenando por `pontos_num` (ou `pontuados/{rodada}` para uma rodada específica) | Média | Não |
| RF04 | Cada recomendação traz o **motivo** (valores usados no cálculo) | Alta | Não |
| RF05 | Guardar no banco o histórico da temporada: carga das rodadas já jogadas (rodada 1 em diante) e snapshot de preços a partir de agora | Média | Sim |
| RF06 | **Forma recente:** pontos nas últimas N rodadas, com **janela N ajustável pelo usuário** no front | Baixa/Média | Opcional |
| RF07 | **Tendência de preço** ao longo das rodadas | Baixa/Média | Sim |
| RF08 | Servir o último dado salvo quando a API da Cartola estiver indisponível | Média | Sim |
| RF09 | **Time sugerido** (formação e orçamento escolhidos pelo usuário) | Baixa (futuro) | Sim |

Regras de negócio:
- Custo-benefício usa a **média** (`media_num`), e não a pontuação de uma única rodada.
- Atletas com menos de `minJogos` jogos não entram no ranking.
- A janela N da forma recente tem limite (mínimo 1, máximo igual ao número de rodadas
  disputadas); valores fora disso retornam erro 400.

Contrato proposto (a validar na implementação):

```
GET /recomendacoes/custo-beneficio?posicaoId=&clubeId=&minJogos=3&status=7&limite=20   (implementado)
GET /recomendacoes/custo-beneficio/clubes?posicaoId=&minJogos=3&status=7&limite=20     (implementado)
GET /recomendacoes/craques?posicaoId=&rodada=&limite=10
GET /recomendacoes/forma?rodadas=3&posicaoId=&clubeId=
```

---

## 3. Requisitos não funcionais

| ID | Categoria | Requisito |
|----|-----------|-----------|
| RNF01 | Desempenho | Endpoints de recomendação respondem em **p95 < 200 ms** com cache aquecido |
| RNF02 | Desempenho | Primeira chamada com cache frio: **p95 < 1 s** |
| RNF03 | Resiliência | Se a Cartola falhar, a API continua respondendo com dados salvos (degradação graciosa) |
| RNF04 | Consistência | Todo dado servido informa a idade (`capturado_em`) |
| RNF05 | Idempotência | Repetir a carga ou o snapshot da mesma rodada não duplica nem corrompe dados |
| RNF06 | Observabilidade | Log da exceção real e métricas via Actuator: chamadas à Cartola, acertos de cache, duração dos jobs |
| RNF07 | Qualidade | Cobertura de testes **≥ 70%** nas regras de cálculo; testes de integração para cache e jobs |
| RNF08 | Manutenibilidade | Esquema do banco versionado (Flyway); configuração por perfis (`dev`, `docker`) |
| RNF09 | Portabilidade | `docker compose up` sobe backend, frontend e banco em máquina limpa |
| RNF10 | Documentação | Todo endpoint documentado no Swagger; README atualizado a cada mudança |
| RNF11 | Segurança básica | Sem segredos no repositório; CORS restrito às origens conhecidas |

---

## 4. Métricas

| Métrica | Como medir | Meta |
|---------|-----------|------|
| Latência p95 dos endpoints | Micrometer/Actuator | < 200 ms com cache |
| Taxa de acerto do cache | Métrica do cache | > 90% |
| Chamadas à Cartola por hora | Contador no `CartolaApiClient` | ≤ 12 |
| Sucesso dos jobs de carga/snapshot | Contador de sucesso e falha | 100% por rodada |
| Cobertura de testes | JaCoCo | ≥ 70% nas regras de cálculo |
| Tempo de `docker compose up` | Medição manual | < 3 min |

---

## 5. Ordem de entrega

1. **Custo-benefício** por posição e clube, e ranking de clubes, sem banco (RF02, RF04) — **implementado**.
2. **Craque da rodada** (RF03).
3. **Banco** (Postgres, Flyway) com carga das rodadas passadas e snapshot de preços (RF05).
4. **Redis** para cachear os rankings calculados.
5. **Job automático** de snapshot por rodada.
6. **Forma recente** com janela ajustável e **tendência de preço** (RF06, RF07).
7. **Time sugerido**, junto com o trabalho no front (RF09).

O Redis só entra depois do banco, e deve ser avaliado pelas métricas de acerto de cache.

---

## 6. Decisões e pontos em aberto

Decididas:
- Mínimo de jogos é 3 e configurável.
- Segmentação por clube e por posição.
- O banco guarda a temporada desde a rodada 1.
- Usuários e login ficam fora do escopo.
- Event sourcing não é necessário: os dados já são snapshots imutáveis por rodada.

Decididas também:
- O ranking agregado de clubes faz parte do escopo (RF02.5).
- Padrão de status: `Provável`, com opção de incluir os demais.
- Limite máximo da janela de forma recente: 10 rodadas.

Resolvido:
- O campo de pontuação da Cartola é `pontos_num`; o `AtletaDTO` e o front já usam esse nome
  (antes lia `ponto_num` e a pontuação chegava sempre nula). Coberto por `AtletaDTOJsonTest`.

Em aberto:
- Nenhum no momento.
