package rbTree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/*
    Aluno: Jessé Oliveira das Chagas
    Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    Matrícula: 123210473
    Nome de usuário do GitHub: jessechagas
*/

public class RBTreeTest {

    // Gera dados aleatórios para a árvore rubro-negra
    private int[] gerarDadosAleatorios(int tamanho) {
        Random random = new Random();
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = random.nextInt(tamanho * 10) + 1;
        }
        return dados;
    }

    // Gera dados ordenados para a árvore rubro-negra
    private int[] gerarDadosOrdenados(int tamanho) {
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = i + 1; // Dados em ordem crescente
        }
        return dados;
    }

    // Testa a árvore rubro-negra com inserção, busca e remoção
    private double[] testarRBTree(int[] valores) {
        RbTree arvore = new RbTree();
        long inicio;

        // Teste de inserção
        inicio = System.nanoTime();
        for (int valor : valores) {
            arvore.insert(valor);
        }
        double tempoInsercao = (System.nanoTime() - inicio) / 1e9;

        // Teste de busca
        inicio = System.nanoTime();
        for (int valor : valores) {
            arvore.search(valor);  // Método search está presente no código? Se não, crie um acessor público para searchNode
        }
        double tempoBusca = (System.nanoTime() - inicio) / 1e9;

        // Teste de remoção
        inicio = System.nanoTime();
        for (int valor : valores) {
            arvore.remove(valor);
        }
        double tempoRemocao = (System.nanoTime() - inicio) / 1e9;

        return new double[]{tempoInsercao, tempoBusca, tempoRemocao};
    }

    // Executa os testes com os dados e salva os resultados
    private void executarTestes(int[] tamanhos) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o tipo de dados para o teste:");
        System.out.println("1 - Aleatórios");
        System.out.println("2 - Ordenados");
        int escolha = scanner.nextInt();

        String tipoDados = (escolha == 1) ? "aleatorio" : "ordenado";
        String fileName = "dados_" + tipoDados + "_rbtree_java.csv";
        StringBuilder csvData = new StringBuilder("Tamanho,Arvore,Operacao,Tempo\n");

        for (int tamanho : tamanhos) {
            int[] dados;

            if (escolha == 1) {
                dados = gerarDadosAleatorios(tamanho);
            } else {
                dados = gerarDadosOrdenados(tamanho);
            }

            double[] temposRB = testarRBTree(dados);
            csvData.append(tamanho).append(",Rubro-Negra,Inserção,").append(temposRB[0]).append("\n");
            csvData.append(tamanho).append(",Rubro-Negra,Busca,").append(temposRB[1]).append("\n");
            csvData.append(tamanho).append(",Rubro-Negra,Remoção,").append(temposRB[2]).append("\n");

            System.out.println("Tamanho: " + tamanho);
            System.out.printf("Árvore Rubro-Negra - Inserção: %.6fs, Busca: %.6fs, Remoção: %.6fs%n",
                    temposRB[0], temposRB[1], temposRB[2]);

            System.out.println("Pressione Enter para continuar...");
            scanner.nextLine(); // limpa buffer
            scanner.nextLine(); // aguarda enter
        }

        // Salva os resultados em CSV
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(csvData.toString());
            System.out.println("Dados salvos em '" + fileName + "'.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        RBTreeTest teste = new RBTreeTest();
        int[] entradas = {10000, 50000, 100000, 250000, 500000, 1000000};
        teste.executarTestes(entradas);
    }
}
