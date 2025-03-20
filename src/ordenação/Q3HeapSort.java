package ordenação;

import java.util.Scanner;

public class Q3HeapSort {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int[] vetor;
        int M = 0;

        boolean parar = false;
        while (!parar) {
            boolean verificacaoTamanho = false;
            while (!verificacaoTamanho) {
                System.out.print("TAMANHO DO VETOR: ");
                M = input.nextInt();
                if (M > 0) {
                    verificacaoTamanho = true;
                } else {
                    System.out.println("Tamanho inválido! Tente novamente.");
                }
            }

            vetor = new int[M];

            System.out.println();

            for (int i = 0; i < M; i++) {
                System.out.print("ELEMENTO (" + i + ")\n >> ");
                vetor[i] = input.nextInt();
            }

            System.out.println();

            System.out.println("(1) - ORDENAR || (2) - REFAZER: ");
            int decisao = input.nextInt();

            if (decisao == 1) {
                System.out.println("\n--- IMPRIMINDO VETOR ORIGINAL ---");
                for (int i = 0; i < M; i++) {
                    System.out.print(vetor[i] + " ");
                }
                System.out.println("\n--- ORDENANDO VETOR ---");

                Heap heap = new Heap();
                vetor = heap.heapsort(vetor, M - 1);

                System.out.println("\n--- VETOR ORDENADO ---");
                for (int i = 0; i < M; i++) {
                    System.out.print(vetor[i] + " ");
                }

                System.out.println("\n\n(1) - FINALIZAR || (2) - REFAZER: ");
                decisao = input.nextInt();
                if (decisao == 1) {
                    parar = true;
                }
            }

        }
    }
}

class Heap {
    int contMaxHeapfy;
    int contTrocas;

    int retornarIndicePai(int i) {
        return i / 2;
    }


    int retornarIndiceFilhoEsquerda(int i) {
        return 2 * i + 1;
    }


    int retornarIndiceFilhoDireita(int i) {
        return 2 * i + 2;
    }


    void maxHeapfy(int[] vetor, int i, int tamanhoHeap) {
        this.contMaxHeapfy++;

        int l = retornarIndiceFilhoEsquerda(i);
        int r = retornarIndiceFilhoDireita(i);

        int maior = i;

        if (l <= tamanhoHeap && vetor[l] > vetor[i]) {
            maior = l;
        }

        if (r <= tamanhoHeap && vetor[r] > vetor[maior]) {
            maior = r;
        }


        if (maior != i) {
            trocar(vetor, i, maior);

            maxHeapfy(vetor, maior, tamanhoHeap);

        }
    }


    void construirMaxHeap(int[] vetor, int tamanhoHeap) {

        for (int i = ((tamanhoHeap - 1) / 2); i >= 0; i--) {
            maxHeapfy(vetor, i, tamanhoHeap);
        }


    }

    void trocar(int[] vetor, int x, int y) {
        this.contTrocas++;
        int aux = vetor[x];
        vetor[x] = vetor[y];
        vetor[y] = aux;
    }

    int[] heapsort(int[] vetor, int tamanhoA) {
        this.contMaxHeapfy = 0;
        this.contTrocas = 0;
        construirMaxHeap(vetor, tamanhoA);


        System.out.print("\nMAXHEAP: ");
        for (int n = 0; n <= tamanhoA; n++) {
            System.out.print(vetor[n] + " ");
        }
        System.out.println(" ");

        int tam = tamanhoA;

        for (int i = tamanhoA; i > 0; i--) {

            trocar(vetor, 0, i);

            tamanhoA -= 1;
            maxHeapfy(vetor, 0, tamanhoA);

            System.out.print("\nAPÓS HEAPFY: ");
            for (int n = 0; n <= tam; n++) {
                System.out.print(vetor[n] + " ");
            }


        }
        System.out.println("\n\nO procedimento MaxHeapFy foi utilizado: " + this.contMaxHeapfy);
        System.out.println("\n\nO procedimento Trocar foi utilizado: " + this.contTrocas);

        return vetor;
    }
}

