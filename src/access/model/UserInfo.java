package access.model;

import access.compute.BehaviorLevel;
import access.compute.SetAccessParams;
import access.db.Behavior;
import access.db.SQLServer;
import access.db.User;
import access.util.PartmentModel.partModel;
import access.util.PositionModel.posiModel;
import com.fpe.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class UserInfo{
	private User user = new User();
	private Behavior behavior = new Behavior();
	private BehaviorLevel getLevel = new BehaviorLevel();
	
	
	public List<String> getUserInfo(int uid,HttpServletRequest request){
		List<String> userInfoList = new ArrayList<String>();
		try {
			user = SQLServer.queryUser(uid);
			behavior = SQLServer.queryBehavior(uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String name = user.getName();
		String partment = user.getPartment();
		int indexOfPartment = partModel.getIndex(partment);
		String position = user.getPosition();
		int indexOfPosition = posiModel.getIndex(position);
		String vip = (user.isVip())?"是":"否";
		String latitude = user.getLatitude();
		String longitude = user.getLongitude();
		
		String ip = request.getRemoteAddr();
		if (Pattern.matches(SetAccessParams.WLAN_IPRANGE_REGEX, ip)) {
			user.setMobile(true);
		}
		String mobile = (user.isMobile())?"无线访问":"有线访问";
		
		Double trust =behavior.getTrust();
		Integer rewardFa = behavior.getRewardFa();
		Integer punishFa = behavior.getPunishFa();
		
		Integer behaviorLevel = getLevel.getUserBehaviorLevel(user, behavior);
		String behaviorLevelString = null;
		switch (behaviorLevel) {
		case 0:
			behaviorLevelString = "恶意用户";
			break;
		case 1:
			behaviorLevelString = "游客";
			break;
		case 2:
			behaviorLevelString = "普通用户";
			break;
		case 3:
			behaviorLevelString = "超级用户";
			break;
		case 4:
			behaviorLevelString = "管理员";
			break;
		default:
			break;
		}
		
		if (request.getSession().getAttribute("anonymous")!=null) {
			String key = MD5.getMD5(user.getName(), user.getPwd());
			NumberFPE partFPE = new NumberFPE(0, 6, key);
			NumberFPE posiFPE = new NumberFPE(0, 4, key);
			StringFPE stringFPE = new StringFPE(key);
			IpFPE ipFPE = new IpFPE(key);
			GeographyFPE geographyFPE = new GeographyFPE(key);
			
			userInfoList.add(0, stringFPE.userNameEncrypt(name));
			userInfoList.add(1,partModel.getContext(partFPE.intEncryptOnRange(indexOfPartment)));
			userInfoList.add(2,posiModel.getContext(posiFPE.intEncryptOnRange(indexOfPosition)));
			userInfoList.add(3,vip);
			userInfoList.add(4,ipFPE.ipEncrypt(ip, 0, 0, 255));
			userInfoList.add(5,geographyFPE.longitudeEncrypt(longitude));
			userInfoList.add(6,geographyFPE.latitudeEncrypt(latitude));
			userInfoList.add(7,mobile);
			userInfoList.add(8,trust.toString());
			userInfoList.add(9,rewardFa.toString());
			userInfoList.add(10,punishFa.toString());
			userInfoList.add(11,behaviorLevelString);
			userInfoList.add(12, "可视化属性");
		}else {
			userInfoList.add(0, name);
			userInfoList.add(1,partment);
			userInfoList.add(2,position);
			userInfoList.add(3,vip);
			userInfoList.add(4,ip);
			userInfoList.add(5,longitude);
			userInfoList.add(6,latitude);
			userInfoList.add(7,mobile);
			userInfoList.add(8,trust.toString());
			userInfoList.add(9,rewardFa.toString());
			userInfoList.add(10,punishFa.toString());
			userInfoList.add(11,behaviorLevelString);
			userInfoList.add(12, "可视化属性");
		}

		
		return userInfoList;
	}
}