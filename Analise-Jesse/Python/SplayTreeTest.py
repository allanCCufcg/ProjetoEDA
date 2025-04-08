import time
import random
import csv
import sys

sys.setrecursionlimit(600000)
from SplayTree import SplayTree

    #Aluno: Jessé Oliveira das Chagas
    #Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    #Matrícula: 123210473
    #Nome de usuário do GitHub: jessechagas

def gerar_dados_aleatorios(tamanho):
    return [random.randint(1, tamanho * 10) for _ in range(tamanho)]

def gerar_dados_ordenados(tamanho):
    return list(range(1, tamanho + 1))

def testar_splay_tree(valores):
    arvore = SplayTree()

    # Teste de inserção
    inicio = time.time()
    for v in valores:
        arvore.insert(v)
    tempo_insercao = time.time() - inicio

    # Teste de busca
    inicio = time.time()
    for v in valores:
        arvore.search(v)
    tempo_busca = time.time() - inicio

    # Teste de remoção
    inicio = time.time()
    for v in valores:
        arvore.delete(v)
    tempo_remocao = time.time() - inicio

    return tempo_insercao, tempo_busca, tempo_remocao

def executar_teste(entradas):
    print("Escolha o tipo de dados para o teste:")
    print("1 - Aleatórios")
    print("2 - Ordenados")
    escolha = input("Sua escolha (1 ou 2): ")

    tipo = "aleatorio" if escolha == "1" else "ordenado"
    file_name = f"dados_{tipo}_splay_python.csv"

    with open(file_name, mode="w", newline='') as arquivo_csv:
        writer = csv.writer(arquivo_csv)
        writer.writerow(["Tamanho", "Arvore", "Operacao", "Tempo"])

        for tamanho in entradas:
            print(f"\nExecutando testes para tamanho: {tamanho}")
            if escolha == "1":
                dados = gerar_dados_aleatorios(tamanho)
            else:
                dados = gerar_dados_ordenados(tamanho)

            tempos = testar_splay_tree(dados)

            writer.writerow([tamanho, "Splay", "Insercao", f"{tempos[0]:.6f}"])
            writer.writerow([tamanho, "Splay", "Busca", f"{tempos[1]:.6f}"])
            writer.writerow([tamanho, "Splay", "Remocao", f"{tempos[2]:.6f}"])

            print(f"Splay Tree - Inserção: {tempos[0]:.6f}s, Busca: {tempos[1]:.6f}s, Remoção: {tempos[2]:.6f}s")
            input("Pressione Enter para continuar...")

    print(f"\nDados salvos em '{file_name}'.")

if __name__ == "__main__":
    entradas = [10000, 50000, 100000, 250000, 500000, 1000000]
    executar_teste(entradas)
