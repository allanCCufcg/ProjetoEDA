#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <random>
#include <algorithm>
#include <unordered_set>
#include <limits>
#include "RBTree.cc" 

using namespace std;
using namespace std::chrono;

/*
    Aluno: Jessé Oliveira das Chagas
    Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    Matrícula: 123210473
    Nome de usuário do GitHub: jessechagas
*/

// Gera dados aleatórios únicos
vector<int> gerarDadosAleatorios(int tamanho) {
    unordered_set<int> unicos;
    mt19937 rng(random_device{}());
    uniform_int_distribution<int> dist(1, tamanho * 10);
    while ((int)unicos.size() < tamanho) {
        unicos.insert(dist(rng));
    }
    return vector<int>(unicos.begin(), unicos.end());
}

// Gera dados ordenados
vector<int> gerarDadosOrdenados(int tamanho) {
    vector<int> dados(tamanho);
    for (int i = 0; i < tamanho; i++) dados[i] = i + 1;
    return dados;
}

// Teste da árvore Rubro-Negra
vector<double> testarRBTree(const vector<int>& dados) {
    RBTree arvore;

    // Inserção
    auto inicio = high_resolution_clock::now();
    for (int val : dados) arvore.insert(val);
    auto fim = high_resolution_clock::now();
    double tempoInsercao = duration<double>(fim - inicio).count();

    // Busca (simulada, se não implementada)
    inicio = high_resolution_clock::now();
    for (int val : dados) {
        // arvore.search(val); // descomente se tiver busca implementada
    }
    fim = high_resolution_clock::now();
    double tempoBusca = duration<double>(fim - inicio).count();

    // Remoção
    inicio = high_resolution_clock::now();
    for (int val : dados) arvore.remove(val);
    fim = high_resolution_clock::now();
    double tempoRemocao = duration<double>(fim - inicio).count();

    return { tempoInsercao, tempoBusca, tempoRemocao };
}

int main() {
    vector<int> tamanhos = {10000, 50000, 100000, 250000, 500000, 1000000};

    int tipo;
    cout << "Escolha o tipo de dados para o teste:\n";
    cout << "1 - Aleatórios\n";
    cout << "2 - Ordenados\n";
    cin >> tipo;
    cin.ignore(numeric_limits<streamsize>::max(), '\n'); // limpa '\n' pendente

    string tipoDados = (tipo == 1) ? "aleatorio" : "ordenado";
    string fileName = "dados_" + tipoDados + "_rbtree_cpp.csv";

    ofstream arquivo(fileName);
    arquivo << "Tamanho,Arvore,Operacao,Tempo\n";

    for (int tamanho : tamanhos) {
        vector<int> dados = (tipo == 1) ? gerarDadosAleatorios(tamanho)
                                        : gerarDadosOrdenados(tamanho);

        vector<double> tempos = testarRBTree(dados);
        arquivo << tamanho << ",Rubro-Negra,Insercao," << tempos[0] << "\n";
        arquivo << tamanho << ",Rubro-Negra,Busca," << tempos[1] << "\n";
        arquivo << tamanho << ",Rubro-Negra,Remocao," << tempos[2] << "\n";

        cout << "Tamanho: " << tamanho << endl;
        cout << "Inserção: " << tempos[0] << "s, ";
        cout << "Busca: " << tempos[1] << "s, ";
        cout << "Remoção: " << tempos[2] << "s\n";

        cout << "Pressione Enter para continuar...\n";
        cin.get();
    }

    arquivo.close();
    cout << "Dados salvos em '" << fileName << "'.\n";
    return 0;
}
