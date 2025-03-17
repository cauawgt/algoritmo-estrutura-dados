package ordenacao;

import java.util.Scanner;

public class Q1MergeSort {
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

                OrdenacaoMergeSort ord = new OrdenacaoMergeSort();
                ord.mergeSort(vetor, 0, M - 1, M);

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

class OrdenacaoMergeSort {
    void mergeSort(int[] vetor, int p, int r, int tamanho) {
        if (p < r) {
            int meio = (p + r) / 2;
            mergeSort(vetor, p, meio, tamanho);
            mergeSort(vetor, meio + 1, r, tamanho);
            merge(vetor, p, r, meio, tamanho);

            // Imprimir o vetor após cada merge
            System.out.print("APÓS O MERGE: ");
            for (int i = 0; i < tamanho; i++) {
                System.out.print(vetor[i] + " ");
            }
            System.out.println();
        }
    }

    void merge(int[] vetor, int p, int r, int meio, int tamanho) {
        int n1 = meio - p + 1;
        int n2 = r - meio;

        int[] v1 = new int[n1];
        int[] v2 = new int[n2];

        for (int i = 0; i < n1; i++) {
            v1[i] = vetor[p + i];
        }
        for (int j = 0; j < n2; j++) {
            v2[j] = vetor[meio + 1 + j];
        }

        int i = 0, j = 0, k = p;

        while (i < n1 && j < n2) {
            if (v1[i] <= v2[j]) {
                vetor[k] = v1[i];
                i++;
            } else {
                vetor[k] = v2[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            vetor[k] = v1[i];
            i++;
            k++;
        }

        while (j < n2) {
            vetor[k] = v2[j];
            j++;
            k++;
        }
    }
}

