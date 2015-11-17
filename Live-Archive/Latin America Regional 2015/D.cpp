#include <iostream>
using namespace std;

int C[6];

int lower_bound(int n) {
	int i = 0;
	while(i < 6 && C[i] <= n)
		i++;
	return C[i-1];
}

int main() {

	C[0] = 0; C[1] = 1; C[2] = 10; C[3] = 100; C[4] = 1000; C[5] = 10000;
	int n, m, b;
	while(cin >> n >> m) {

		int p1 = 0, p2 = 0;

		for(int i = 0; i < m; i++) {

			int rodada[25];
			int s1 = 0;

			cin >> b;
			
			for(int i = 0; i < n; i++) {
				cin >> rodada[i];
				s1 += rodada[i];
			}
			if(s1 <= b)
				p1 += rodada[0];
			
			p2 += lower_bound(b - s1 + rodada[0]);

		}
		cout << p2 - p1 << endl;
			
	}

	return 0;
}
