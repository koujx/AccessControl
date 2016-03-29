package access.policy;

/**
 * Created by 902 on 2016/3/29.
 */
public class test {
    public static void main(String[] args) {
        AccessTest accessTest = new AccessTest();
        ProcessPolicy processPolicy = new ProcessPolicy();

        String[] policies = new String[6];
        policies[0] = "a|1|1";
        policies[1] = "a,b|1|1";
        policies[2] = "a,b|2|1";
        policies[3] = "a|1;b,c,d|2;e,f|2|3";
        policies[4] = "a,b,c,d,e|2|1";
        policies[5] = "a,b,c,d,e|5|1";
//
//        for (int i = 0; i < policies.length; i++) {
//            String policy = policies[i];
//            AccessPolicy accessPolicy = processPolicy.processAccessPolicy(policy);
//            System.out.println(accessPolicy.getAttributes());
//            System.out.println(accessPolicy.getVectors());
//
//            String attrs = "a";
//            System.out.println(accessTest.access(attrs, accessPolicy));
//        }

        String policy = policies[5];
        AccessPolicy accessPolicy = processPolicy.processAccessPolicy(policy);
//        System.out.println(accessPolicy.getAttributes());
//        System.out.println(accessPolicy.getVectors());

        String attrs = "a,d,e,c,e";
        System.out.println(accessTest.access(attrs, accessPolicy));
    }
}
