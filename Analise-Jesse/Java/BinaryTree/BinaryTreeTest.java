package BinaryTree;

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

public class BinaryTreeTest {

    // Gera dados aleatórios para a árvore binária
    private int[] gerarDadosAleatorios(int tamanho) {
        Random random = new Random();
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = random.nextInt(tamanho * 10) + 1;
        }
        return dados;
    }

    // Gera dados ordenados para a árvore binária
    private int[] gerarDadosOrdenados(int tamanho) {
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = i + 1; // Dados em ordem crescente
        }
        return dados;
    }

    // Testa a árvore binária com inserção, busca e remoção
    private double[] testBinaryTree(int[] valores) {
        BinaryTree arvore = new BinaryTree();
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
            arvore.search(valor);
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

    // Função para executar o teste em partes separadas
    private void executarTesteEmPartesSeparadas(int[] entradas) {
        Scanner scanner = new Scanner(System.in);

        // Pergunta ao usuário qual tipo de dados ele deseja
        System.out.println("Escolha o tipo de dados para o teste:");
        System.out.println("1 - Aleatórios");
        System.out.println("2 - Ordenados");
        int escolha = scanner.nextInt();

        String tipoDados = (escolha == 1) ? "aleatorio" : "ordenado";
        String fileName = "dados_" + tipoDados + "_binaria_java.csv";
        StringBuilder csvData = new StringBuilder("Tamanho,Arvore,Operacao,Tempo\n");

        for (int tamanho : entradas) {
            int[] dados;

            // Gera dados aleatórios ou ordenados com base na escolha
            if (escolha == 1) {
                dados = gerarDadosAleatorios(tamanho);
            } else {
                dados = gerarDadosOrdenados(tamanho);
            }

            // Testa a Árvore Binária para o tamanho de dados
            double[] temposBin = testBinaryTree(dados);
            csvData.append(tamanho).append(",Binaria,Inserção,").append(temposBin[0]).append("\n");
            csvData.append(tamanho).append(",Binaria,Busca,").append(temposBin[1]).append("\n");
            csvData.append(tamanho).append(",Binaria,Remoção,").append(temposBin[2]).append("\n");

            System.out.println("Tamanho: " + tamanho);
            System.out.printf("Árvore Binária - Inserção: %.6fs, Busca: %.6fs, Remoção: %.6fs%n",
                    temposBin[0], temposBin[1], temposBin[2]);

            System.out.println("Pressione Enter para continuar com o próximo tamanho...");
            scanner.nextLine(); // Aguarda o usuário pressionar Enter
            scanner.nextLine(); // Lê o Enter pressionado
        }

        // Salva os dados em um arquivo CSV
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(csvData.toString());
            System.out.println("Dados da Árvore Binária salvos em '" + fileName + "'.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BinaryTreeTest teste = new BinaryTreeTest();
        int[] entradas = {1000, 5000, 10000, 25000, 50000, 100000};
        teste.executarTesteEmPartesSeparadas(entradas);
    }
}
