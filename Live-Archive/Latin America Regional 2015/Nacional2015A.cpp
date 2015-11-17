#include <iostream>
#include <string>
#include <string.h>

using namespace std;

int n;
int num[19];
int v[19];
int solve(int atual, int usados[10], int ja)
{
    int aux[10];
    for (int i = 0; i < 10; i++)
        aux[i] = usados[i];

    for (int i = 0; i < 10; i++){
        if (usados[i] > 2) return 0;
    }
    if (atual == n) return 1;

    for (int i = 9; i >= 0; --i){
        if (!ja && i > v[atual])
            continue;
        if (!ja){
            aux[i]++;
            int res = solve(atual + 1, aux, ja);
            aux[i]--;
            if (res){
                num[atual] = i;
                return 1;
            }
            ja = 1;
        }
        else{
            aux[i]++;
            int res = solve(atual + 1, aux, ja);
            aux[i]--;
            if (res){
                num[atual] = i;
                return 1;
            }
        }
    }
    return 0;
}

int main()
{
    string s;
    while (cin >> s)
    {
        n = s.size();
        for (int i = 0; i < n; i++)
            v[i] = s[i] - '0';
        memset(num, 0, sizeof(num));
        int usado[10];
        memset(usado, 0, sizeof(usado));
        solve(0, usado, 0);
        int aux;
        for (aux = 0; aux < n; aux++){
            if (num[aux])
                break;
        }
        for (int i = aux; i < n; i++)
            cout << num[i];
            cout << endl;
    }

}
