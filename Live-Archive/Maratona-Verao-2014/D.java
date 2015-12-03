// Curo Factories
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
  
//class Main
class D
{
  int n, days, maxDays, cycleSize, cycleSum, cycleCount, row, col, x, varA, varB, varC;
  boolean isNewCycle;
  int[] g, c, a;
  boolean[] marked;
  List<Integer> list;
  List<List<Integer>> table;
  Map<Integer, Integer> map;
  
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
      while (stream.nextToken() != StreamTokenizer.TT_EOF)
      {
        maxDays = cycleCount = 0;
        n = (int) stream.nval;
        g = new int[n];
        c = new int[n];
        a = new int[n];
        marked = new boolean[n];
        list = new ArrayList<Integer>();
        table = new ArrayList<List<Integer>>();
        map = new TreeMap<Integer, Integer>();
        for (int i = 0; i < n; ++i)
        {
          stream.nextToken();
          g[i] = (int) stream.nval;
        }
        for (int i = 0; i < n; ++i)
        {
          stream.nextToken();
          c[i] = (int) stream.nval;
        }
        for (int i = 0; i < n; ++i)
        {
          stream.nextToken();
          a[i] = (int) stream.nval;
        }
        for (int i = 0; i < n; ++i)
        {
          cycleSum = cycleSize = 0;
          x = i;
          isNewCycle = false;
          while (!marked[x])
          {
            if (!isNewCycle)
            {
              isNewCycle = true;
              table.add(new ArrayList<Integer>());
              ++cycleCount;
            }
            marked[x] = true;
            cycleSum += c[x];
            table.get(cycleCount - 1).add(c[x]);
            map.put(x, (cycleCount - 1) << 16 | cycleSize);
            x = g[x];
            ++cycleSize;
          }
          if (isNewCycle)
          {
            list.add(cycleSum);
          }
        }
        for (int i = 0; i < n; ++i)
        {
          row = map.get(i) >> 16;
          col = map.get(i) & 0xffff;
          varA = a[i] / list.get(row);
          varB = varA * list.get(row);
          varC = a[i] - varB;
          days = varA * table.get(row).size();
          for (int j = 0; varC > 0; ++j)
          {
            varC -= table.get(row).get((col + j) % table.get(row).size());
            ++days;
          }
          maxDays = Math.max(maxDays, days);
        }
        writer.println(maxDays);
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
