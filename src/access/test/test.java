package access.test;

import access.db.SQLServer;

import java.sql.SQLException;

public class test {
    public static void main(String args[]) {
        int i  = 0;
        do {
            i++;
            System.out.println(i);
        }while (i<10);

        try {
            System.out.println(SQLServer.queryUser(1).getPosition());
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        List<IpReg> ipReg;
//        ipReg = SQLServer.getIpReg("总装备部");
//        String ip = "258.255.255.255";
//        for (IpReg anIpReg : ipReg) {
//            System.out.println(anIpReg.getDescription());
//            if (inIpArea(ip, anIpReg.getRegexp())) {
//                System.out.println("in");
//            } else System.out.println("out");

    }
}