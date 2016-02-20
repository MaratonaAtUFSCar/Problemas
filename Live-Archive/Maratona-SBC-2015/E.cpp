#include <bits/stdc++.h>
using namespace std;
typedef long long int ll;
typedef long double lf;

ll solve_bhaskara(ll a, ll b, ll c)
{
    lf delta = (lf)b*b - (lf)4*a*c;
    lf x1 = (-b + sqrtl(delta)) / (2*a);
    lf x2 = (-b - sqrtl(delta)) / (2*a);
    return floor(min(x1,x2)); // maior X tal que (-4X^2 + (4N)X - B <= 0)
}
 
int main()
{
    ll N,B;
    while (scanf("%lld %lld", &N, &B) != EOF)
    {
        ll X = solve_bhaskara(-4, 4*N, -B);
        ll Y = X+1;
        ll falta = B - (4*X*(N-X)), tam = N-2*X;
        swap(X,Y);

        int dx[] = {0,1,0,-1}, dy[] = {1,0,-1,0}, dir = 0;
        while (falta)
        {
            ll d = min(falta,tam);
            X += dx[dir] * d;
            Y += dy[dir] * d;
            falta -= d;
            if (!dir) tam--;
            dir++;
        }
        cout << X << " " << Y << endl;
    }
}
