// Maior Palíndromo Comum Você Deve Encontrar
//import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
     
//class Main {
class B {
  final double inf = Double.POSITIVE_INFINITY;
  final int eof = StreamTokenizer.TT_EOF;
  int cases, ans, n, pos, x, size, index, tableIndex, mask, comparisonMask, minVal, lastMinVal, a, b;
  int[] table, tArray, sArray, lArray, now;
  boolean[] preTable;
  CharSequence cs;
  StringBuilder sb;
  Queue<Long> queue;
  List<Integer> indexList;
       
  int[] buildSuffixArray(final CharSequence cs) {
    int n, s1;
    int[] sa, rank, r, s, count;
    Integer[] order;
     
    n = cs.length();
    order = new Integer[n];
    for (int i = 0; i < n; i++) {
      order[i] = n - 1 - i;
    }    
    Arrays.sort(order, new Comparator<Integer>() {
      @Override
      public int compare(Integer a, Integer b) {
        return Character.compare(cs.charAt(a), cs.charAt(b));
      }
    });
    sa = new int[n];
    rank = new int[n];
    for (int i = 0; i < n; i++) {
      sa[i] = order[i];
      rank[i] = cs.charAt(i);
    } 
    for (int len = 1; len < n; len *= 2) {
      r = rank.clone();
      for (int i = 0; i < n; i++) {
        rank[sa[i]] = i > 0 && r[sa[i - 1]] == r[sa[i]] && sa[i - 1] + len < n && r[sa[i - 1] + len / 2] == r[sa[i] + len / 2] ? rank[sa[i - 1]] : i;
      }
     count = new int[n];
      for (int i = 0; i < n; i++) {
        count[i] = i;
      }
      s = sa.clone();
      for (int i = 0; i < n; i++) {
        s1 = s[i] - len;
        if (s1 >= 0) {
          sa[count[rank[s1]]++] = s1;
        }
      }
    }
    return sa;
  } 
     
  int[] buildLCPArray(int[] sa, CharSequence cs) {
    int n;
    int[] rank, lcp;
        
//    n = sa.length;
    n = sa.length - 3;
    rank = new int[n];     
    for (int i = 0; i < n; i++) {
      rank[sa[i]] = i;
    }
    lcp = new int[n - 1];
    for (int i = 0, h = 0; i < n; i++) {
      if (rank[i] < n - 1) {
        for (int j = sa[rank[i] + 1]; Math.max(i, j) + h < cs.length() && cs.charAt(i + h) == cs.charAt(j + h); ++h);
        lcp[rank[i]] = h;
        if (h > 0) {
          --h;
        }
      }
    }
    return lcp;
  }
   
  // http://algo2.iti.kit.edu/documents/jacm05-revised.pdf
 
