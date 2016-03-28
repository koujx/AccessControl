package access.AndOrPolicy;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * created by koujx on 2016/2/29.
 */
public class CreatTree {

    public static PolicyTree creatPolicyTree(ArrayList attrsList){
        PolicyTree root = new PolicyTree();
        for (int i = 0; i < attrsList.size(); i++) {

        }


        return root;
    }

    public static ArrayList attrsList(String policy) {
        ArrayList attrsList = new ArrayList();
        int index = policy.lastIndexOf("|");
        int gate = Integer.parseInt(policy.substring(index + 1));
        attrsList.add(gate);
        String attrs = policy.substring(0, index);
        StringTokenizer token = new StringTokenizer(attrs, ";");
        if (token.countTokens() > 1) {
            while (token.hasMoreElements()) {
                attrsList.add(subAttrs(token.nextToken()));
            }
        } else {
            attrsList.add(subAttrs(attrs));
        }
        return attrsList;
    }

    public static ArrayList subAttrs(String subPolicy) {
        ArrayList subAttrs = new ArrayList();
        int index = subPolicy.lastIndexOf("|");
        if (index == -1) {
            StringTokenizer token = new StringTokenizer(subPolicy, ",");
            while (token.hasMoreElements()) {
                subAttrs.add(token.nextToken());
            }
        } else {
            int gate = Integer.parseInt(subPolicy.substring((index + 1)));
            subAttrs.add(gate);
            StringTokenizer token = new StringTokenizer(subPolicy.substring(0, index), ",");
            while (token.hasMoreElements()) {
                subAttrs.add(token.nextToken());
            }
        }
        return subAttrs;
    }

    public static void main(String[] args) {
        String policy = new String("a;b1,b2,b3|2;c1,c2|2|1");
        //String policy = new String("a,b,c|3");
        ArrayList arrtsL = attrsList(policy);
        for (int j = 0; j < arrtsL.size(); j++) {
            System.out.println(arrtsL.get(j));
        }
    }

}
