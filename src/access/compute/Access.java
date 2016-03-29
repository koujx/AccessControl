package access.compute;

import Jama.Matrix;
import access.policy.AccessPolicy;
import access.model.AccessParams;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class Access{
	public static boolean access(AccessParams params,AccessPolicy accessPolicy) {
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

		StringTokenizer tokenOfAParams = new StringTokenizer(params.attributesToString(), ",");
		String[] aparams = new String[tokenOfAParams.countTokens()];
		int num2 = 0;
		while (tokenOfAParams.hasMoreElements()) {
			String param = tokenOfAParams.nextToken();
			aparams[num2] = param;
			num2++;
		}

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		LinkedList<String> list = new LinkedList<String>();
		for (String str : aparams) {
			if (!map.containsKey(str)) {
				map.put(str, Boolean.FALSE);
			}
		}
		for (String str : attributes) {
			if (map.containsKey(str)) {
				map.put(str, Boolean.TRUE);
			}
		}

		for (Entry<String, Boolean> e : map.entrySet()) {
			if (e.getValue().equals(Boolean.TRUE)) {
				list.add(e.getKey());
			}
		}

		String[] result = {};
		String[] intersectSring = list.toArray(result);

		int num3 = 0;
		for (String str : intersectSring) {
			System.out.print(str + "  ");
			num3++;
		}
		System.out.println("子属性集内有" + num3 + "个属性");
		return num3 == 0 || attributeMatrix(intersectSring, policyMap, num3, accessPolicy.getLengthOfVector());
	}

	public static boolean attributeMatrix(String[] attr,Map<String, String> map,int row,int column){

		double[][] vectors = new double[row+1][column];
		for (int i = 0; i < attr.length; i++) {
			if (map.containsKey(attr[i])) {
				String[] vector = map.get(attr[i]).split(",");
				for (int j = 0; j < vector.length; j++) {
					vectors[i][j] = Double.valueOf(vector[j]);
				}
			}
		}
		vectors[row][0] = 1;

		Matrix ma = new Matrix(vectors).transpose();
		Matrix m = ma.getMatrix(0,column-1,0,row-1);
		System.out.println("属性子矩阵转置矩阵：");
		m.print(2, 0);
		System.out.println("子矩阵转置矩阵的秩:"+m.rank());
		System.out.println("线性方程组的增广矩阵：");
		ma.print(2, 0);
		System.out.println("增广的秩:"+ma.rank());

		return ma.rank() != m.rank();
	}

}
