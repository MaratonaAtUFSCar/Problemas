// Huffman Inversion
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
 
//class Main
class I
{
    class Node implements Comparable<Node>
    {
        Node l;
        Node r;
        int key;
        long freq;
        int preced;
   
        Node(int key, long freq, int preced)
        {
            this.key = key;
            this.freq = freq;
            this.preced = preced;
        }
         
        public int compareTo(Node node)
        {
            if (this.key != node.key)
                return (node.key - this.key);
            return (this.preced - node.preced);
        }
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
 
                Node raiz = null;
 
                int n = (int) fluxo.nval;
                 
                Queue<Node> queue = new PriorityQueue<Node>();
 
                int max = 0, index = 0;
                for (int i = 0; i < n; ++i)
                {
                    fluxo.nextToken();
                    int x = (int) fluxo.nval;
                    max = Math.max(max, x);
                    queue.add(new Node(x, 0, index++));
                }
 
                int len = max + 1;
                long[] freqs = new long[len];
                freqs[max] = 1;
 
                while (true)
                {
                    Node a = queue.poll();
                    if (a.freq == 0 && a.l == null && a.r == null)
                        a.freq = freqs[a.key];
 
                    if (queue.isEmpty())
                    {
                        raiz = a;
                        break;
                    }
 
                    Node b = queue.poll();
                    if (b.freq == 0 && b.l == null && b.r == null)
                        b.freq = freqs[b.key];
 
                    long sum = a.freq + b.freq;
                    Node c = new Node(a.key - 1, sum, index++);
                    c.l = a;
                    c.r = b;
                    queue.add(c);
 
                    freqs[a.key - 1] = Math.max(freqs[a.key - 1], a.freq);
                    freqs[b.key - 1] = Math.max(freqs[b.key - 1], b.freq);
                }
 
                long ans = raiz.freq;
                escritor.println(ans);
 
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
