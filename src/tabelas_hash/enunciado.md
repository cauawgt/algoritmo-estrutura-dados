Escreva, usando uma Linguagem de Programação, as estruturas de dados básicas e o algoritmo
de inserção em uma Tabela Hash de tamanho m (parâmetro fornecido como entrada para o
algoritmo), que executa a estratégia de **Open Address Hashing por Linear Probing** enquanto
houver espaço disponível na tabela. Quando a tabela estiver completamente preenchida, o
algoritmo deve executar a estratégia de **Closed Address Hashing**. Escreva o código para as
Estruturas de Dados necessárias. Considere a hash function abaixo para uma chave k
qualquer:

ℎ(𝑘) = 𝑘 𝑚𝑜𝑑 𝑚

OBS.: A Tabela Hash propriamente dita deve ser implementada como um Array estático,
enquanto as listas usadas pela estratégia de Closed Addrees Hashing, devem ser implementadas
como Listas Lineares Encadeadas (alocação dinâmica).