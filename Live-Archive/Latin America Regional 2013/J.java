// Join Two Kingdoms
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
   
//class Main
class J
{
    final int INF = 1 << 29;
    int N, M;
    int[] par, deg;
    boolean[] vis, blk;
    int[][] list, dist;
    boolean[][] mat;
    List<List<Integer>> aList;
 
    void appendTo(StringBuilder builder, double d, int n)
    {
        if (d < 0.)
        {
            builder.append('-');
            d = -d;
        }
        double steer = Math.pow(10., n);
        long scaled = (long) (d * steer + .5);
        long factor = (long) steer;
        int scale = n + 1;
        while (factor * 10 <= scaled)
        {
            factor *= 10;
            scale++;
        }
        while (scale > 0)
        {
            if (scale == n)
                builder.append('.');
            long c = scaled / factor % 10;
            factor /= 10;
            builder.append((char) ('0' + c));
            scale--;
        }
    }
 
    int EXTR;
 
    void bfs(int src)
    {
        int v;
        Deque<Integer> q = new ArrayDeque<Integer>();
 
        q.add(src);
        vis[src] = true;
        while (!q.isEmpty())
        {
            v = q.remove();
//          for (int w = 0; w < deg[v]; ++w)
            for (int w = 0; w < aList.get(v).size(); ++w)
            {
                int x = aList.get(v).get(w);
                if (!vis[x])
                {
                    vis[x] = true;
                    q.add(x);
                    par[x] = v;
                }
            }
            if (q.isEmpty())  // ultimo node a ser visitado é o extremo
                EXTR = v;
        }
    }
 
    int distanciaMinima(int v, int w)
    {
        int distancia = 0;
        while (w != v)
        {
            w = par[w];
            if (w == -1)
            {
                distancia = INF;
                break;
            }
            ++distancia;
        }
        return (distancia);
    }
 
    int[] todasDistancias(int v)
    {
        vis = new boolean[N];
 
        int[] distancias = new int[N];
 
        for (int i = 0; i < N; ++i)
            if (!vis[i])
            {
                int w = i;
                int distancia = 0;
                while (!vis[w] && w != v)
                {
                    w = par[w];
                    if (w == -1)
                    {
                        distancia = INF;
                        break;
                    }
                    ++distancia;
                }
                if (vis[w])
                    distancia += distancias[w];
                distancias[i] = distancia;
                w = i;
                while (!vis[w] && w != v)
                {
                    vis[w] = true;
                    w = par[w];
                    distancias[w] = --distancia;
                }
 
            }
        return (distancias);
    }
 
    // encontra-se o diametro da arvore com um bfs a partir de qualquer node
    // o ultimo node a ser visitado eh um extremo, entao um bfs deste extremo encontra o outro
    int[] processa1(StreamTokenizer fluxo) throws Exception
    {
        par = new int[N];
        vis = new boolean[N];
        aList = new ArrayList<List<Integer>>();
 
        int[] resultados = new int[N];
 
        for (int i = 0; i < N; ++i)
            aList.add(new ArrayList<Integer>());
        for (int i = 0; i < N - 1; ++i)
        {
            fluxo.nextToken();
            int a = (int) fluxo.nval - 1;
            fluxo.nextToken();
            int b = (int) fluxo.nval - 1;
            aList.get(a).add(b);
            aList.get(b).add(a);
        }
        Arrays.fill(par, -1);
        Arrays.fill(vis, false);
        bfs(0);
        int u = EXTR;
        Arrays.fill(par, -1);
        Arrays.fill(vis, false);
        bfs(u);
        int[] distancias1 = todasDistancias(u);
        int v = EXTR;
        Arrays.fill(par, -1);
        Arrays.fill(vis, false);
        bfs(v);
        int[] distancias2 = todasDistancias(v);
        for (int j = 0; j < N; ++j)
            resultados[j] = Math.max(distancias1[j], distancias2[j]);
        return (resultados);
    }
 
    // soma rapida de produto cartesiano a x b em O(a * log(b))
    double processa2(int a, int b, int[] va, int[] vb) throws Exception
    {
        int max = 0;
        for (int i = 0; i < a; ++i)
            max = Math.max(max, va[i]);
        for (int i = 0; i < b; ++i)
            max = Math.max(max, vb[i]);
 
        Arrays.sort(vb);
        int[] somas = new int[b];
        somas[0] = vb[0];
        for (int i = 1; i < b; ++i)
            somas[i] += vb[i] + somas[i - 1];
 
        double total = 0.;
        for (int i = 0; i < a; ++i)
        {
            int x = max - va[i] - 1;
            int ii = Arrays.binarySearch(vb, x);
            if (ii < 0)
                ii = ~ii;
            else
                ++ii;
            while (ii < b && vb[ii] == x)
                ++ii;
            if (ii < b)
                total += (double) ii * max + (double) (b - ii) * (va[i] + 1) + (somas[b - 1] - (ii == 0 ? 0 : somas[ii - 1]));
            else
                total += (double) b * max;
        }
        double resultado = total / (a * b);
 
        return (resultado);
    }
 
    void executa() throws Exception
    {
        int caso = 1;
    
        BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer fluxo = new StreamTokenizer(leitor);
        PrintWriter escritor = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    
//      fluxo.resetSyntax();
//      fluxo.ordinaryChars(0, 255);
//      fluxo.wordChars('!', '~');
//      fluxo.whitespaceChars('\n', ' ');
        fluxo.eolIsSignificant(false);
    
        try
        {
            while (true)
            {
                int token = fluxo.nextToken();
                if (token == StreamTokenizer.TT_EOF)
                    break;
                int n = (int) fluxo.nval;
                fluxo.nextToken();
                int q = (int) fluxo.nval;
 
                N = n;
                int[] distsMaxN = processa1(fluxo);
                N = q;
                int[] distsMaxQ = processa1(fluxo);
 
                double resultado = n < q ? processa2(n, q, distsMaxN, distsMaxQ) : processa2(q, n, distsMaxQ, distsMaxN);
                StringBuilder sb = new StringBuilder();
                appendTo(sb, resultado, 3);
                escritor.println(sb.toString());
 
                ++caso;
            }
        }
        catch (Exception e)
        {
            System.err.println("Problema no caso " + caso);
            e.printStackTrace();
            throw new Exception();
        }
        finally
        {
            escritor.close();
        }
    }
        
    public static void main (String[] args) throws Exception
    {
        (new Main()).executa();
    }
}
