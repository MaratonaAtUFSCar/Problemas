#include <bits/stdc++.h>
using namespace std;

int N,T,X[212345]; // Variaveis de entrada
int SV,SM,NC,NM; // Variaveis da dp bottom-up
int a,b,c,d; // Variaveis auxiliares da dp

void calc()
{
    /* Caso base */
    SV = -999999999;
    SM = -999999999;
    NC = -X[0]-T;
    NM = 0;
    
    /* Passo indutivo */
    for (int i = 1; i < N; i++)
    {
        a = max(SM,NC)+X[i];
        b = max(SM,NC);
        c = max(SV,NM)-X[i]-T;
        d = max(SV,NM);
        SV=a; SM=b; NC=c; NM=d;
    }
}

int main()
{
    while (scanf("%d %d", &N, &T) != EOF)
    {
        for (int i = 0; i < N; i++)
            scanf("%d", X+i);
        calc();
        cout << max(SV,max(SM,max(NC,NM))) << endl;
    }
    return 0;
}
