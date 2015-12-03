// Eleven
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
  
//public class Main
public class E
{
  final double inf = Double.POSITIVE_INFINITY;
  final int eof = StreamTokenizer.TT_EOF, ring = 1000000007;
  int ans, odd, even, x, n;
  int[] freqs;
  long[][] pp;
  int[][][] dp;
  String s;
   
  <T> List<List<T>> newNestedList(List<List<T>> l, int n)
  {
    l = new ArrayList<List<T>>();
    for (int i = 0; i < n; ++i) l.add(new ArrayList<T>());
    return (l);
  }
 
    void pascal(int n)
    {
        pp = new long[n + 1][n + 1];
        for (int i = 0; i <= n; ++i)
            for (int j = 0; j <= i; ++j)
            {
                if (j == 0 || j == i) pp[i][j] = 1;
                else pp[i][j] = (pp[i - 1][j] + pp[i - 1][j - 1]) % ring;
            }
    }
 
  int solve(int p, int v, int o, int e)
  {
    long ans, x;
    int size, total, lower, upper;
     
    if (p == 10) return (v == 0 ? 1 : 0);
    if (dp[p][v][o] != -1) return (dp[p][v][o]);
    ans = 0;
    size = o + e;
    total = freqs[p];
    for (int i = 0; i <= Math.min(o, total); ++i)
    {
      lower = i;
      upper = total - i;
      if (upper <= e)
      {
        x = pp[o][lower] * pp[e][upper] % ring;
        ans += x * solve(p + 1, (v + lower * p - upper * p + 110000) % 11, o - lower, e - upper);
        ans %= ring;
      }
    }
    return (dp[p][v][o] = (int) ans);
  }
   
  void execute() throws Exception
  {
    int cases = 1;
      
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    StreamTokenizer stream = new StreamTokenizer(reader);
    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
      
//    stream.resetSyntax();
//    stream.ordinaryChars(0, 255);
//    stream.wordChars('!', '~');
//    stream.whitespaceChars('\n', ' ');
    stream.eolIsSignificant(false);
      
    try
    {
      freqs = new int[10];
      pascal(100);
      while (true)
      {
        s = reader.readLine();
        if (s == null || s.length() == 0) break;
        ans = 0;
        n = s.length();
        for (char i : s.toCharArray())
          ++freqs[i - '0'];
        odd = (n + 1) / 2;
        even = n - odd;
        dp = new int[10][11][odd + 1];
        for (int i = 1; i <= 9; ++i)
        {
          if (freqs[i] == 0) continue;
          if (n % 2 == 0)
          {
            --even;
            x = 11 - i;
          }
          else
          {
            --odd;
            x = i;
          }
          --freqs[i];
          for (int[][] j : dp)
            for (int[] k : j)
              Arrays.fill(k, -1);
          ans = ans + solve(0, x, odd, even);
          ans %= ring;
          ++freqs[i];
          if (n % 2 == 0) ++even;
          else ++odd;
        }
        writer.println(ans);
        Arrays.fill(freqs, 0);
        ++cases;
      }
    }
    catch (Exception e)
    {
      System.err.println("Problema no caso " + cases);
      e.printStackTrace();
      throw new Exception();
    }
    finally
    {
      writer.close();
    }
  }
        
  public static void main (String[] args) throws Exception
  {
    (new Main()).execute();
  }
}
