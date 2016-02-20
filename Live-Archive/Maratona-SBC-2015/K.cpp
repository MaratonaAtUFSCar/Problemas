#include <bits/stdc++.h>
using namespace std;

pair<int,int> dp[2002][2002];
int arr[2002], n, x, sz;
char str[2002];

void lps() // processa a tabela de subsequencias palindromas - bottom up
{
    sz = strlen(str);
    pair<int,int> sol = {0,0}; // {indices especiais, tamanho do palindromo}
    for (int k = 0; k < sz; k++)
        for (int i = 0, j = k; j < sz; i++, j++)
        {
            if (i==j)
                dp[i][j] = {arr[i], 1};
                
            else
            {   
                dp[i][j] = max(dp[i+1][j],dp[i][j-1]);
                if (str[i] == str[j])
                    dp[i][j] = max(dp[i][j], make_pair(dp[i+1][j-1].first+arr[i]+arr[j], dp[i+1][j-1].second + 2));
            }
            
            sol = max(sol, dp[i][j]);
        }
    cout << sol.second << endl;
}

int main()
{
    cin >> str;
    cin >> n;
    
    memset(arr,0,sizeof(arr));
    for (int i = 0; i < n; i++)
    {
        cin >> x;
        arr[x-1] = 1;
    }
    
    lps();
}
