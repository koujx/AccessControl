package access.compute;

import access.db.Behavior;
import access.db.User;
import access.model.Environment;
import access.model.Subject;

import java.util.regex.Pattern;

public class BehaviorLevel{
	private final static int DEFAULT_WRONG_PASSWORD_TIMES = 5;
	private final static int DEFAULT_LOGIN_TIMES = 10;
	
	public int behaviorLevel(Subject subject,Environment environment){
		String ip = environment.getIp();
		int attempts = subject.getFailureLoginAttempts();
		System.out.println(ip);
		if (!inIpArea(ip, SetAccessParams.IPRANGE_REGEX)||overLoginLimit(attempts)) {
			System.out.println("ip地址不合法或登陆失败次数超限，用户权限受限，自动降级为游客！");
			return 1;
		}else 
			return computeBehaviorLevel(subject);
	}
	
	public int getUserBehaviorLevel(User user,Behavior behavior){
		Subject sub = new Subject();
		sub.setTrust(behavior.getTrust());
		sub.setSuccessLoginCount(user.getSuccessLoginCount());
		sub.setVip(user.isVip());
		return computeBehaviorLevel(sub);
	}
	
	
	private int computeBehaviorLevel(Subject subject){				
		int bl = 0;
		double trustValue = subject.getTrust();
		double loginValue = (subject.getSuccessLoginCount()/DEFAULT_LOGIN_TIMES);		
		boolean isVip = subject.isVip();
		
		if (trustValue<0) {
			bl = 0;
		}
		else if (loginValue>1&&trustValue>=0) {
			if (isVip && trustValue>=10) {
				bl = 3;
			}else {
				return 2;
			}
		}else {
			bl = 1;
		}
		
		return bl;
	}	

	

	private static boolean inIpArea(String ip, String regex) {
		try {
			return Pattern.matches(regex, ip);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("无法获取用户当前ip地址");
			return false;
		}
	}
	
	private static boolean overLoginLimit(int count) {
		return (count<=DEFAULT_WRONG_PASSWORD_TIMES)?false:true;
	}
}