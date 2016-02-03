/*
 *	Author: Antonio Carlos <falcaopetri@gmail.com>
 *	Tags:	Graph, Bicoloring, DFS/BFS
 */

#include <vector>
#include <queue>
#include <stdio.h>
#include <string.h>

using namespace std;

#define MAX 210

int g[MAX][MAX];
int colors[MAX];

bool is_bicolorable(int beg) {
    memset(colors, 0, sizeof colors);
    queue<int> q;

    q.push(beg);
    colors[beg] = 1;

    while (!q.empty()) {
        int curr = q.front(); q.pop();

        for (int i = 0; i < MAX; ++i) {
            if (!g[curr][i]) continue;

            int next = i;

            if (!colors[next]) {
                colors[next] = -colors[curr];
                q.push(next);
            }
            else if (colors[next] == colors[curr]) {
                return false;
            }
        }
    }

    return true;
}

int main() {
	int n;

    while (scanf("%d", &n), n != 0) {
        memset(g, false, sizeof g);

        int l; scanf("%d", &l);

        while (l--) {
            int a, b; scanf("%d %d", &a, &b);

            g[a][b] = g[b][a] = true;
        }

        if (is_bicolorable(0))  printf("BICOLORABLE.\n");
        else                    printf("NOT BICOLORABLE.\n");
    }

	return 0;
}
