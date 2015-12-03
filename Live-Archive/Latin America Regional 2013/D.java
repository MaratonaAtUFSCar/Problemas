// Disjoint Water Supply
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
 
//class Main
class D
{
  final int maxVal = 1000;
  int token, c, p, u, v, count;
  int[][] dp;
  List<List<Integer>> connects;
 
  int solve(int x, int y)
  {
    int status;
     
    if (x == y) return (0);
    if (x == 1) return (1);
    if (dp[x][y] != -1) return (dp[x][y]);
     
    status = 0;
     
    for (int i = connects.get(y).size() - 1; i >= 0; --i)
      if (solve((int) Math.min(x, connects.get(y).get(i)), (int) Math.max(x, connects.get(y).get(i))) != 0)
      {
        status = 1;
        break;
      }
    return (dp[x][y] = status);
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
      connects = new ArrayList<List<Integer>>();
 
      for (int i = 0; i <= maxVal; ++i)
        connects.add(new ArrayList<Integer>());
      while (stream.nextToken() != StreamTokenizer.TT_EOF )
      {
        c = (int) stream.nval;
        stream.nextToken();
        p = (int) stream.nval;
         
        count = c - 1;
        dp = new int[c + 1][c + 1];
         
        for (int i = 0; i <= c; ++i)
          if (!connects.get(i).isEmpty()) connects.get(i).clear();
        for (int i = 0; i < p; ++i)
        {
          stream.nextToken();
          u = (int) stream.nval;
          stream.nextToken();
          v = (int) stream.nval;
          connects.get((int) Math.max(u, v)).add((int) Math.min(u, v));
        }
        for (int[] i : dp)
          Arrays.fill(i, -1);        
        for (int i = 2; i <= c; ++i)
          for (int j = i + 1; j <= c; ++j)
            count += solve(i, j);
 
        writer.println(count);
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
