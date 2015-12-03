// URI Online Judge | 1507 
// Subsequências
// Por Rigel Bezerra de Melo, Universidade Federal de Campina Grande BR Brazil
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
 
//public class Subsequencias
//class Main
class F
{   
    void executa() throws Exception
    {
        int caso = 1;
         
        BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer fluxo = new StreamTokenizer(leitor);
        PrintWriter escritor = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
 
        fluxo.eolIsSignificant(false);
         
        try
        {
            int[] corresp = new int[256];
            for (int i = 0; i < 26; ++i)
                corresp[i + 'A'] = i;
            for (int i = 0; i < 26; ++i)
                corresp[i + 'a'] = i + 26;
                 
            int n = Integer.parseInt(leitor.readLine());
            while (--n >= 0)
            {
                char[] seq = leitor.readLine().toCharArray();
                int[] ind = new int[52];
                int[][] mat = new int[52][seq.length];
                for (int i = 0; i < seq.length; ++i)
                {
                    int atual = corresp[seq[i]];
                    mat[atual][ind[atual]++] = i;
                }
                int q = Integer.parseInt(leitor.readLine());
                while (--q >= 0)
                {
                    char[] cand = leitor.readLine().toCharArray();
                    boolean ok = true;
                    int ptr = 0;
                    for (int i = 0; i < cand.length; ++i)
                    {
                        int atual = corresp[cand[i]];
                        ptr = Arrays.binarySearch(mat[atual], 0, ind[atual], ptr);
                        if (ptr < 0)
                            ptr = ~ptr;
                        if (ptr == ind[atual])
                        {
                            ok = false;
                            break;
                        }
                        ptr = mat[atual][ptr] + 1;
                    }
                    if (ok)
                        escritor.println("Yes");
                    else
                        escritor.println("No"); 
                }
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
//      (new Subsequencias()).executa();
        (new Main()).executa();
    }
}
