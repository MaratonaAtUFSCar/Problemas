// Raio Laser
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Visitante
 */
//public class Main {
public class G {
 
    static StringTokenizer parse() {
        try {
            int available = System.in.available();
            if (available == 0) {
                available = 1 << 16;
            }
            byte[] buffer = new byte[available];
            int read = System.in.read(buffer, 0, available);
            return new StringTokenizer((new String(buffer)).substring(0, read));
        } catch (IOException e) {
            return null;
        }
    }
 
    static class PairII implements Comparable<PairII> {
 
        int a, b;
 
        PairII(int a, int b) {
            this.a = a;
            this.b = b;
        }
 
        @Override
        public int compareTo(PairII other) {
            if (this.a != other.a) {
                return this.a - other.a;
            }
            return this.b - other.b;
        }
 
        @Override
        public String toString() {
            return "(" + this.a + ", " + this.b + ")";
        }
    }
 
    static class QuadIIII implements Comparable<QuadIIII> {
 
        int a, b, c, d;
 
        QuadIIII(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
 
        @Override
        public int compareTo(QuadIIII other) {
            if (this.a != other.a) {
                return this.a - other.a;
            }
            if (this.b != other.b) {
                return this.b - other.b;
            }
            if (this.c != other.c) {
                return this.c - other.c;
            }
            return this.d - other.d;
        }
 
        @Override
        public String toString() {
            return "(" + this.a + ", " + this.b + ", " + this.c + ", " + this.d + ")";
        }
    }
 
    static int lastElement(PairII[] array, int startIndex, PairII next) {
        int nextIndex = Arrays.binarySearch(array, startIndex, array.length, next);
        if (nextIndex < 0) {
            nextIndex = ~nextIndex;
        }
        int lastIndex = -1;
        if (nextIndex > 0) {
            lastIndex = nextIndex - 1;
        }
        return lastIndex;
    }
 
    static int solve(List<QuadIIII> list, PairII[] array, int n, int w, boolean xIsFirst, boolean prepare) {
        int previous = -1, diff = 0;
        for (int i = 0; i < n; ++i) {
            if (i == 0 || array[i].a != array[i - 1].a) {
                int last = lastElement(array, 0, new PairII(array[i].a + w, Integer.MAX_VALUE));
                for (int j = previous + 1; j <= last; ++j) {
                    if (j == last || array[j].a != array[j + 1].a) {
                        // Cada moldura de limites (x1, y1, x2, y2) pode ser identificada como a
                        // detentora unívoca de um dado subconjunto
                        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = xIsFirst ? array[j].a : Integer.MIN_VALUE, maxY = xIsFirst ? Integer.MIN_VALUE : array[j].a;
                        for (int k = j; k >= i; --k) {
                            if (xIsFirst) {
                                minX = array[k].a;
                                minY = Math.min(minY, array[k].b);
                                maxY = Math.max(maxY, array[k].b);
                            } else {
                                minX = Math.min(minX, array[k].b);
                                minY = array[k].a;
                                maxX = Math.max(maxX, array[k].b);
                            }
                            if (k == i || array[k].a != array[k - 1].a) {
                                QuadIIII elem = new QuadIIII(minX, minY, maxX, maxY);
                                if (prepare) {
                                    ++diff;
                                    list.add(elem);
                                } else {
                                    if (Collections.binarySearch(list, elem) < 0) {
                                        ++diff;
                                    }
                                }
                            }
                        }
                    }
                }
                previous = last;
            }
        }
        if (prepare) {
            Collections.sort(list);
        }
        return diff;
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        StringTokenizer tokenizer;
 
        try {
            tokenizer = parse();
            while (true) {
                int n = Integer.parseInt(tokenizer.nextToken());
                int w = Integer.parseInt(tokenizer.nextToken());
                if (n == 0 && w == 0) {
                    break;
                }
                PairII[] xy = new PairII[n];
                PairII[] yx = new PairII[n];
                for (int i = 0; i < n; ++i) {
                    int a = Integer.parseInt(tokenizer.nextToken());
                    int b = Integer.parseInt(tokenizer.nextToken());
                    xy[i] = new PairII(a, b);
                    yx[i] = new PairII(b, a);
                }
                Arrays.sort(xy);
                Arrays.sort(yx);
                List<QuadIIII> list = new ArrayList<QuadIIII>();
                int ans = solve(list, xy, n, w, true, true);
                ans += solve(list, yx, n, w, false, false);
                writer.println(ans);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //
        } finally {
            writer.close();
        }
    }
}
