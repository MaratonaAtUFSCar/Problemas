// Contando Ambiguidades
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
  
//public class Main
public class H
{
  final long min = -1, max = 1;
  final double inf = Double.POSITIVE_INFINITY, eps = 1e-9;
  final int eof = StreamTokenizer.TT_EOF, ring = (int) 1e9 + 7;
  int nt, d, u, ans;
  int[] dp;
  String s;
  Set<String> set;
   
  <T> List<List<T>> newNestedList(List<List<T>> l, int n)
  {
    l = new ArrayList<List<T>>();
    for (int i = 0; i < n; ++i) l.add(new ArrayList<T>());
    return (l);
  }
   
  int solve(int l)
  {
    if (dp[l] != -1)
    {
      return dp[l];
    }
    int ans = 0;
    if (l >= s.length())
    {
      return dp[l] = 1;
    }
    for (int r = l + 1; r <= s.length(); ++r)
    {
      if (set.contains(s.substring(l, r)))
      {
        ans += solve(r);
        ans %= ring;
      }
    }
    return dp[l] = ans;
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
      while (stream.nextToken() != eof)
      {
        d = (int) stream.nval;
        set = new HashSet<String>();
        for (int i = 0; i < d; ++i)
        {
          stream.nextToken();
          set.add(stream.sval);
        }
        stream.nextToken();
        u = (int) stream.nval;
        for (int i = 0; i < u; ++i)
        {
          stream.nextToken();
          s = stream.sval;
          dp = new int[s.length() + 1];
          Arrays.fill(dp, -1);
          ans = solve(0);
          writer.println(ans);
        }
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
