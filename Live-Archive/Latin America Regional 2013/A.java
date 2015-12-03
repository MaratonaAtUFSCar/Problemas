// Attacking Rooks
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
     
//public class Main {
public class A {
  int maxm, maxn1, maxn2, edges, v, w, ans, n, mark;
  boolean prev, okay;
  boolean[] array;
  int[][] rows, cols;
  char[][] tab;
  List<List<Integer>> graph;
  String s;
 
  int maxMatching(List<List<Integer>> graph, int n2) {
    int n1, matches;
    int[] matching;
    
    n1 = graph.size();
    matching = new int[n2];
    Arrays.fill(matching, -1);
    matches = 0;
    for (int u = 0; u < n1; ++u) {
      if (findPath(graph, u, matching, new boolean[n1]))
        ++matches;
    }
    return matches;
  }
 
  boolean findPath(List<List<Integer>> graph, int u1, int[] matching, boolean[] vis) {
    int u2;
     
    vis[u1] = true;
    for (int v : graph.get(u1)) {
      u2 = matching[v];
      if (u2 == -1 || !vis[u2] && findPath(graph, u2, matching, vis)) {
        matching[v] = u1;
        return true;
      }
    }
    return false;
  }
 
  void execute() throws Exception {
    int cases = 1, remainingTests;
           
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    StreamTokenizer stream = new StreamTokenizer(reader);
    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    StringTokenizer st;
           
//    stream.resetSyntax();
//    stream.ordinaryChars(0, 255);
//    stream.wordChars('!', '~');
//    stream.whitespaceChars('\n', ' ');
    stream.eolIsSignificant(false);
           
    try {
      while ((s = reader.readLine()) != null && !s.equals("")) {
        n = Integer.parseInt(s);
        tab = new char[n][];
        for (int i = 0; i < n; ++i) {
          tab[i] = reader.readLine().toCharArray();
        }
        rows = new int[n][n];
        cols = new int[n][n];
        mark = 1;
        prev = false;
        for (int i = 0; i < n; ++i) {
          for (int j = 0; j < n; ++j)
            if (tab[i][j] == '.') {
              prev = true;
              rows[i][j] = mark;
            } else {
              if (prev) {
                prev = false;
                ++mark;
              }
            }
          if (prev) {
            prev = false;
            ++mark;
          }
        }
        maxn1 = mark - 1;
        for (int i = 0; i < n; ++i) {
          for (int j = 0; j < n; ++j) {
              if (tab[j][i] == '.') {
                prev = true;
                cols[j][i] = mark;
              } else {
                if (prev) {
                  prev = false;
                  ++mark;
                }
              }
          }
          if (prev) {
            prev = false;
            ++mark;
          }
        }
        maxn2 = mark - 1;
        if (maxn1 == 0 || maxn2 == 0) {
          ans = 0;
        } else {
          edges = 0;
          for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
              if (rows[i][j] != 0) {
                  ++edges;
              }
            }
          }
          graph = new ArrayList<List<Integer>>();
          for (int i = 0; i < maxn1; ++i) {
            graph.add(new ArrayList<Integer>());
          }
          for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
              if (rows[i][j] != 0) {
                v = rows[i][j] - 1;
                w = cols[i][j] - 1 - maxn1;
                graph.get(v).add(w);
              }
            }
          }
          ans = maxMatching(graph, maxn2 - maxn1);
        }
        writer.println(ans);
        cases++;
      }
    } catch (Exception e) {
      System.err.println("Problema no caso " + cases);
      e.printStackTrace();
      throw new Exception();
    } finally {
      writer.close();
    }
  }
             
  public static void main (String[] args) throws Exception {
    (new Main()).execute();
  }
}
