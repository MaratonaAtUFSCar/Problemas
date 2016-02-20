#include <bits/stdc++.h>
using namespace std;
#define INF 1123456789

class Point
{
    public:
    int X,Y;
    bool operator<(const Point p) const
    {
        return Y<p.Y;
    }
};

int main()
{
    Point p[3333];
    int N;
    cin >> N;
    for (int i = 0; i < N; i++)
        cin >> p[i].X >> p[i].Y;
    sort(p,p+N);
    
    int lower, upper, sol = 0;

    for (int i = 0; i < N-1; i++)
    {
        lower = -INF; upper = INF;
        for (int j = i+1; j < N; j++)
        {
            if (p[j].X > lower && p[j].X < upper)
            {
                sol++;
                if (p[j].X > p[i].X) upper = p[j].X;
                else lower = p[j].X;
            }
        }
    }
    cout << sol << endl;
}
