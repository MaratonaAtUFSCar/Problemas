// Counting Ones
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
 
//class Main
class C
{
    long[] pot2 = new long[62];
 
    void conta(long x, long[] contador)
    {
        long resultado = 0, restoDeZeros = 0;
        int contadorResto = 0;
        while (x > 0)
        {
            long valor;
            int restoAtual = (int) (x % 2);
            x /= 2;
            if (contadorResto > 0)
            {
                valor = restoAtual * pot2[contadorResto - 1] * contadorResto;
                for (int i = 0; i < 2; ++i)
                    contador[i] += valor;
                if (restoAtual == 0)
                    restoDeZeros += (pot2[contadorResto] - 1) - resultado;
            }
            valor = pot2[contadorResto];
            for (int i = 1; i < restoAtual; ++i)
                contador[i] += valor;
            if (restoAtual > 0)
                contador[restoAtual] += resultado + 1;
            resultado += pot2[contadorResto++] * restoAtual;
        }
        contador[0] -= restoDeZeros;
    }
     
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
            pot2[0] = 1;
            for (int i = 1; i < 62; ++i)
                pot2[i] = 2 * pot2[i - 1];
 
            while (true)
            {
                int ini, fim;
                String linha;
                 
                linha = leitor.readLine();
                if (linha == null)
                    break;
                fim = linha.indexOf(' ');
                long m = Long.parseLong(linha.substring(0, fim));
                ini = fim + 1;
                long n = Long.parseLong(linha.substring(ini));
                long[][] c = new long[2][2];
                conta(n, c[1]);
                conta(m - 1, c[0]);
                long resp = c[1][1] - c[0][1];
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
