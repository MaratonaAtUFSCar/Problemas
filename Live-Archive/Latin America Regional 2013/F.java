// Football
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
 
//class Main
class F
{   
    void executa() throws Exception
    {
        int caso = 1;
           
        final BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
        final StreamTokenizer fluxo = new StreamTokenizer(leitor);
        final PrintWriter escritor = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
   
        fluxo.resetSyntax();
        fluxo.ordinaryChars(0, 255);
        fluxo.wordChars('!', '~');
        fluxo.whitespaceChars('\n', ' ');
        fluxo.eolIsSignificant(false);
 
        try
        {
            while (true)
            {
                int ini, fim;
                String linha;
                 
                linha = leitor.readLine();
                if (linha == null)
                    break;
                fim = linha.indexOf(' ');
                int n = Integer.parseInt(linha.substring(0, fim));
                ini = fim + 1;
                int g = Integer.parseInt(linha.substring(ini));
                Queue<Integer> fila = new PriorityQueue<Integer>();
                int nn = n;
                int resp = 0;
                while (--nn >= 0)
                {
                    linha = leitor.readLine();
                    fim = linha.indexOf(' ');
                    int s = Integer.parseInt(linha.substring(0, fim));
                    ini = fim + 1;
                    int r = Integer.parseInt(linha.substring(ini));
                    int dd = r - s;
                    if (dd >= 0)
                    {
                        fila.add(dd);
                        if (dd == 0)
                            ++resp;
                    }
                    else
                        resp += 3;
                }
                int gg = g;
                while (!fila.isEmpty() && gg > 0)
                {
                    int dd = fila.poll();
                    if (gg > dd)
                    {
                        gg -= dd + 1;
                        if (dd == 0)
                            resp += 2;
                        else
                            resp += 3;
                    }
                    else
                    {
                        if (dd > 0)
                        {
                            if (dd - gg == 0)
                                ++resp;
                        }
                        else
                            if (dd - gg == 0)
                                resp += 2;
                        gg = 0;
                    }
                }   
                escritor.println(resp);
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
