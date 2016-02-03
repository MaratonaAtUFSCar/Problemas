/*
 *	Author: Antonio Carlos <falcaopetri@gmail.com>
 *	Tags:	Graph, Bicoloring, DFS/BFS
 */

#include <vector>
#include <stdio.h>
#include <string.h>

using namespace std;

#define MAX 2010

vector<int> g[MAX];
int colors[MAX];

bool is_bicolorable(int curr, int color) {
	colors[curr] = color;

    for (int i = 0; i < g[curr].size(); ++i) {
        int next = g[curr][i];

		if (!colors[next]) {
			if (!is_bicolorable(next, -color)) return false;
		}
		else if (colors[next] == color) {
			return false;
		}
    }

    return true;
}

int main() {
	int T; scanf("%d", &T);

	for (int t = 1; t <= T; ++t) {
		memset(colors, 0, sizeof colors);

		int V, E; scanf("%d %d", &V, &E);

		for (int i = 0; i < E; ++i) {
			int a, b; scanf("%d %d", &a, &b);
			a--; b--;

			g[a].push_back(b);
			g[b].push_back(a);
		}

		bool ok = true;
		for (int i = 0; i < V; ++i) {
			if (!colors[i]) {
				if (!is_bicolorable(i, 1)) {
					ok = false;
					break;
				}
			}
		}

		printf("Scenario #%d:\n", t);

		if (ok) 	printf("No suspicious bugs found!\n");
		else		printf("Suspicious bugs found!\n");

		for (int i = 0; i < MAX; ++i) g[i].clear();
	}

	return 0;
}
