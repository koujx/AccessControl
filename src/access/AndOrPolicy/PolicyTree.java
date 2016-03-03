package access.AndOrPolicy;

import java.util.ArrayList;
import java.util.Stack;

/**
 * created by koujx on 2016/2/29.
 */
public class PolicyTree {
    public ArrayList creat(String policy) {
        ArrayList policyArray = new ArrayList();
        StringBuffer sb = new StringBuffer(policy);
        Stack policyStack = new Stack();
        sb.reverse();
        for (int i = 0; i < policy.length(); i++) {
            Character ch = sb.charAt(i);
            if (ch.equals("(")) {
                String s;
                String subPolicy = null;
                do {
                    s = (String) policyStack.pop();
                    subPolicy += s;
                }while (s.equals(")"));
                policyArray.add(creat(subPolicy));
            }
            else if (ch.equals(",")){
                String s;
                String attr = null;
                do {
                    s = (String) policyStack.pop();
                    attr +=s;
                }while (s.equals(","));
                policyArray.add(attr);
            }
            policyStack.push(ch);
        }
        return policyArray;
    }
}
