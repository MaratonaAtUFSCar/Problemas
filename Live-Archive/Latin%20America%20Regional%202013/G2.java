// -*- coding: utf-8 -*-
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;

public class G2 {
  public static void main(String[] args) {
    InputStream inputStream;
    if (args.length > 0 && args[0].equals("devTesting")) {
      try {
        inputStream = new FileInputStream(args[1]);
      } catch(FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    } else {
      inputStream = System.in;
    }
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    SubaOsUltras solver = new SubaOsUltras();
    int testCase = 1;
    while (in.hasInput()) {
      solver.solve(testCase++, in, out);
    }
    out.close();
  }
  
  static class SubaOsUltras {
    final int minp = 150000;
    int n;
    int[] profile;
    
    void solve(int testNumber, InputReader in, PrintWriter out) {
      n = in.nextInt();
      profile = new int[n];
      int maxval = 0;
      for (int i = 0; i < n; ++i) {
        profile[i] = in.nextInt();
        maxval = Math.max(maxval, profile[i]);
      }
      int[][] table = new int[n][17];
      pre(table, profile, n);
      int[] left = makeLeft(profile);
      int[] right = makeRight(profile);
      boolean isFirst = true;
      for (int i = 0; i < n; ++i) {
        if (profile[i] >= minp) {
          boolean ok = true;
          if (profile[i] != maxval) {
            ok = false;
            if ((left[i] == -1 
              || profile[i] - profile[rmq(table, profile, left[i], i)] >= minp) 
              && (right[i] == n 
              || profile[i] - profile[rmq(table, profile, i, right[i])] >= minp)) {
              ok = true;
            }
          }
          if (ok) {
            if (!isFirst) {
              out.print(' ');
            }
            out.print(i + 1);
            if (isFirst) {
              isFirst = false;
            }
          }
        }
      }
      out.println();
    }
    
    int[] makeLeft(int[] profile) {
      int n = profile.length;
      int[] left = new int[n];
      left[0] = -1;
      for (int i = 1; i < n; ++i) {
        left[i] = i - 1;
        while (left[i] > -1 && profile[i] >= profile[left[i]]) {
          left[i] = left[left[i]];
        }
      }
      return left;
    }

    int[] makeRight(int[] profile) {
      int n = profile.length;
      int[] right = new int[n];
      right[n - 1] = n;
      for (int i = n - 2; i >= 0; --i) {
        right[i] = i + 1;
        while (right[i] < n && profile[i] >= profile[right[i]]) {
          right[i] = right[right[i]];
        }
      }
      return right;
    }
    
    void pre(int[][] m, int[] o, int n) {
      for (int i = 0; i < n; ++i)
        m[i][0] = i;
      for (int j = 1; 1 << j - 1 < n; ++j)
        for (int i = 0; i + (1 << j) - 1 < n; ++i)
          if (o[m[i][j - 1]] < o[m[i + (1 << (j - 1))][j - 1]])
            m[i][j] = m[i][j - 1];
          else
            m[i][j] = m[i + (1 << (j - 1))][j - 1];
    }
      
    int rmq(int[][] m, int[] o, int a, int b) {
      int d = Integer.highestOneBit(b - a + 1);
      int c = Integer.bitCount(d - 1);
      if (o[m[a][c]] < o[m[b - d + 1][c]])
        return m[a][c];
      else
        return m[b - d + 1][c];
    }
        
  }
      
  static class InputReader {
    public BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
      reader = new BufferedReader(new InputStreamReader(stream));
      tokenizer = null;
    }

    public String next() {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        try {
          tokenizer = new StringTokenizer(reader.readLine());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return tokenizer.nextToken();
    }
    
    public String nextLine() {
      try {
        return reader.readLine();
      } catch(IOException e) {
        throw new RuntimeException(e);
      }
    }

    public int nextInt() {
      return Integer.parseInt(next());
    }

    public long nextLong() {
      return Long.parseLong(next());
    }
    
    public double nextDouble() {
      return Double.parseDouble(next());
    }

    public boolean hasInput() {
      try {
        if (tokenizer != null && tokenizer.hasMoreTokens()) {
          return true;
        }
        reader.mark(1);
        int ch = reader.read();
        if (ch != -1) {
          reader.reset();
          return true;
        }
        return false;
      } catch(IOException e) {
        throw new RuntimeException(e);
      }
    }
    
  }
}
