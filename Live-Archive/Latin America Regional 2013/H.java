import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;

public class H {
  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    EscondeEsconde solver = new EscondeEsconde();
    int testCase = 1;
    while (in.hasInput()) {
      solver.solve(testCase++, in, out);
    }
    out.close();
  }
  
  static class EscondeEsconde {    
    final int bias = (int) 1e9;
    OrdPt refpoint, extend;
    
    class OrdPt extends Point implements Comparable<OrdPt> {
      public OrdPt(int x, int y) {
        super(x, y);
      }
          
      public boolean equals(OrdPt other) {
        return this.x == other.x && this.y == other.y;
      }
        
      public int compareTo(OrdPt other) {
        long dx1 = this.x - refpoint.x;
        long dy1 = this.y - refpoint.y;
        long dx2 = other.x - refpoint.x;
        long dy2 = other.y - refpoint.y;
        if (dy1 == 0 && dx1 > 0 || dy1 > 0 && dy2 < 0) {
          return -1;
        }
        if (dy2 == 0 && dx2 > 0 || dy1 < 0 && dy2 > 0) {
          return 1;
        }
        int o = orientation(this, refpoint, other);
        if (o != 0) {
          return o;
        }
        long d1 = dx1 * dx1 + dy1 * dy1;
        long d2 = dx2 * dx2 + dy2 * dy2;
        return Long.compare(d1, d2);
      }
    }

    class Segment implements Comparable<Segment> {
      OrdPt a, b;
      
      Segment(OrdPt a, OrdPt b) {
        this.a = a;
        this.b = b;
      }
      
      public int compareTo(Segment other) {
        if (this.a.equals(other.a) && this.b.equals(other.b)) {
          return 0;
        }
        if (!doIntersect(other.a, other.b, refpoint, this.a) && !doIntersect(other.a, other.b, refpoint, this.b)) {
          return -1;
        }
        return 1;
      }
      
      public String toString() {
        return "[" + a.toString() + ", " + b.toString() + "]";
      }
    }
    
    void solve(int testNumber, InputReader in, PrintWriter out) {
      int s = in.nextInt();
      int k = in.nextInt();
      int w = in.nextInt();
      OrdPt[] ref = new OrdPt[s];
      OrdPt[] point = new OrdPt[k + 2 * w];
      
      for (int i = 0; i < k; ++i) {
        int x = in.nextInt();
        int y = in.nextInt();
        point[i] = new OrdPt(x, y);
        if (i < s) {
          ref[i] = point[i];
        }
      }
      Map<OrdPt, Segment> segMap = new HashMap<OrdPt, Segment>();
      
      for (int i = 0; i < w; ++i) {
        int x1 = in.nextInt();
        int y1 = in.nextInt();
        int x2 = in.nextInt();
        int y2 = in.nextInt();
        point[2 * i + k] = new OrdPt(x1, y1);
        point[2 * i + k + 1] = new OrdPt(x2, y2);
        Segment segment = new Segment(point[2 * i + k], point[2 * i + k + 1]);
        segMap.put(point[2 * i + k], segment);
        segMap.put(point[2 * i + k + 1], segment);
      }
      for (int i = 0; i < s; ++i) {
        setup(ref[i]);
        int ans = count(point, segMap);
        out.println(ans);
      }
    }
    
    void setup(OrdPt p) {
      refpoint = p;
      extend = new OrdPt(p.x + bias, p.y);
    }
    
    int count(OrdPt[] point, Map<OrdPt, Segment> segMap) {
      Arrays.sort(point);
      TreeSet<Segment> hold = new TreeSet<Segment>();
      
      for (int i = 0; i < point.length; ++i) {
        Segment segment = null;
        if (segMap.containsKey(point[i])) {
          segment = segMap.get(point[i]);
        }
        if (segment != null && !hold.contains(segment) && doIntersect(segment.a, segment.b, refpoint, extend)) {
          hold.add(segment);
        }
      }
      int ans = 0;
      Deque<Segment> pre = new ArrayDeque<Segment>();
      Deque<OrdPt> thisRef = new ArrayDeque<OrdPt>();
      Deque<Segment> post = new ArrayDeque<Segment>();
      
      for (int i = 0; i <= point.length; ++i) {
        if (i < point.length && refpoint.equals(point[i])) {
          continue;
        }
        if (i == point.length || i > 1 && point[i].compareTo(point[i - 1]) != 0) {
          while (!pre.isEmpty()) {
            hold.add(pre.poll());
          }
          while (!thisRef.isEmpty()) {
            if (hold.isEmpty()) {
              ++ans;
            } else {
              Segment segment = hold.first();
              if (!doIntersect(segment.a, segment.b, refpoint, thisRef.peek())) {
                ++ans;
              }
            }
            thisRef.poll();
          }
          while (!post.isEmpty()) {
            hold.remove(post.poll());
          }
        }
        if (i == point.length) {
          break;
        }
        Segment segment = null;
        if (segMap.containsKey(point[i])) {
          segment = segMap.get(point[i]);
        }
        if (segment != null) {
          if (!hold.contains(segment)) {
            pre.add(segment);
          } else {
            post.add(segment);
          }
        } else {
          thisRef.add(point[i]);
        }
      }
      return ans;
    }
    
    long det(OrdPt a, OrdPt b, OrdPt c) {
      long dx1 = b.x - a.x;
      long dy1 = b.y - a.y;
      long dx2 = c.x - a.x;
      long dy2 = c.y - a.y;
      return dx1 * dy2 - dx2 * dy1;
    }
    
    boolean onSegment(OrdPt a, OrdPt b, OrdPt c) {
      if (b.x <= Math.max(a.x, c.x) && b.x >= Math.min(a.x, c.x) && b.y <= Math.max(a.y, c.y) && b.y >= Math.min(a.y, c.y)) {
        return true;
      }
      return false;
    }
    
    boolean doIntersect(OrdPt a, OrdPt b, OrdPt c, OrdPt d) {
      int o1 = orientation(a, b, c);
      int o2 = orientation(a, b, d);
      int o3 = orientation(c, d, a);
      int o4 = orientation(c, d, b);
      
      if (o1 != o2 && o3 != o4) {
        return true;
      }
      
      /*
      if (o1 == 0 && onSegment(a, c, b)) {
        return true;
      }
      if (o2 == 0 && onSegment(a, d, b)) {
        return true;
      }
      if (o3 == 0 && onSegment(c, a, d)) {
        return true;
      }
      if (o4 == 0 && onSegment(c, b, d)) {
        return true;
      }
      */
      return false;
    }
    
    int orientation(OrdPt a, OrdPt b, OrdPt c) {
      long dx1 = b.x - a.x;
      long dy1 = b.y - a.y;
      long dx2 = c.x - b.x;
      long dy2 = c.y - b.y;
      long val = dx2 * dy1 - dy2 * dx1;
      if (val == 0L) {
        return 0;
      }
      return val > 0L ? -1 : 1; // -1 - sentido horário, 1 - sentido anti-horário
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
