package access.policy;

import Jama.Matrix;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

public class AccessTest {
    public static boolean access(String accessAttributes, AccessPolicy accessPolicy) {
        if (accessPolicy == null) return true;
        else if (accessAttributes == null || accessAttributes.length() <= 0) {
            return false;
        } else {

            //属性矩阵
            Map<String, String> policyMap = new HashMap<String, String>();
            StringTokenizer tokenOfVector = new StringTokenizer(accessPolicy.getVectors(), ";");
            String[] vectors = new String[tokenOfVector.countTokens()];
            int num = 0;
            while (tokenOfVector.hasMoreElements()) {
                String vector = tokenOfVector.nextToken();
                vectors[num] = vector;
                num++;
            }
            StringTokenizer tokenOfAPolicy = new StringTokenizer(accessPolicy.getAttributes(), ",");
            String[] attributes = new String[tokenOfAPolicy.countTokens()];
            int num1 = 0;
            while (tokenOfAPolicy.hasMoreElements()) {
                String attribute = tokenOfAPolicy.nextToken();
                attributes[num1] = attribute;
                num1++;
            }
            for (int i = 0; i < attributes.length; i++) {
                policyMap.put(attributes[i], vectors[i]);
            }

            //子属性列表
            StringTokenizer tokenOfAParams = new StringTokenizer(accessAttributes, ",");
            String[] aparams = new String[tokenOfAParams.countTokens()];
            int num2 = 0;
            while (tokenOfAParams.hasMoreElements()) {
                String param = tokenOfAParams.nextToken();
                aparams[num2] = param;
                num2++;
            }

            //子属性矩阵
            Map<String, Boolean> map = new HashMap<String, Boolean>();
            for (String str : attributes) {
                map.put(str, Boolean.FALSE);
            }
            for (String str : aparams) {
                if (map.containsKey(str)) {
                    map.put(str, Boolean.TRUE);
                }
            }

            LinkedList<String> subAttrList = new LinkedList<String>();
            for (Map.Entry<String, Boolean> e : map.entrySet()) {
                if (e.getValue().equals(Boolean.TRUE)) {
                    subAttrList.add(e.getKey());
                }
            }
            int numSubAttrs = subAttrList.size();
            //System.out.println("子属性集内有" + numSubAttrs + "个属性:" + subAttrList.toString());

            return numSubAttrs == 0 ||
                    attributeMatrix(subAttrList, policyMap, numSubAttrs, accessPolicy.getLengthOfVector());
        }
    }

    public static boolean attributeMatrix(LinkedList attrs, Map<String, String> map, int row, int column) {
        double[][] vectors = new double[row + 1][column];
        for (int i = 0; i < row; i++) {
            String attr = (String) attrs.get(i);
            if (map.containsKey(attr)) {
                String[] vector = map.get(attr).split(",");
                for (int j = 0; j < vector.length; j++) {
                    vectors[i][j] = Double.valueOf(vector[j]);
                }
            }
        }
        vectors[row][0] = 1;
        Matrix ma = new Matrix(vectors).transpose();
        Matrix m = ma.getMatrix(0, column - 1, 0, row - 1);

//        System.out.println("属性子矩阵转置矩阵：");
//        m.print(2, 0);
//        System.out.println("子矩阵转置矩阵的秩:" + m.rank());
//        System.out.println("线性方程组的增广矩阵：");
//        ma.print(2, 0);
//        System.out.println("增广的秩:" + ma.rank());

        if (ma.rank() > m.rank()) return false;
        else return true;
    }
}
