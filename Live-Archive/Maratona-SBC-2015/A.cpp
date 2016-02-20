#include <bits/stdc++.h>
using namespace std;
#define Aresta pair<Vertice*, int>  
#define mp make_pair
 
class Vertice
{
    public:
    int info, cor, id;
    vector<Aresta> filhos;
    Vertice(int i) : id(i) {};
    void addAresta(Vertice *v, int p) { filhos.push_back(mp(v,p)); }
};

class Grafo
{
    public:
    vector<Vertice*> vertices;
 
    Grafo(int n) { for (int i = 0; i < n; i++) { vertices.push_back(new Vertice(i)); }}
    void addAresta(int i, int j, int p) { vertices[i]->addAresta(vertices[j],p); }
 
	int dijkstra(int N)
	{
        vector<int> dist(N, 112345678); dist[0] = 0;
        
        priority_queue< Aresta,vector<Aresta>,greater<Aresta> > pq;
        pq.push(mp(vertices[0],0));

        while (!pq.empty())
        {
            Aresta a = pq.top(); pq.pop();
            Vertice *v = a.first;
            int d = a.second;
            
            if (d > dist[v->id]) continue;
            
            for (int i = 0; i < v->filhos.size(); i++)
            {
                Vertice *f = v->filhos[i].first;
                int peso = v->filhos[i].second;

                if (dist[v->id] + peso < dist[f->id])
                {                
                    dist[f->id] = dist[v->id] + peso;
                    pq.push(mp(f, dist[f->id]));
                } 
            } 
        }
        return dist[N-2];
	}
};

 
int main()
{
    int N,M,I,A,B;
    long long int P;
    while (scanf("%d %d", &N, &M) != EOF)
    {
        Grafo G(2*N);
        for (int i = 0; i < M; i++)
        {
            scanf("%d %d %lld", &A, &B, &P); A--; B--;
            G.addAresta(2*A,2*B+1,P);
            G.addAresta(2*B+1,2*A,P);
            G.addAresta(2*A+1,2*B,P);
            G.addAresta(2*B,2*A+1,P);
        }
    I = G.dijkstra(2*N);
    cout << (I!=112345678?I:-1) << endl;
    }
}
