#include <iostream>
#include <string.h>
#include <string>
#include <vector>

#define N 10009
using namespace std;

pair<int, int> tabuleiroMod[101][101];

//Emparelhamento implementado pelo fossa
//DFS
int A[N], B[N]; // zerar A e B para cada caso de teste
bool v[N]; // visitados, zerar a cada chamada da dfs
vector<int> adj[N];

int dfs(int x)
{
    if (x == 0) return 1;
    v[x] = 1;
    for (int i = 0; i < adj[x].size(); ++i)
    {
        int y = adj[x][i];
        if (!v[B[y]] && dfs(B[y]))
        {
            B[y] = x;
            A[x] = y;
            return 1;
        }
    }
    return 0;
}
//Fim da DFS do Fossa

int main()
{
    int n;
    while (cin >> n)
    {
        //Zera a matriz de adjacência
        for (int i = 0; i < N; i++)
            adj[i].clear();

        //Leitura e modificação do tabuleiro como pares de linhas e colunas modelado para o problema
        //Modifica linhas

        int linhaAtual = 1;

        for (int i = 0; i < n; i++)
        {
            string in;
            cin >> in;

            for (int j = 0; j < n; j++)
            {
                if (j != 0 && j != n - 1 && in[j] == 'X')
                    linhaAtual++;
                tabuleiroMod[i][j].first = linhaAtual;
                if (in[j] == 'X')
                    tabuleiroMod[i][j].first = -1;
            }
            linhaAtual++;
        }

        //Modifica colunas

        int colunaAtual = 1;

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (j != 0 && j != n - 1 && tabuleiroMod[j][i].first == -1)
                    colunaAtual++;
                tabuleiroMod[j][i].second = colunaAtual;
                if (tabuleiroMod[j][i].first == -1)
                    tabuleiroMod[j][i].second = -1;
            }
            colunaAtual++;
        }

        //Monta o grafo para emparelhamento

        int nLinhas = linhaAtual - 1;   //Utilizado na hora do emparelhamento
        int nColunas = colunaAtual - 1; //Não utilizado

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                int numLinha = tabuleiroMod[i][j].first;
                int numColuna = tabuleiroMod[i][j].second;

                if (tabuleiroMod[i][j].first != -1)
                    adj[numLinha].push_back(numColuna);
            }
        }

        //Chama emparelhamento dando a saída
        //Emparelhamento implementado pelo Fossa
        memset(A, 0, sizeof A);
        memset(B, 0, sizeof B);
        int res = 0;

        //Para todos os nós de um único lado
        for (int i = 1; i <= nLinhas; i++)
        {
            memset(v, 0, sizeof v);
            res += dfs(i);
        }

        //Resposta do matching
        cout << res << endl;
    }

}
