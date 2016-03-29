package access.policy;

import java.util.ArrayList;

/**
 * Created by 902 on 2016/3/29.
 */
public class AttributePoint {
    public String attribute;
    public ArrayList vector;
    public int length;

    public AttributePoint(String attribute) {
        this.attribute = attribute;
    }

    public AttributePoint(String attribute, ArrayList vector) {
        this.attribute = attribute;
        this.vector = vector;
        this.length = vector.size();
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public ArrayList getVector() {
        return vector;
    }

    public void setVector(ArrayList vector) {
        this.vector = vector;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
