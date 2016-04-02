#include <bits/stdc++.h>
using namespace std;

int N, K, X, Z = 0;
long long V;
map<int,long long> U;

inline int findMSB(long long &v)
{
    for (int i = K; i >= 0; i--)
        if (v & (1LL<<i)) return i;
    return -1;
}

int main()
{
    ios_base::sync_with_stdio(false);
    scanf("%d %d", &N, &K);
    for (int i = 0; i < N; i++)
    {
        int MSB = -1;
        V = 0;
        for (int j = 0; j < K; j++)
        {
            scanf("%d", &X);
            if (X&1) { V |= (1LL<<(K-1-j)); MSB = max(MSB,K-1-j); }
        }
        while (V && U.find(MSB) != U.end())
        {
            V ^= U[MSB];
            MSB = findMSB(V);
        }
        U[MSB] = V;
    }
    if (U.size() == K+1) printf("N\n");
    else printf("S\n");
}
