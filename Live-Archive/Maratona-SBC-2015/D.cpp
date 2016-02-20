#include <bits/stdc++.h>
using namespace std;
 
int N, M, somaLin[101], somaCol[101];
map<string, int> values;
set<string> total;
string grid[101][101];
int main()
{
    while (scanf("%d %d", &N, &M) != EOF)
    {
        values.clear();
        total.clear();
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++)
            {
                cin >> grid[i][j];
                total.insert(grid[i][j]);
            }
            cin >> somaLin[i];
        }
        for (int i = 0; i < M; i++)
            cin >> somaCol[i];
           
        while (values.size() < total.size())
        {
            // Varredura horizontal
            for (int i = 0; i < N; i++)
            {
                map<string,int> notFound;
                int somaAtual = 0;
                for (int j = 0; j < M; j++)
                    if (values.find(grid[i][j]) == values.end())
                        notFound[grid[i][j]]++;
                    else
                        somaAtual += values[grid[i][j]];
                if (notFound.size() == 1)
                {
                    pair<string,int> p = *notFound.begin();
                    values[p.first] = (somaLin[i]-somaAtual)/p.second;
                }
            }
            // Varredura vertical
            for (int j = 0; j < M; j++)
            {
                map<string,int> notFound;
                int somaAtual = 0;
                for (int i = 0; i < N; i++)
                    if (values.find(grid[i][j]) == values.end())
                        notFound[grid[i][j]]++;
                    else
                        somaAtual += values[grid[i][j]];
                if (notFound.size() == 1)
                {
                    pair<string,int> p = *notFound.begin();
                    values[p.first] = (somaCol[j]-somaAtual)/p.second;
                }
            }
        }
        for (auto i : values) cout << i.first << " " << i.second << endl;
    }
}
