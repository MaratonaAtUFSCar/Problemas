#include <bits/stdc++.h>
int fat[] = {1,2,6,24,120,720,5040,40320,362880};

int main()
{
	int n;
	scanf("%d", &n);
	int i = 8, c = 0;
	while (n)
	{
		while (n >= fat[i])
		{
			n -= fat[i];
			c++;
		}
		i--;
	}
	printf("%d\n", c);
}
