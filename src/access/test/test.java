package access.test;

import access.db.IpReg;
import access.db.SQLServer;

import java.util.List;

import static access.compute.GetPolicy.inIpArea;

public class test {
    public static void main(String args[]) {

        List<IpReg> ipReg;
        ipReg = SQLServer.getIpReg("总装备部");
        String ip = "258.255.255.255";
        for (IpReg anIpReg : ipReg) {
            System.out.println(anIpReg.getDescription());
            if (inIpArea(ip, anIpReg.getRegexp())) {
                System.out.println("in");
            } else System.out.println("out");

        }
    }
}