  boolean leq(int a1, int a2, int b1, int b2) { // lexicographic order
    return(a1 < b1 || a1 == b1 && a2 <= b2); } // for pairs
  boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
    return(a1 < b1 || a1 == b1 && leq(a2,a3, b2,b3)); } // and triples
  // stably sort a[0..n-1] to b[0..n-1] with keys in 0..K from r
  void radixPass(int[] a, int[] b, int[] r, int n, int K, int offset) { // count occurrences
    int[] c = new int[K + 1]; // counter array
    for (int i = 0; i <= K; i++) c[i] = 0; // reset counters
    for (int i = 0; i < n; i++) c[r[a[i] + offset]]++; // count occurrences
    for (int i = 0, sum = 0; i <= K; i++) { // exclusive prefix sums
      int t = c[i]; c[i] = sum; sum += t; }
    for (int i = 0; i < n; i++) b[c[r[a[i] + offset]]++] = a[i]; // sort
  }
  // find the suffix array SA of T[0..n-1] in {1..K}^n
  // require T[n]=T[n+1]=T[n+2]=0, n>=2
  void suffixArray(int[] T, int[] SA, int n, int K) {
    int n0=(n+2)/3, n1=(n+1)/3, n2=n/3, n02=n0+n2;
    int[] R = new int[n02 + 3]; R[n02]= R[n02+1]= R[n02+2]=0;
    int[] SA12 = new int[n02 + 3]; SA12[n02]=SA12[n02+1]=SA12[n02+2]=0;
    int[] R0 = new int[n0];
    int[] SA0 = new int[n0];
  //******* Step 0: Construct sample ********
  // generate positions of mod 1 and mod 2 suffixes
  // the "+(n0-n1)" adds a dummy mod 1 suffix if n%3 == 1
    for (int i=0, j=0; i < n+(n0-n1); i++) if (i%3 != 0) R[j++] = i;
  //******* Step 1: Sort sample suffixes ********
  // lsb radix sort the mod 1 and mod 2 triples
    radixPass(R , SA12, T, n02, K,2);
    radixPass(SA12, R , T, n02, K,1);
    radixPass(R , SA12, T , n02, K,0);
  // find lexicographic names of triples and
  // write them to correct places in R
    int name = 0, c0 = -1, c1 = -1, c2 = -1;
    for (int i = 0; i < n02; i++) {
      if (T[SA12[i]] != c0 || T[SA12[i]+1] != c1 || T[SA12[i]+2] != c2) {
        name++; c0 = T[SA12[i]]; c1 = T[SA12[i]+1]; c2 = T[SA12[i]+2]; }
        if (SA12[i] % 3 == 1) { R[SA12[i]/3] = name; // write to R1
      } else { R[SA12[i]/3 + n0] = name; } // write to R2
    }
  // recurse if names are not yet unique
    if (name < n02) {
      suffixArray(R, SA12, n02, name);
  // store unique names in R using the suffix array
      for (int i = 0; i < n02; i++) R[SA12[i]] = i + 1;
    } else { // generate the suffix array of R directly
      for (int i = 0; i < n02; i++) SA12[R[i] - 1] = i;
    }
  //******* Step 2: Sort nonsample suffixes ********
  // stably sort the mod 0 suffixes from SA12 by their first character
    for (int i=0, j=0; i < n02; i++) {
      if (SA12[i] < n0) R0[j++] = 3*SA12[i];
    }
    radixPass(R0, SA0, T, n0, K, 0);
  //******* Step 3: Merge ********
  // merge sorted SA0 suffixes and sorted SA12 suffixes
    for (int p=0, t=n0-n1, k=0; k < n; k++) {
      int i = (SA12[t] < n0 ? SA12[t] * 3 + 1 : (SA12[t] - n0) * 3 + 2); // pos of current offset 12 suffix
      int j = SA0[p]; // pos of current offset 0 suffix
      if (SA12[t] < n0 ? // different compares for mod 1 and mod 2 suffixes
      leq(T[i], R[SA12[t] + n0], T[j], R[j/3]) :
      leq(T[i],T[i+1],R[SA12[t]-n0+1], T[j],T[j+1],R[j/3+n0])) { // suffix from SA12 is smaller
        SA[k] = i; t++;
        if (t == n02) {// done --- only SA0 suffixes left
          for (k++; p < n0; p++, k++) SA[k] = SA0[p];
        }
      } else { // suffix from SA0 is smaller
        SA[k] = j; p++;
        if (p == n0) { // done --- only SA12 suffixes left
          for (k++; t < n02; t++, k++) SA[k] = (SA12[t] < n0 ? SA12[t] * 3 + 1 : (SA12[t] - n0) * 3 + 2);
        }
      }
    }
  }
   
