#include <iostream>
#include <fstream>
#include <chrono>
#include <vector>
#include <random>
#include <string>
#include <algorithm>
#include <tuple>
#include "SplayTree.cc"

using namespace std;
using namespace std::chrono;

// Gera dados aleatórios
vector<int> gerarDadosAleatorios(int tamanho) {
    vector<int> dados(tamanho);
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dis(1, tamanho * 10);

    for (int i = 0; i < tamanho; ++i) {
        dados[i] = dis(gen);
    }
    return dados;
}

// Gera dados ordenados
vector<int> gerarDadosOrdenados(int tamanho) {
    vector<int> dados(tamanho);
    for (int i = 0; i < tamanho; ++i) {
        dados[i] = i + 1;
    }
    return dados;
}

// Testa a SplayTree com os dados fornecidos
tuple<double, double, double> testarSplayTree(const vector<int>& valores) {
    SplayTree arvore;

    auto inicio = high_resolution_clock::now();
    for (int v : valores) {
        arvore.insert(v);
    }
    auto fim = high_resolution_clock::now();
    double tempoInsercao = duration<double>(fim - inicio).count();

    inicio = high_resolution_clock::now();
    for (int v : valores) {
        arvore.search(v);
    }
    fim = high_resolution_clock::now();
    double tempoBusca = duration<double>(fim - inicio).count();

    inicio = high_resolution_clock::now();
    for (int v : valores) {
        arvore.remove(v);
    }
    fim = high_resolution_clock::now();
    double tempoRemocao = duration<double>(fim - inicio).count();

    return {tempoInsercao, tempoBusca, tempoRemocao};
}

int main() {
    vector<int> tamanhos = {10000, 50000, 100000, 250000, 500000, 1000000};

    cout << "Escolha o tipo de dados para o teste:\n1 - Aleatórios\n2 - Ordenados\n> ";
    int escolha;
    cin >> escolha;

    string tipoDados = (escolha == 1) ? "aleatorio" : "ordenado";
    string nomeArquivo = "dados_" + tipoDados + "_splay_cpp.csv";

    ofstream arquivo(nomeArquivo);
    if (!arquivo.is_open()) {
        cerr << "Erro ao abrir o arquivo para escrita.\n";
        return 1;
    }

    arquivo << "Tamanho,Arvore,Operacao,Tempo\n";

    for (int tamanho : tamanhos) {
        vector<int> dados = (escolha == 1)
            ? gerarDadosAleatorios(tamanho)
            : gerarDadosOrdenados(tamanho);

        auto [tInsercao, tBusca, tRemocao] = testarSplayTree(dados);

        arquivo << tamanho << ",Splay,Insercao," << tInsercao << "\n";
        arquivo << tamanho << ",Splay,Busca," << tBusca << "\n";
        arquivo << tamanho << ",Splay,Remocao," << tRemocao << "\n";

        cout << "Tamanho: " << tamanho << endl;
        printf("SplayTree - Inserção: %.6fs, Busca: %.6fs, Remoção: %.6fs\n",
               tInsercao, tBusca, tRemocao);

        cout << "Pressione Enter para continuar...";
        cin.ignore();
        cin.get();
    }

    arquivo.close();
    cout << "Dados salvos em '" << nomeArquivo << "'.\n";

    return 0;
}
