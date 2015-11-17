#include <iostream>
using namespace std;

int main() {

	int chutes[5];
	int t;
	while(cin >> t) {
		int c = 0;
		for(int i = 0; i < 5; i++) {
			cin >> chutes[i];
			if(chutes[i] == t)
				c++;
		}
		cout << c << endl;
	}

	return 0;
}
