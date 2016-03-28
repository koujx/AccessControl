package access.AndOrPolicy;

/**
 * created by koujx on 2016/2/29.
 */
public class PolicyTree {
    public String value;
    public PolicyTree children;
    public PolicyTree brother;
    public int[] vector;
    public int vLength = vector.length;

    public PolicyTree getBrother() {
        return brother;
    }
    public void setBrother(PolicyTree brother) {
        this.brother = brother;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public PolicyTree getChildren() {
        return children;
    }
    public void setChildren(PolicyTree children) {
        this.children = children;
    }
    public int[] getVector() {
        return vector;
    }
    public void setVector(int[] vector) {
        this.vector = vector;
    }
    public int getvLength() {
        return vLength;
    }
    public void setvLength(int vLength) {
        this.vLength = vLength;
    }
}
