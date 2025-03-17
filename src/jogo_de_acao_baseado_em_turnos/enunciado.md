# Lista Prática 02

## Introdução
Deseja-se elaborar um Sistema de Controle para um Jogo de Ação Baseada em Turnos. Tal sistema deverá ser implementado em uma linguagem de programação real, seguindo as especificações abaixo.

---

## 1. Preliminares
1. O Sistema de Controle deverá suportar exatamente **dois times de lutadores rivais**.
2. Cada time pode ter um **número diferente de lutadores**, que pode aumentar ou diminuir durante o combate.
3. **Não há limite** para o número de lutadores em cada time.
4. Cada lutador possui:
    - Identificador único
    - Time ao qual pertence
    - Valor de dano
    - Número de pontos de vida
    - Valor base de iniciativa (**1 a 100**)
5. O jogo será realizado em **turnos**.
6. Cada turno terá **três etapas**:
    - **Organização dos Times**
    - **Combate**
    - **Resultados**
7. Um lutador é **considerado vivo** se seus pontos de vida forem **maiores que 0**.
8. O jogo termina quando **um dos times não tem mais lutadores vivos**.

---

## 2. Funcionamento
O Sistema de Controle oferecerá um **menu interativo**, permitindo que o usuário execute ações ao longo do jogo.

### 2.1. Fase de Organização dos Times
Nesta etapa, o jogo é inicializado e o usuário pode realizar as seguintes ações:

1. **Inserir lutadores em times**:
    - Criar um novo lutador e atribuir a um time.
    - **Identificadores devem ser únicos**, não podendo se repetir entre times ou em listas de lutadores mortos.
    - Se houver tentativa de inserir um identificador já existente, o sistema deve exibir uma **mensagem de erro**.

2. **Consultar o status de um time**:
    - O usuário pode verificar a situação dos lutadores vivos e mortos.
    - A lista de **lutadores vivos** deve ser exibida em **ordem decrescente de iniciativa**.
    - A lista de **lutadores mortos** também deve ser organizada por iniciativa.

3. **Remover um lutador do combate (fuga)**:
    - O usuário pode remover um lutador vivo fornecendo seu identificador.

Após essas ações, cada time é organizado em uma **Fila individual**, ordenada pela iniciativa dos lutadores. Em seguida, a fase de combate inicia.

### 2.2. Fase de Combate
O combate ocorre automaticamente, seguindo os passos:

1. Os primeiros lutadores das filas de cada time se enfrentam:
    - São removidos da fila.
    - Se estiverem aptos, atacam simultaneamente, causando dano ao oponente.
    - O dano é subtraído dos pontos de vida do lutador atacado.
    - Se algum lutador chegar a **0 pontos de vida ou menos**, ele é considerado **morto**.
    - Lutadores vivos retornam à fila; os mortos são enviados ao **Cemitério do time**.

2. O processo é repetido até que todos os lutadores tenham atacado uma vez ou até que um dos times fique sem lutadores vivos.

### 2.3. Fase de Resultados
No final de cada combate:
1. O **score** de cada time é calculado como a quantidade de lutadores mortos do time adversário.
2. Avaliam-se as **condições de término**:
    - Se **apenas um time** tem lutadores vivos, ele vence e o jogo termina.
    - Se ambos os times possuem lutadores vivos, mas um deles alcançou **score >= 20**, esse time é declarado vencedor.
    - Se ambos os times alcançaram **score > 20**, o que tiver maior score vence.
    - Se ambos os times ficaram vazios, vence o que tiver maior score; se os scores forem iguais, o jogo termina em empate.
    - Caso nenhuma das condições acima seja atendida, inicia-se um novo turno.

---

## 3. Implementação
Todas as **Estruturas de Dados e Algoritmos** devem ser implementados conforme especificado.

Estruturas recomendadas:
- **Filas** para organização dos times.
- **Listas ordenadas** para os cemitérios.

O jogo deve ser capaz de gerenciar dinamicamente os lutadores e seguir as regras acima para garantir um combate justo e funcional.

---

*FIM DA LISTA PRÁTICA 02*

