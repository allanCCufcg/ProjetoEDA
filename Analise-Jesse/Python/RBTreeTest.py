import random
import time
import csv

from RbTree import RedBlackTree

    #Aluno: Jessé Oliveira das Chagas
    #Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    #Matrícula: 123210473
    #Nome de usuário do GitHub: jessechagas


def gerar_dados_aleatorios(tamanho):
    return [random.randint(1, tamanho * 10) for _ in range(tamanho)]

def gerar_dados_ordenados(tamanho):
    return list(range(1, tamanho + 1))

def testar_rbtree(valores):
    arvore = RedBlackTree()

    # Teste de inserção
    inicio = time.time()
    for valor in valores:
        arvore.insert(valor)
    tempo_insercao = time.time() - inicio

    # Teste de busca (simulada, pois não há método search na implementação atual)
    inicio = time.time()
    for valor in valores:
        pass  # aqui seria: arvore.search(valor)
    tempo_busca = time.time() - inicio

    # Teste de remoção
    inicio = time.time()
    for valor in valores:
        arvore.remove(valor)
    tempo_remocao = time.time() - inicio

    return tempo_insercao, tempo_busca, tempo_remocao

def executar_teste(entradas):
    print("Escolha o tipo de dados para o teste:")
    print("1 - Aleatórios")
    print("2 - Ordenados")
    escolha = input("Sua escolha: ")

    tipo_dado = "aleatorio" if escolha == "1" else "ordenado"
    nome_arquivo = f"dados_{tipo_dado}_rbtree_python.csv"

    with open(nome_arquivo, mode="w", newline="") as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(["Tamanho", "Arvore", "Operacao", "Tempo"])

        for tamanho in entradas:
            print(f"\nTestando tamanho: {tamanho}")
            dados = gerar_dados_aleatorios(tamanho) if escolha == "1" else gerar_dados_ordenados(tamanho)

            tempos = testar_rbtree(dados)

            writer.writerow([tamanho, "RedBlackTree", "Insercao", f"{tempos[0]:.6f}"])
            writer.writerow([tamanho, "RedBlackTree", "Busca", f"{tempos[1]:.6f}"])
            writer.writerow([tamanho, "RedBlackTree", "Remocao", f"{tempos[2]:.6f}"])

            print(f"RedBlackTree - Inserção: {tempos[0]:.6f}s, Busca: {tempos[1]:.6f}s, Remoção: {tempos[2]:.6f}s")
            input("Pressione Enter para continuar para o próximo tamanho...")

    print(f"\nDados salvos em '{nome_arquivo}'")

if __name__ == "__main__":
    entradas = [10000, 50000, 100000, 250000, 500000, 1000000]
    executar_teste(entradas)
