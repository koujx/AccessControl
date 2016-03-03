package access.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLServer{

	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载数据库驱动！");
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getConn() throws SQLException {
		  Connection conn = null;
		  conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8", "root", "root");
		  //conn = DriverManager.getConnection("jdbc:mysql://10.103.241" +
		  //	".73:3306/test?useUnicode=true&characterEncoding=UTF-8", "root", "wbljy");
		  //conn = DriverManager.getConnection("jdbc:mysql://10.103.241.77:3306/test?useUnicode=true&characterEncoding=UTF-8", "root", "wangpeng");
		  return conn;
	}
	
	public static void newUserRegist(User user){
		insertUser(user);
		Behavior behavior = new Behavior();
		behavior.setUid(user.getUid());
		insertBehavior(behavior);
	}
	
	/**
	 * 添加新用户
	 */
	private static void insertUser(User user) {
		Connection conn = null;
        try {
        	conn = getConn();
            PreparedStatement stat = null;
            String sql = "insert into access_list (uid,pwd,name,partment,position,keypath,vip,ip,latitude,longitude,mobile,behavior_level,failure_login_attempts,success_login_count,last_access_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stat = conn.prepareStatement(sql);
            stat.setInt(1, user.getUid());
            stat.setString(2, user.getPwd());
            stat.setString(3, user.getName());
            stat.setString(4, user.getPartment());
            stat.setString(5, user.getPosition());
            stat.setString(6,user.getKeyPath());
            stat.setBoolean(7, user.isVip());
            stat.setString(8, user.getIp());
            stat.setString(9, user.getLatitude());
            stat.setString(10, user.getLongitude());
            stat.setBoolean(11, user.isMobile());
            stat.setInt(12, user.getBehaviorLevel());
            stat.setInt(13, user.getFailureLoginAttempts());
            stat.setInt(14, user.getSuccessLoginCount()); 
            stat.setString(15, user.getLastTime());
            
            stat.executeUpdate();
            //System.out.println("成功插入用户数据");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 添加新用户的行为记录
	 */
	public static void insertBehavior(Behavior behavior){
		Connection conn = null;		
		try {
			conn = getConn();
			String sql = "insert into behavior(uid,trust,reward_factor,punish_factor) values(?,?,?,?)";
			PreparedStatement stat;
			stat = conn.prepareStatement(sql);
			stat.setInt(1, behavior.getUid());
			stat.setDouble(2, behavior.getTrust());
			stat.setInt(3, behavior.getRewardFa());
			stat.setInt(4, behavior.getPunishFa());
			stat.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 查找用户
	 * uid 用户uid
	 * 返回值为查找到的用户
	 * @throws SQLException 
	 */
	public static User queryUser(int uid) throws SQLException{
		Connection conn = null;
		User u = new User();

    	conn = getConn();
        Statement stmt = conn.createStatement();
        String sql = "select * from access_list where uid=" + uid;
        ResultSet rs = stmt.executeQuery(sql);
        rs.last();
        if (rs.getRow()==0) {
			System.out.println("该用户未注册！");
			return null;
		}
        else{
        	rs.beforeFirst();
            while(rs.next()){
            	u.setUid(rs.getInt("uid"));
            	u.setPwd(rs.getString("pwd"));
            	u.setName(rs.getString("name"));
            	u.setPartment(rs.getString("partment"));
            	u.setPosition(rs.getString("position"));
            	u.setKeyPath(rs.getString("keypath"));
            	u.setVip(rs.getBoolean("vip"));
            	u.setIp(rs.getString("ip"));
            	u.setLatitude(rs.getString("latitude"));
            	u.setLongitude(rs.getString("longitude"));
            	u.setMobile(rs.getBoolean("mobile"));
            	u.setBehaviorLevel(rs.getInt("behavior_level"));
            	u.setFailureLoginAttempts(rs.getInt("failure_login_attempts"));
            	u.setSuccessLoginCount(rs.getInt("success_login_count"));
            	u.setLastTime(rs.getString("last_access_time"));
            }
            //System.out.println("成功查找到该用户！");
            conn.close();
            return u;
        }           
    }
	
	/**
	 * 更新用户行为等级
	 */
	public static void updateUserBehaviorLevel(int uid, int behaviorLevel){
		Connection conn = null;
		try {
			conn = getConn();
			PreparedStatement stat = null;
			String sql = "update access_list set behavior_level=" + behaviorLevel +" where uid="+ uid;
			stat = conn.prepareStatement(sql);
			stat.executeUpdate();
			conn.close();
			//System.out.println("更新用户行为等级");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查找用户行为记录
	 * @throws SQLException
	 */
	public static Behavior queryBehavior(int uid) throws SQLException{
		Connection conn = null;
		Behavior behavior = new Behavior();
		behavior.setUid(uid);
		
    	conn = getConn();
        Statement stmt = conn.createStatement();
        String sql = "select * from behavior where uid=" + uid;
        ResultSet rs = stmt.executeQuery(sql);
        rs.last();
        if (rs.getRow()==0) {
			System.out.println("该用户为新用户！");
			insertBehavior(behavior);
			return behavior;	
		}
        else{
        	rs.beforeFirst();
            while(rs.next()){
            	behavior.setRewardFa(rs.getInt("reward_factor"));
            	behavior.setPunishFa(rs.getInt("punish_factor"));
            	behavior.setTrust();
            }
            //System.out.println("读取该用户的行为记录！");
            conn.close();
            return behavior;
        }           
	}
	
	/**
	 * 更新用户行为记录 
	 */
	public static void updateBehavior(Behavior behavior){
		Connection conn = null;
	    try {
	    	conn = getConn();
            PreparedStatement stat = null;
            String sql = "update behavior set trust=" + behavior.getTrust()
            		+ ",reward_factor=" + behavior.getRewardFa() 
            		+ ",punish_factor=" + behavior.getPunishFa()
            		+ " where uid=" + behavior.getUid();
            stat = conn.prepareStatement(sql);
			stat.executeUpdate();
		    //System.out.println("更新用户行为记录");
		    conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void reward(int uid){
		Connection conn = null;
		try {
			conn = getConn();
			PreparedStatement stat = null;
			String sql = "update behavior set reward_factor=reward_factor+1 where uid="+uid;
            stat = conn.prepareStatement(sql);
			stat.executeUpdate();
		    conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void punish(int uid){
		Connection conn = null;
		try {
			conn = getConn();
			PreparedStatement stat = null;
			String sql = "update behavior set punish_factor=punish_factor+1 where uid="+uid;
            stat = conn.prepareStatement(sql);
			stat.executeUpdate();
		    conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 提取ip策略
	 */
	public static List<IpReg> getIpReg(String partment){
		Connection conn = null;
		List<IpReg> lIpRegs = new ArrayList<IpReg>();
		try {
			conn = getConn();
			Statement stat = conn.createStatement();
			String sql = "select * from ip_reg where partment='" + partment +"'";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()){
				IpReg ipReg = new IpReg();
				ipReg.setId(rs.getInt("id"));
				ipReg.setName(rs.getString("name"));
				ipReg.setPartment(rs.getString("partment"));
				ipReg.setRegexp(rs.getString("regexp"));
				ipReg.setDescription(rs.getString("description"));
				lIpRegs.add(ipReg);
			}
	        //System.out.println("加载ip策略");
	        conn.close();
            return lIpRegs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * 提取经纬度策略
	 */
	public static List<GeoCoord> getGeoCoords(){
		Connection conn = null;
		List<GeoCoord> geoCoords = new ArrayList<GeoCoord>();
		try {
			conn = getConn();
			Statement stat = conn.createStatement();
			String sql = "select * from geo_coord ";
		    ResultSet rs = stat.executeQuery(sql);
		    while(rs.next()){
		    	GeoCoord geoCoord = new GeoCoord();
		    	geoCoord.setId(rs.getInt("id"));
		    	geoCoord.setPoints(rs.getString("points"));
		    	geoCoord.setDescription(rs.getString("description"));
		    	geoCoord.setWeight(rs.getDouble("weight"));
				geoCoords.add(geoCoord);
		        //System.out.println(geoCoord.getDescription());
		    }
		    //System.out.println("加载经纬度策略");
		    conn.close();
		    return geoCoords;
		} catch (SQLException e) {
		    e.printStackTrace();
		    return geoCoords;
		}
	}

	/**
	 * 提取可视性属性
	 */
	public static Visibility getVisibility(int id){
		Connection conn = null;
		Visibility visi = new Visibility();
		try {
			conn = getConn();
	        Statement stat = conn.createStatement();
	        String sql = "select * from visibility_view where id=" + id;
	        ResultSet rs = stat.executeQuery(sql);
	        while(rs.next()){
	        	visi.setId(rs.getInt("id"));
	        	visi.setName(rs.getString("name"));	
	        	visi.setDescription(rs.getString("description"));
	        }
	        //System.out.println("提取可视性属性策略:" + visi.getDescription());
	        conn.close();
	        return visi;
		}catch (SQLException e) {
            e.printStackTrace();
            return visi;
        }	
	}
	
	/**
	 * 查询数据类型
	 */
	public static String getDataType(String dataid){
		Connection conn = null;
		String type = null;
		try {
			conn = getConn();
			Statement stat = conn.createStatement();
			String sql = "select * from detail where dataID='"+dataid+"'";
			ResultSet rs =  stat.executeQuery(sql);
			while(rs.next()){
				type = rs.getString("type");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return type;
	}
	
	/**
	 * 查询数据可视性属性
	 */
	public static Visibility getDataVisibility(String dataid){
		Connection conn = null;
		int visId = 1;
		try {
			conn = getConn();
			Statement stat = conn.createStatement();
			String sql = "select * from detail where dataID='"+dataid+"'";
			ResultSet rs =  stat.executeQuery(sql);
			while(rs.next()){
				visId = rs.getInt("visibility");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SQLServer.getVisibility(visId);
	}
	/**
	 * 提取访问控制策略
	 */
	 public static AccessPolicy getAccessPolicy(String dataid) {
		Connection conn = null;
		AccessPolicy accessPolicy = new AccessPolicy();
		try {
			conn = getConn();
	        Statement stat = conn.createStatement();
	        String sql = "select * from access_policy where dataid='" + dataid+"'";
	        ResultSet rs = stat.executeQuery(sql);
	        int count =0;
	        while(rs.next()){
	        	count++;
	        	accessPolicy.setAttributes(rs.getString("attributes"));
	        	accessPolicy.setVectors(rs.getString("vectors"));
	        	accessPolicy.setNumOfAttributes(rs.getInt("row"));
	        	accessPolicy.setLengthOfVector(rs.getInt("column"));
	        }
            conn.close();
            if (count==0) {
				return null;
			}else   return accessPolicy;	
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }		
	}
	 
	/**
	 * 提取数据提供策略
	 */
	 public static Policy getPolicy(int pid) {
		Connection conn = null;
		Policy policy = new Policy();
		try {
			conn = getConn();
	        Statement stat = conn.createStatement();
	        String sql = "select * from policy_list where pid=" + pid;
	        ResultSet rs = stat.executeQuery(sql);
	        while(rs.next()){
	        	policy.setPid(rs.getInt("pid"));
	        	policy.setPolicy(rs.getString("policy"));
	        	policy.setDescription(rs.getString("description"));
	        }
	        //System.out.println("提取数据提供策略:" + policy.getDescription_list());
            conn.close();
	        return policy;
		}catch (SQLException e) {
            e.printStackTrace();
            return policy;
        }		
	}
}