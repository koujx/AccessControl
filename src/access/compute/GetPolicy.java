/**
 * @author 902 
 * Compute user's security
 * */
package access.compute;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import access.db.IpReg;
import access.db.SQLServer;
import access.db.Policy;
import access.model.AccessParams;
import access.model.Environment;
import access.model.Object;
import access.util.Point;
import access.util.ReadXml;

public class GetPolicy{
	
	private ReadXml rx = new ReadXml(); 

	
	public Policy getPolicy(AccessParams params){
		String envValue = computeEnvValue(params.getEnv(),params.getObj());
		int pid = rx.queryPolicy(params.getAccessType(),params.getObj().getDataType(), envValue);
		Policy policy = SQLServer.getPolicy(pid);
		System.out.println("pid="+pid);
		
		return policy;
	}
	
	public String computeEnvValue(Environment env,Object obj) {
		int[] envValue = {0,0};
		double ipRange = 0;
		double netMode = 0;
//		double location = 0;

		if (!env.isMobile()) {
			netMode = 1;
			System.out.println("该用户通过有线网络访问。");
			
			//判断是否在合法ip范围内
			try {
				List<IpReg> ipRegs = SQLServer.getIpReg(obj.getPartment()); //提取ip策略
				IpReg ipreg;
				for (int i = 0; i < ipRegs.size(); i++) {
					ipreg = ipRegs.get(i);
					if (inIpArea(env.getIp(), ipreg.getRegexp())) {
						ipRange = 1;
						System.out.println("该用户从"+ipreg.getName()+"访问。");
						break;
					}
				}
				if (ipRange == 0) {
					System.out.println("该用户从外网访问。");
				}
			}catch (Exception eip) {
				eip.printStackTrace();
			}
		}
		else {
			netMode = 0;
			System.out.println("该用户通过无线网络访问。");	
		}
//			//判断是否在合法的经纬度范围内
//			try {
//				List<GeoCoord> geoCoords = sql.getGeoCoords(); //提取经纬度策略
//				GeoCoord geoc;
//				for (int i = 0; i < geoCoords.size(); i++) {
//					geoc = geoCoords.get(i);
//					List<Point> frame = new ArrayList<Point>(); //经纬度点序列
//					String[] points;
//					String[] xy;
//					points = geoc.getPoints().split(" "); //按照数据库存储格式划分，提取x,y
//					for (int j = 0; j < points.length; j++) {
//						xy = points[j].split(","); //取出Point的x和y
//						try {
//							Point point = new Point(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
//							frame.add(point); //加入点(x,y)到点序列中
//						} catch (NumberFormatException ne) {
//							ne.printStackTrace();
//						}	
//					}				
//					if (inGeoArea(Double.parseDouble(env.getLongitude()), Double.parseDouble(env.getLatitude()), frame)) { //提取经纬度权值
//						location = geoc.getWeight();
//						System.out.println("该用户当前地理位置在" + geoc.getDescription() + "内！");
//						break;
//					}
//				}
//				if (location == 0) {
//					System.out.println("该用户当前地理位置不在安全区域内！");
//				}
//			} catch (Exception egeo) {
//				egeo.printStackTrace();
//			}	
		
		envValue[0] = (int) netMode;
		envValue[1] = (int) ipRange;	
//		envValue[2] = (int) location;
			
		return Arrays.toString(envValue).replaceAll(" ","").replaceAll(",", "");	
	}	

	
	/**
	 * 判断ip是否符合某个正则表达式
	 * @param ip String ip
	 * @param regex String 正则表达式
	 * @return boolean
	 * */
	public static boolean inIpArea(String ip, String regex) {
		try {
			return Pattern.matches(regex, ip);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("无法获取用户当前ip地址");
			return false;
		}
	}
	
	/**
	 * 采用水平/垂直交叉点数判别法（适用于任意多边形）判断坐标点是否在指定的范围内
	 * @param x double x坐标
	 * @param y double y坐标
	 * @param frame List<Point> 边框点序列
	 * @return boolean
	 * */
	public static boolean inGeoArea(double x, double y, List<Point> frame) {
		int nCross = 0;
    	Point p1, p2;
    	for (int i = 0; i < frame.size()-1; i++) {
    		p1 = frame.get(i);
    		p2 = frame.get(i+1);
    		double x0 = p1.getX(), y0 = p1.getY();  //current start point
    		double x1 = p2.getX(), y1 = p2.getY();  //current target point
    		//System.out.println("x0:"+x0+" y0:"+y0+" x1:"+x1+" y1:"+y1);
    		// 求解 y=p.y 与 p1p2 的交点
    		if ( y0 == y1 ) // p1p2 与 y=p0.y平行 
    			continue;
    		if ( y < Math.min(y0, y1) ) // 交点在p1p2延长线上 
    			continue; 
    		if ( y >= Math.max(y0, y1) ) // 交点在p1p2延长线上 
    			continue;
    		// 求交点的 X 坐标 -------------------------------------------------------------- 
    		double xx = (double)(y - y0) * (double)(x1 - x0) / (double)(y1 - y0) + x0;
    		if ( xx > x ) 
    			nCross++; // 只统计单边交点 
    	}
    	// 单边交点为偶数，点在多边形之外 --- 
    	return (nCross % 2 == 1); 
	}
	
	/**
	 * 判断访问时间是否合法
	 * */
	@SuppressWarnings("deprecation")
	public static Boolean TimeValid(Date date){
		Date startTime = new Date();
		Date stopTime = new Date();
		startTime.setHours(8);
		stopTime.setHours(22);
		if(date.after(startTime)&&date.before(stopTime))
			return true;
		else return false;
	}
}