// http://en.wikipedia.org/wiki/Longest_palindromic_substring
   
  String findLongestPalindrome(String s) {
    if (s==null || s.length()==0)
      return "";
   
    char[] s2 = addBoundaries(s.toCharArray());
    int[] p = new int[s2.length]; 
    int c = 0, r = 0; // Here the first element in s2 has been processed.
    int m = 0, n = 0; // The walking indices to compare if two elements are the same
    for (int i = 1; i<s2.length; i++) {
      if (i>r) {
        p[i] = 0; m = i-1; n = i+1;
      } else {
        int i2 = c*2-i;
        if (p[i2]<(r-i)) {
          p[i] = p[i2];
          m = -1; // This signals bypassing the while loop below. 
        } else {
          p[i] = r-i;
          n = r+1; m = i*2-n;
        }
      }
      while (m>=0 && n<s2.length && s2[m]==s2[n]) {
        p[i]++; m--; n++;
      }
      if ((i+p[i])>r) {
        c = i; r = i+p[i];
      }
    }
    int len = 0; c = 0;
    for (int i = 1; i<s2.length; i++) {
      if (len<p[i]) {
        len = p[i]; c = i;
      }
    }
    char[] ss = Arrays.copyOfRange(s2, c-len, c+len+1);
    return String.valueOf(removeBoundaries(ss));
  }
   
  char[] addBoundaries(char[] cs) {
    if (cs==null || cs.length==0)
      return "||".toCharArray();
   
    char[] cs2 = new char[cs.length*2+1];
    for (int i = 0; i<(cs2.length-1); i = i+2) {
      cs2[i] = '|';
      cs2[i+1] = cs[i/2];
    }
    cs2[cs2.length-1] = '|';
    return cs2;
  }
   
  char[] removeBoundaries(char[] cs) {
    if (cs==null || cs.length<3)
      return "".toCharArray();
   
    char[] cs2 = new char[(cs.length-1)/2];
    for (int i = 0; i<cs2.length; i++) {
      cs2[i] = cs[i*2+1];
    }
    return cs2;
  }
       
  boolean isPalindrome(CharSequence cs, int index, int size) {
    int span;
      
    span = 2 * index + size - 1;
    for (int i = index; i < index + size / 2; ++i) {
      if (cs.charAt(i) != cs.charAt(span - i)) {
        return false;
      }
    }
    return true;
  }
       
  void execute() throws Exception {
    cases = 1;
           
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    StreamTokenizer stream = new StreamTokenizer(reader);
    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
           
//    stream.resetSyntax();
//    stream.ordinaryChars(0, 255);
//    stream.wordChars('!', '~');
//    stream.whitespaceChars('\n', ' ');
    stream.eolIsSignificant(false);
           
    try {
      preTable = new boolean[256];
      table = new int[256];
      while (stream.nextToken() != eof) {
        n = (int) stream.nval;
        index = 0;
        sb = new StringBuilder();
        indexList = new ArrayList<Integer>();
        Arrays.fill(preTable, false);
        for (int i = 0; i < n; ++i) {
          stream.nextToken();
          sb.append(stream.sval);
          sb.append((char) index);
          for (int j = 0; j < stream.sval.length(); ++j) {
            preTable[stream.sval.charAt(j)] = true;
          }
          preTable[index] = true;
          for (int j = 0; j < stream.sval.length() + 1; ++j) {
            indexList.add(index);
          }
          ++index;
        }
        ans = 0;
        if (n > 0) {
          if (n > 1) {
            sb.append((char) index - 1);
            sb.append((char) index - 1);
            sb.append((char) index - 1);
            indexList.add(index - 1);
            indexList.add(index - 1);
            indexList.add(index - 1);
            tableIndex = 0;
            for (int i = 0; i < 256; ++i) {
              if (preTable[i]) {
                table[i] = tableIndex++;
              }
            }
            cs = sb.toString().subSequence(0, sb.length());
            tArray = new int[cs.length()];
            sArray = new int[cs.length()];
            for (int i = 0; i < cs.length(); ++i) {
              tArray[i] = table[cs.charAt(i)];
            }
//          sArray = buildSuffixArray(cs);
            suffixArray(tArray, sArray, tArray.length - 3, tableIndex);
            lArray = buildLCPArray(sArray, cs);
            mask = 0;
            comparisonMask = (1 << n) - 1;
            lastMinVal = -1;
            now = new int[n];
            queue = new PriorityQueue<Long>();
            for (int i = 0; i < lArray.length; ++i) {
              if (cs.charAt(sArray[i]) <= '\11') {
                continue;
              }
              x = lArray[i];
              if (x == 0) {
                if (mask != 0) {
                  mask = 0;
                  if (lastMinVal != -1) {
                    lastMinVal = -1;
                  }
                }
                continue;
              }
              a = indexList.get(sArray[i]);
              b = indexList.get(sArray[i + 1]);
              mask |= 1 << a | 1 << b;
              now[a] = x;
              now[b] = x;
              if (mask == comparisonMask) {
                minVal = (int) inf;
                for (int j = 0; j < n; ++j) {
                  minVal = Math.min(minVal, now[j]);
                }
                if (minVal != lastMinVal) {
                  queue.add((long) ~minVal << 32 | sArray[i]);
                  lastMinVal = minVal; 
                }
              }
            }
            while (!queue.isEmpty()) {
              size = (int) (~queue.peek() >> 32);
              pos = (int) (queue.poll() & 0xffffffff);
              if (isPalindrome(cs, pos, size)) {
                ans = size;
                break;
              }
            }
          } else {
            ans = findLongestPalindrome(sb.toString()).length();
          }
        }
        writer.println(ans);
        ++cases;
      }
    }
    catch (Exception e) {
      System.err.println("Problema no caso " + cases);
      e.printStackTrace();
      throw new Exception();
    }
    finally {
      writer.close();
    }
  }
             
  public static void main (String[] args) throws Exception {
    (new Main()).execute();
  }
}
