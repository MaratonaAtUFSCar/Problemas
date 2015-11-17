#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <algorithm>
using namespace std;

typedef pair<int, int> point;
typedef pair<point, int> ppi;
typedef vector<ppi> vppi;

int main() {

	ios::sync_with_stdio(false);

	int p, v;
	while(cin >> p >> v) {

		vppi todos;

		for(int i = 1; i <= p; i++) {
			ppi aux;
			cin >> aux.first.first >> aux.first.second;
			aux.second = i;
			todos.push_back(aux);
		}
		for(int i = 0; i < v; i++) {
			ppi aux;
			cin >> aux.first.first >> aux.first.second;
			aux.second = -1;
			todos.push_back(aux);
		}

		sort(todos.begin(), todos.end());

		set<int> verticesAtuais;
		int cont = 0;
		for(int i = 0; i < todos.size(); i++) {
			if(todos[i].second == -1) {
				if(verticesAtuais.find(todos[i].first.second) != verticesAtuais.end()) 
					verticesAtuais.erase(todos[i].first.second);
				else
					verticesAtuais.insert(todos[i].first.second);	
			}
			else {
				set<int>::iterator up = verticesAtuais.upper_bound(todos[i].first.second);
				int indice = 0;
				for(set<int>::iterator it = verticesAtuais.begin(); it != up; it++, indice++);
				if(indice % 2 == 0)
					cont += todos[i].second;
			}
		}
		cout << cont << endl;
	}

	return 0;
}
