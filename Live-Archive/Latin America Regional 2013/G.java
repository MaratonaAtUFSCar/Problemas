// Going Up The Ultras
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
   
//class Main
class G
{
    final double log2 = Math.log(2.);
  
    // baseado no exemplo em
    // http://www.topcoder.com/tc?d1=tutorials&d2=lowestCommonAncestor&module=Static
       
    void processaMatRMaxQ(int[][] m, int[] o, int n)
    {
        int i, j;
       
        for (i = 0; i < n; i++)
            m[i][0] = i;
        for (j = 1; 1 << j <= n; j++)
            for (i = 0; i + (1 << j) - 1 < n; i++)
                if (o[m[i][j - 1]] > o[m[i + (1 << (j - 1))][j - 1]])
                    m[i][j] = m[i][j - 1];
                else
                    m[i][j] = m[i + (1 << (j - 1))][j - 1];
    }
       
    int consultaRMaxQ(int[][] m, int[] o, int b, int a)
    {
        int d;
       
        d = a - b;
        d = (int) (Math.log(d) / log2);
        if (o[m[b][d]] > o[m[a - (1 << d) + 1][d]])
            return m[b][d];
        else
            return m[a - (1 << d) + 1][d];
    }
  
    void processaMatRMinQ(int[][] m, int[] o, int n)
    {
        int i, j;
       
        for (i = 0; i < n; i++)
            m[i][0] = i;
        for (j = 1; 1 << j <= n; j++)
            for (i = 0; i + (1 << j) - 1 < n; i++)
                if (o[m[i][j - 1]] < o[m[i + (1 << (j - 1))][j - 1]])
                    m[i][j] = m[i][j - 1];
                else
                    m[i][j] = m[i + (1 << (j - 1))][j - 1];
    }
       
    int consultaRMinQ(int[][] m, int[] o, int b, int a)
    {
        int d;
       
        d = a - b;
        d = (int) (Math.log(d) / log2);
        if (o[m[b][d]] < o[m[a - (1 << d) + 1][d]])
            return m[b][d];
        else
            return m[a - (1 << d) + 1][d];
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
            final int minD = 150000;
              
            while (true)
            {
                String linha = leitor.readLine();
                if (linha == null || linha.equals(""))
                    break;
                int n = Integer.parseInt(linha);
                int max = 0;
                int[] profile = new int[n];
                for (int i = 0; i < n; ++i)
                {
                    fluxo.nextToken();
                    profile[i] = (int) fluxo.nval;
                    max = Math.max(max, profile[i]);
                }
  
                int[] left = new int[n];
                left[0] = -1;
                for (int i = 1; i < n; ++i)
                {
                    left[i] = i - 1;
                    while (left[i] > -1 && profile[i] >= profile[left[i]])
                    {
                        left[i] = left[left[i]];
                    }
                }
                  
                int[] right = new int[n];
                right[n - 1] = n;
                for (int i = n - 2; i >= 0; --i)
                {
                    right[i] = i + 1;
                    while (right[i] < n && profile[i] >= profile[right[i]])
                        right[i] = right[right[i]];
                }
  
                int log = (int) Math.ceil(Math.log(n) / log2);
                int[][] mat = new int[n][log];
                  
                processaMatRMinQ(mat, profile, n);
                  
                boolean atLeastOne = false;
                for (int i = 0; i < n; ++i)
                {
                    if (profile[i] >= minD)
                    {
                        boolean ok1 = false, ok2 = false;
         
                        if (profile[i] == max)
                        {
                            ok1 = ok2 = true;
                        }
                        else
                        {
                            if (left[i] > -1)
                            {
                                int ii = consultaRMinQ(mat, profile, left[i], i);
                                if (profile[i] - profile[ii] >= minD)
                                    ok1 = true;
                            }
                            else
                                ok1 = true;
                                          
                            if (right[i] < n)
                            {
                                int jj = consultaRMinQ(mat, profile, i, right[i]);
                                if (profile[i] - profile[jj] >= minD)
                                    ok2 = true;
                            }
                            else
                                ok2 = true;
                                      
                        }
                                  
                        if (ok1 && ok2)
                        {
                            if (atLeastOne)
                                escritor.print(' ');
                            escritor.print(i + 1);
                            if (!atLeastOne)
                                atLeastOne = true;
                        }
                    }
                }
                escritor.println();
  
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
