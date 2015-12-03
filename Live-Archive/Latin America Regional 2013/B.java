// Blogger Language
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author freel
 */
//public class Main {
public class B {
 
    static int[] makeArray(String s, String t) {
        int[] a = new int[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            int value = 0;
            if (i + t.length() - 1 < s.length()) {
                for (int j = 0; j < t.length(); ++j) {
                    if (Character.toUpperCase(s.charAt(i + j)) == Character.toUpperCase(t.charAt(j))) {
                        if (s.charAt(i + j) != t.charAt(j)) {
                            value |= 1 << j;
                        }
                    } else {
                        value = -1;
                        break;
                    }
                }
            } else {
                value = -1;
            }
            if (value != -1) {
                a[i] = 1 << value;
            }
        }
        return a;
    }
 
    static class STNode {
 
        int leftIndex;
        int rightIndex;
        int value;
        int lazy;
        boolean color;
        STNode leftNode;
        STNode rightNode;
    }
 
    static STNode constructSegmentTree(int[] a, int l, int r) {
        if (l == r) {
            STNode node = new STNode();
            node.leftIndex = l;
            node.rightIndex = r;
            node.value = a[l];
            node.lazy = 0;
            node.color = false;
            return node;
        }
        int mid = (l + r) / 2;
        STNode leftNode = constructSegmentTree(a, l, mid);
        STNode rightNode = constructSegmentTree(a, mid + 1, r);
        STNode root = new STNode();
        root.leftIndex = leftNode.leftIndex;
        root.rightIndex = rightNode.rightIndex;
        root.value |= leftNode.value;
        root.value |= rightNode.value;
        root.leftNode = leftNode;
        root.rightNode = rightNode;
        return root;
    }
 
    static int modifyMask(int mask, int value) {
        int newMask = 0;
        for (int i = 0; mask != 0; ++i) {
            if ((mask & 1) != 0) {
                int x = i;
                x ^= value;
                newMask |= 1 << x;
            }
            mask >>>= 1;
        }
        return newMask;
    }
 
    static void commitUpdate(STNode root) {
        if (root.color) {
            if (root.lazy != 0) {
                root.value = modifyMask(root.value, root.lazy);
                if (root.leftIndex != root.rightIndex) {
                    root.leftNode.color = root.rightNode.color = true;
                    root.leftNode.lazy ^= root.lazy;
                    root.rightNode.lazy ^= root.lazy;
                }
                root.lazy = 0;
            }
            root.color = false;
        }
    }
 
    static int getValue(STNode root, int l, int r) {
        if (root.value == 0) {
            return 0;
        }
        commitUpdate(root);
        if (root.rightIndex < l || root.leftIndex > r) {
            return 0;
        }
        if (root.leftIndex >= l && root.rightIndex <= r) {
            return root.value;
        }
        int value = getValue(root.leftNode, l, r) | getValue(root.rightNode, l, r);
        root.value = root.leftNode.value | root.rightNode.value;
        return value;
    }
 
    static int processValue(STNode root, int l, int r, int value) {
        int span = Math.min(value, r - l + 1);
        int newValue = (1 << span) - 1;
        int p = l - root.leftIndex;
        int s = (root.rightIndex + span - 1) - r;
        if (p > 0) {
            newValue <<= p;
            newValue &= (1 << value) - 1;
        }
        if (s > 0) {
            newValue >>>= s;
        }
        return newValue;
    }
 
    static void updateValue(STNode root, int l, int r, int value) {
        if (root.value == 0) {
            return;
        }
        commitUpdate(root);
        if (root.rightIndex < Math.max(0, l - value + 1) || root.leftIndex > r) {
            return;
        }
        if (root.leftIndex >= l && root.rightIndex <= r - value + 1) {
            root.color = true;
            root.lazy = (1 << value) - 1;
            commitUpdate(root);
            return;
        }
        if (root.leftIndex == root.rightIndex) {
            root.color = true;
            root.lazy = processValue(root, l, r, value);
            commitUpdate(root);
            return;
        }
        updateValue(root.leftNode, l, r, value);
        updateValue(root.rightNode, l, r, value);
        root.value = root.leftNode.value | root.rightNode.value;
    }
 
    static int maximum(int mask) {
        int ans = -1;
        for (int i = 0; mask != 0; ++i) {
            if ((mask & 1) != 0) {
                ans = Math.max(ans, Integer.bitCount(i));
            }
            mask >>>= 1;
        }
        return ans;
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
//        PrintWriter writer = new PrintWriter("saida.txt", "UTF-8");
        StringTokenizer tokenizer;
 
        try {
            String read;
            while ((read = reader.readLine()) != null) {
                tokenizer = new StringTokenizer(read);
                int n = Integer.parseInt(tokenizer.nextToken());
                String t = tokenizer.nextToken();
                tokenizer = new StringTokenizer(reader.readLine());
                String s = tokenizer.nextToken();
                int[] array = makeArray(s, t);
                STNode root = constructSegmentTree(array, 0, array.length - 1);
                for (int i = 0; i < n; ++i) {
                    tokenizer = new StringTokenizer(reader.readLine());
                    int l = Integer.parseInt(tokenizer.nextToken());
                    int r = Integer.parseInt(tokenizer.nextToken());
                    --l;
                    --r;
                    int ans = -1;
                    if (t.length() <= r - l + 1) {
                        ans = maximum(getValue(root, l, r - t.length() + 1));
                    }
                    updateValue(root, l, r, t.length());
                    writer.println(ans);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //
        } finally {
            writer.close();
        }
    }
 
}
