package access.AndOrPolicy;

/**
 * created by koujx on 2016/2/29.
 */
public class AndOrNode {
    public String attribute;
    public AndOrNode left;
    public AndOrNode right;
    public int[] vector;
    public int vLength = vector.length;

    public void AttrNode(String attribute) {
        this.attribute = attribute;
    }

    public int getvLength() {
        return vLength;
    }

    public void setvLength(int vLength) {
        this.vLength = vLength;
    }

    public int[] getVector() {
        return vector;
    }

    public void setVector(int[] vector) {
        this.vector = vector;
    }

    public AndOrNode getRight() {
        return right;
    }

    public void setRight(AndOrNode right) {
        this.right = right;
    }

    public AndOrNode getLeft() {
        return left;
    }

    public void setLeft(AndOrNode left) {
        this.left = left;
    }
}
