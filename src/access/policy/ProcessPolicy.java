package access.policy;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * created by koujx on 2016/2/29.
 *
 */
public class ProcessPolicy {

    public static void main(String[] args){

    }

    public AccessPolicy processAccessPolicy(String policy){
        ArrayList arrtsL = attrsList(policy);
        ArrayList<AttributePoint> attrsMtrix;
        ArrayList preVector = new ArrayList();
        attrsMtrix = attrMtrix(arrtsL, preVector);

        AccessPolicy accessPolicy = new AccessPolicy();
        int attrNums = 0;
        int vecLength = 0;
        for (int i = 0; i < attrsMtrix.size(); i++) {
            if (i == 0) {
                AttributePoint attrPoint = attrsMtrix.get(i);
                String vector = subString(attrPoint.getVector().toString());
                accessPolicy.setAttributes(attrPoint.getAttribute());
                accessPolicy.setVectors(vector);
                attrNums++;
                vecLength = attrPoint.getLength();
            } else {
                AttributePoint attrPoint = attrsMtrix.get(i);
                String vector = subString(attrPoint.getVector().toString());
                accessPolicy.setAttributes(accessPolicy.getAttributes() + "," + attrPoint.getAttribute());
                accessPolicy.setVectors(accessPolicy.getVectors() + ";" + vector);
                attrNums++;
                vecLength = attrPoint.getLength();
            }
        }
        accessPolicy.setNumOfAttributes(attrNums);
        accessPolicy.setLengthOfVector(vecLength);

        return accessPolicy;
    }

    public ArrayList<AttributePoint> attrMtrix(ArrayList attrsList, ArrayList attrVector) {
        Integer n = (Integer) attrsList.get(0);
        ArrayList attrVectors = new ArrayList();
        ArrayList preVector;
        int length = 0;
        for (int i = 1; i < attrsList.size(); i++) {
            preVector = new ArrayList(attrVector);
            if (preVector.size() == 0) {
                for (int j = 0; j < n; j++) {
                    int value = (int) Math.pow(i, j);
                    preVector.add(value);
                }
            }

            for (int j = preVector.size(); j < length; j++) {
                preVector.add(0);
            }

            Object subAttr = copy(attrsList.get(i));

            if (subAttr instanceof ArrayList) {
                ArrayList subAttrCopy = (ArrayList) subAttr;
                if (subAttrCopy.size() > 2) {
                    ArrayList<AttributePoint> attributePoints = attrMtrix(subAttrCopy, preVector);
                    for (AttributePoint attributePoint : attributePoints) {
                        attrVectors.add(attributePoint);
                        length = attributePoint.getLength();
                    }
                } else {
                    AttributePoint attr = new AttributePoint((String) subAttrCopy.get(1), preVector);
                    attrVectors.add(attr);
                    length = attr.getLength();
                }
            } else {
                for (int j = 1; j < n; j++) {
                    int value = (int) Math.pow(i, j);
                    preVector.add(value);
                }
                AttributePoint attr = new AttributePoint((String) subAttr, preVector);
                attrVectors.add(attr);
            }
        }
        return attrVectors;
    }

    public static <T> T copy(T source) {
        T result = source;
        return result;
    }

    public ArrayList attrsList(String policy) {
        ArrayList attrsList = new ArrayList();
        int index = policy.lastIndexOf("|");
        int gate = Integer.parseInt(policy.substring(index + 1));
        attrsList.add(gate);
        String attrs = policy.substring(0, index);
        StringTokenizer token = new StringTokenizer(attrs, ";");
        if (token.countTokens() > 1) {
            while (token.hasMoreElements()) {
                attrsList.add(subAttr(token.nextToken()));
            }
        } else {
            attrsList.add(subAttr(attrs));
        }
        return attrsList;
    }

    public ArrayList subAttr(String subPolicy) {
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

    public static String subString(String str) {
        return str.substring(1, str.length() - 1).replace(" ","");
    }
}
