#include <bits/stdc++.h>

#define MAXV 202
#define MAXA 101

int memo[202][21234];

using namespace std;

class Vertice
{
	public:
		int id,info,val,cor,qtFilhos;
		Vertice *pai, *filhos[MAXA];

		Vertice(int _id=0, int _info=0, int _val=0) : id(_id), info(_info), val(_val), qtFilhos(0) {};
		int addAresta(Vertice *filho) {filhos[qtFilhos++] = filho; return qtFilhos-1;}
};

class Grafo
{
	public:
		int qtVertices, qtComponentes;
		int qc[202], vc[202];
		Vertice *vertices[MAXV];

		Grafo() : qtVertices(0) {};
		void addVertice(int id, int info, int val) {vertices[qtVertices++] = new Vertice(id, info, val);}
		void addAresta(int a, int b) {vertices[a]->addAresta(vertices[b]);}
		int DFS(int raiz = -1)
		{
			for (int i = 0; i < qtVertices; i++)
			{
				vertices[i]->cor = 0;
				vertices[i]->pai = NULL;
			}
			qtComponentes = 0;
			memset(qc,0,sizeof(qc));
			memset(vc,0,sizeof(vc));

			for (int i = 0; i < qtVertices; i++)
				if (vertices[i]->cor == 0)
				{
					DFS_AUX(i);
					qtComponentes++;
				}
			return qtComponentes;
		}
		void DFS_AUX(int raiz)
		{
			vertices[raiz]->cor = 1;
			qc[qtComponentes]+=vertices[raiz]->info;
			vc[qtComponentes]+=vertices[raiz]->val;
			for (int i = 0; i < vertices[raiz]->qtFilhos; i++)
			{
				if (vertices[raiz]->filhos[i]->cor == 0)
				{
					vertices[raiz]->filhos[i]->pai = vertices[raiz];
					DFS_AUX(vertices[raiz]->filhos[i]->id);
				}
			}
		}

		int knap(int id, int remW)
		{
			if (remW==0) return 0;
			if (id==qtComponentes) return 0;
			if (vc[id] > remW) return knap(id+1, remW);
			if (vc[id] <= remW)
			{
				if (memo[id][remW] == -1)
					memo[id][remW] = max(knap(id+1,remW), qc[id] + knap(id+1,remW-vc[id]));
				
				return memo[id][remW];
			}
		}
};

int main()
{
	int D,P,R,B,X,Y,i,j,V;

	scanf("%d %d %d %d", &D, &P, &R, &B);

	Grafo G;
	for (i = 0; i < D; i++)
	{
		scanf("%d", &V);
		G.addVertice(i, -1, V);
	}
	for (j = 0; j < P; j++)
	{
		scanf("%d", &V);
		G.addVertice(i+j, 1, V);
	}
	for (i = 0; i < R; i++)
	{
		scanf("%d %d", &X, &Y);
		X--; Y--;
		G.addAresta(X,Y+D);
		G.addAresta(Y+D,X);
	}

	G.DFS();
	memset(memo,-1,sizeof(memo));
	printf("%d ", D+G.knap(0,B));
	for (i = 0; i < G.qtComponentes; i++)
	{
		G.qc[i] = -G.qc[i];
	}
	memset(memo,-1,sizeof(memo));
	printf("%d\n", P+G.knap(0,B));	
}
