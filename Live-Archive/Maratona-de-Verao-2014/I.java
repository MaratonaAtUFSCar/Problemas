// O Passeio Turístico
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
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
public class I {
 
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
 
    static class TripleIIB implements Comparable<TripleIIB> {
 
        int a, b;
        byte c;
 
        public TripleIIB(int a, int b, byte c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
 
        @Override
        public int compareTo(TripleIIB other) {
            if (this.a != other.a) {
                return this.a - other.a;
            }
            if (this.b != other.b) {
                return this.b - other.b;
            }
            return this.c - other.c;
        }
    }
 
    static long pack(int hi, int lo) {
        return (long) hi << 32 | lo;
    }
 
    static int unpackHi(long pack) {
        return (int) (pack >> 32);
    }
 
    static int unpackLo(long pack) {
        return (int) (pack & (1L << 32) - 1);
    }
 
    static void preProcess(TripleIIB[] array, Set<Integer> set, int n, int idx, int mask, int acc) {
        if (array[idx].b == n) {
            set.add(acc);
            return;
        }
        int next = Arrays.binarySearch(array, idx + 1, array.length, new TripleIIB(array[idx].a + 1, 0, (byte) 0));
        if (next < 0) {
            next = ~next;
        }
        if (next < array.length && array[idx].b >= array[next].b) {
            next = Arrays.binarySearch(array, next, array.length, new TripleIIB(array[next].a, array[idx].b + 1, (byte) 0));
            if (next < 0) {
                next = ~next;
            }
        }
        int newMask = mask;
        for (int i = next; i < array.length && array[i].a <= array[idx].b + 1; ++i) {
            newMask |= 1 << i;
        }
        for (int i = next; i < array.length && array[i].a <= array[idx].b + 1; ++i) {
            if ((1 << i & mask) == 0) {
                preProcess(array, set, n, i, newMask, acc + array[i].c);
            }
        }
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
            int nt = Integer.parseInt(tokenizer.nextToken());
            for (int tc = 1; tc <= nt; ++tc) {
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());
                int k = Integer.parseInt(tokenizer.nextToken());
                TripleIIB[] bin = new TripleIIB[m];
                for (int i = 0; i < m; ++i) {
                    int a = Integer.parseInt(tokenizer.nextToken());
                    int b = Integer.parseInt(tokenizer.nextToken());
                    byte c = Byte.parseByte(tokenizer.nextToken());
                    bin[i] = new TripleIIB(a, b, c);
                }
                Arrays.sort(bin);
                TreeSet<Integer> value = new TreeSet<Integer>();
                int mask = 0;
                for (int i = 0; i < bin.length && bin[i].a == 1; ++i) {
                    mask |= 1 << i;
                }
                for (int i = 0; i < bin.length && bin[i].a == 1; ++i) {
                    preProcess(bin, value, n, i, mask, bin[i].c);
                }
                long total = 0;
                for (int i = 0; i < k; ++i) {
                    int x = Integer.parseInt(tokenizer.nextToken());
                    int y = Integer.parseInt(tokenizer.nextToken());
                    int vv;
                    int inc = -1;
                    for (int ptr = Math.max(x, value.first()); ptr <= y; ptr += inc) {
                        vv = value.floor(ptr);
                        inc = Math.min(value.ceiling(vv + 1) == null ? y : value.ceiling(vv + 1) - 1, y) - ptr + 1;
                        total += (long) inc * vv;
                    }
                }
                writer.println("Case #" + (tc) + ": " + total);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            //
        } finally {
            writer.close();
        }
    }
}
