package access.compute;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.fpe.GeographyFPE;
import com.fpe.IpFPE;
import com.fpe.MD5;
import com.fpe.NumberFPE;

import access.db.Behavior;
import access.db.SQLServer;
import access.db.User;
import access.model.AccessParams;
import access.model.Environment;
import access.model.Object;
import access.model.Subject;
import access.util.PartmentModel.partModel;
//import access.util.NetType;
import access.util.PositionModel.posiModel;

public class SetAccessParams{
	private Subject sub = new Subject();
	private Object obj = new Object();
	private Environment env = new Environment();
	
	private static final String ACCESS_TYPE1 = "Internal_High_To_Low";
	private static final String ACCESS_TYPE2 = "Internal_Low_To_High";
	private static final String ACCESS_TYPE3 = "External_Common";
	private static final String ACCESS_TYPE4 = "External_Special";
	private static final String ACCESS_TYPE5 = "Platform";
	public final static String IPRANGE_REGEX = "(0|2[0-4]{0,1}[0-9]{0,1}|25[0-5]|[1][0-9]{0,2}|[3-9]{1}[0-9]{0,1}).(0|2[0-4]{0,1}[0-9]{0,1}|25[0-5]|[1][0-9]{0,2}|[3-9]{1}[0-9]{0,1}).(0|2[0-4]{0,1}[0-9]{0,1}|25[0-5]|[1][0-9]{0,2}|[3-9]{1}[0-9]{0,1}).(0|2[0-4]{0,1}[0-9]{0,1}|25[0-5]|[1][0-9]{0,2}|[3-9]{1}[0-9]{0,1})";
	public final static String WLAN_IPRANGE_REGEX = "10.8.(0|2[0-4]{0,1}[0-9]{0,1}|25[0-5]|[1][0-9]{0,2}|[3-9]{1}[0-9]{0,1}).(0|2[0-4]{0,1}[0-9]{0,1}|25[0-5]|[1][0-9]{0,2}|[3-9]{1}[0-9]{0,1})";
	public final static String TEST_IPRANGE_REGEX = "10.103.(24[0-5]).(0|2[0-4]{0,1}[0-9]{0,1}|25[0-5]|[1][0-9]{0,2}|[3-9]{1}[0-9]{0,1})";
	
	public AccessParams setAccessParams(int uid, int ownerid,HttpServletRequest request,String dataType) throws Exception{
		AccessParams params = new AccessParams();
		User user = SQLServer.queryUser(uid);
		User owner = SQLServer.queryUser(ownerid);
		Behavior behavior = SQLServer.queryBehavior(user.getUid());	
	
		String accessType = accessType(user, owner);
		
		if (request.getSession().getAttribute("anonymous")!=null) {
			String key = MD5.getMD5(user.getName(), user.getPwd());
			NumberFPE numberFPE = new NumberFPE(key);
			NumberFPE partFPE = new NumberFPE(0, 6, key);
			NumberFPE posiFPE = new NumberFPE(0, 4, key);
			IpFPE ipFPE = new IpFPE(key);
			GeographyFPE geographyFPE = new GeographyFPE(key);
			
			int indexOfPartment = partModel.getIndex(user.getPartment());
			int indexOfPosition = posiModel.getIndex(user.getPosition());
			
			//主体属性
			sub.setUid(numberFPE.intEncrypt(uid));
			sub.setPartment(partModel.getContext(partFPE.intEncryptOnRange(indexOfPartment)));
			sub.setPosition(posiModel.getContext(posiFPE.intEncryptOnRange(indexOfPosition)));
			sub.setVip(user.isVip());
			sub.setTrust(behavior.getTrust());	
			sub.setSuccessLoginCount(user.getSuccessLoginCount());
			sub.setFailureLoginAttempts(user.getFailureLoginAttempts());
			
			//环境属性	
			if (Pattern.matches(WLAN_IPRANGE_REGEX, request.getRemoteAddr())) {
				env.setMobile(true);
				env.setIp(ipFPE.ipEncrypt(request.getRemoteAddr(), 0, 0, 255));
			}else {
				env.setMobile(false);
				if (Pattern.matches(TEST_IPRANGE_REGEX, request.getRemoteAddr())) {
					env.setIp(ipFPE.ipEncrypt(request.getRemoteAddr(), 1, 0, 255));
				}else {
					env.setIp(ipFPE.ipEncrypt(request.getRemoteAddr(), 0, 0, 255));;
				}
			}
			env.setLatitude(geographyFPE.latitudeEncrypt(user.getLatitude()));
			env.setLongitude(geographyFPE.longitudeEncrypt(user.getLongitude()));
			
			
		}else {
			//主体属性
			sub.setUid(user.getUid());
			sub.setPartment(user.getPartment());
			sub.setPosition(user.getPosition());
			sub.setVip(user.isVip());
			sub.setTrust(behavior.getTrust());
			sub.setSuccessLoginCount(user.getSuccessLoginCount());
			sub.setFailureLoginAttempts(user.getFailureLoginAttempts());
			
			
			//环境属性	
			if (Pattern.matches(WLAN_IPRANGE_REGEX, request.getRemoteAddr())) {
				env.setMobile(true);
			}else env.setMobile(false);
			env.setIp(user.getIp());
			env.setLatitude(user.getLatitude());
			env.setLongitude(user.getLongitude());
		
		}
		//客体属性
		obj.setOwnerid(owner.getUid());
		obj.setPartment(owner.getPartment());
		obj.setPosition(owner.getPosition());
		obj.setDataType(dataType);
		
		params.setAccessType(accessType);
		params.setSub(sub);
		params.setObj(obj);
		params.setEnv(env);
		
		
		SQLServer.updateBehavior(behavior);
		return params;
	}
	
	public String  accessType(User user,User owner) {
		String accessType;
		
		if (user.getPartment().equals("大数据平台A")&&owner.getPartment().equals("大数据平台A")) {
			accessType = ACCESS_TYPE5;
		}
		else if (user.getPartment().equals("大数据平台A")&&!owner.getPartment().equals("大数据平台A")) {
			accessType = ACCESS_TYPE3;
		} 
		
		else{
			if (!owner.getPartment().equals("大数据平台A")) {
				if(user.getPartment().equals(owner.getPartment())){
					if(posiModel.getIndex(user.getPosition()) < posiModel.getIndex(owner.getPosition()))
					{
						accessType = ACCESS_TYPE2;
					}
					else accessType = ACCESS_TYPE1;
				}				
				else {
					if (user.getPartment().equals("特殊部门")) {
						accessType = ACCESS_TYPE4;
					}
					else accessType = ACCESS_TYPE3;
				}
			}
			else accessType = ACCESS_TYPE5;
		}

		return accessType;
	}
}
