import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker

    #Aluno: Jessé Oliveira das Chagas
    #Email: jesse.oliveira.chagas@ccc.ufcg.edu.br
    #Matrícula: 123210473
    #Nome de usuário do GitHub: jessechagas

# Função para formatar valores no eixo Y (Tempo)
def formatar_valor(valor, _):
    if valor < 1:
        return f"{valor:.1f}" 
    return f"{int(valor)}"  


# Lê os arquivos CSV
dados_binaria = pd.read_csv("dados_aleatorio_binaria_python.csv")
dados_avl = pd.read_csv("dados_aleatorio_avl_python.csv")

# Converte a coluna "Tempo" para float, caso esteja como string
dados_binaria["Tempo"] = dados_binaria["Tempo"].astype(float)
dados_avl["Tempo"] = dados_avl["Tempo"].astype(float)

# Definição dos tamanhos de entrada fixos
tamanhos_entrada = [10_000, 50_000, 100_000, 250_000, 500_000, 1_000_000]
tamanhos_formatados = ["10k",  "50k", "100k", "250k", "500k", "1M"]

# Cria uma figura com 3 subplots (um para cada operação)
fig, axs = plt.subplots(3, 1, figsize=(12, 10))

# Lista de operações
operacoes = ["Inserção", "Busca", "Remoção"]

# Itera sobre as operações e plota cada gráfico em um subplot
for i, (operacao, titulo) in enumerate(zip(operacoes, ["Tempo de Inserção", "Tempo de Busca", "Tempo de Remoção"]), 1):
    # Filtra os dados para a operação atual
    dados_binaria_operacao = dados_binaria[dados_binaria["Operacao"] == operacao]
    dados_avl_operacao = dados_avl[dados_avl["Operacao"] == operacao]

    # Encontra o maior tempo para definir o limite do eixo Y
    max_tempo = max(dados_binaria_operacao["Tempo"].max(), dados_avl_operacao["Tempo"].max())

    # Plota no subplot correspondente
    axs[i-1].plot(dados_binaria_operacao["Tamanho"], dados_binaria_operacao["Tempo"], label="Árvore Binária", marker="o")
    axs[i-1].plot(dados_avl_operacao["Tamanho"], dados_avl_operacao["Tempo"], label="Árvore AVL", marker="o")
    axs[i-1].set_title(titulo)
    axs[i-1].set_xlabel("Tamanho da Entrada")
    axs[i-1].set_ylabel("Tempo (s)")
    axs[i-1].legend()
    axs[i-1].grid()

    # Define o limite do eixo Y baseado no maior tempo encontrado
    axs[i-1].set_ylim(0, max_tempo * 1.1)  

    # Ajusta os ticks do eixo X para corresponder às entradas fornecidas
    axs[i-1].set_xticks(tamanhos_entrada)
    axs[i-1].set_xticklabels(tamanhos_formatados)

    # Aplica a formatação personalizada no eixo Y (Tempo)
    axs[i-1].yaxis.set_major_formatter(ticker.FuncFormatter(formatar_valor))

# Ajusta o layout para evitar sobreposição
plt.subplots_adjust(bottom=0.05, top=0.95, hspace=0.3)

# Salva a figura em um arquivo de imagem
plt.savefig("graficos_comparacao.png", bbox_inches="tight")  

# Exibe a figura
plt.show()
