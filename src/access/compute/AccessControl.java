package access.compute;

import access.policy.AccessPolicy;
import access.db.Policy;
import access.db.SQLServer;
import access.db.Visibility;
import access.model.AccessParams;

import javax.servlet.http.HttpServletRequest;

public class AccessControl{
	private static BehaviorLevel blc = new BehaviorLevel();
	private static DataServicePolicy pc = new DataServicePolicy();
	private static DataVisibility vc = new DataVisibility();
	private static SetAccessParams setParams = new SetAccessParams();

	public static AccessParams access(int uid,int ownerid,HttpServletRequest request,String dataid){
		AccessParams params = new AccessParams();
		AccessPolicy accessPolicy = SQLServer.getAccessPolicy(dataid);
		String dataType = (SQLServer.getDataType(dataid).equals("table"))?"table":"file";
		
		try {
			params = setParams.setAccessParams(uid, ownerid,request,dataType);
			if (accessPolicy!=null&&!Access.access(params, accessPolicy)) {
				params.setAccess(false);
				System.out.println("---------------用户未通过访问权限验证！---------------");
			}else {
				params.setAccess(true);
				System.out.println("---------------用户通过访问权限验证！---------------");
				
				Policy policy = pc.getPolicy(params);
				params.setPolicy(policy.getPolicy());
				params.setPolicyDescription(policy.getDescription());
				params.setBehaviorLevel(blc.behaviorLevel(params.getSub(),params.getEnv()));
				Visibility visibility = vc.getVisibility(params.getBehaviorLevel(), params.getSub().getUid(), params.getObj().getOwnerid());
				params.setVisibility(visibility.getId());
				params.setVisibilityDescription(visibility.getDescription());
			}
		}catch(Exception e){
			params.setAccess(false);
			e.printStackTrace();
		}
		
//		if (Access.access(params, accessPolicy)) {	
//			System.out.println("---------------用户通过访问权限验证---------------");
//			
//			Policy policy = pc.getPolicy(params);
//			params.setPolicy(policy.getPolicy());
//			params.setPolicyDescription(policy.getDescription());
//			params.setBehaviorLevel(blc.behaviorLevel(params.getSub(),params.getEnv()));
//			//Visibility visibility = vc.getVisibility(params.getBehaviorLevel(), params.getSub().getUid(), params.getObj().getOwnerid());
//			Visibility visibility = SQLServer.getVisibility(accessPolicy.getVisiId());
//			params.setVisibility(visibility.getId());
//			params.setVisibilityDescription(visibility.getDescription());
//			
//		}
//		else{
//			params.setBehaviorLevel(1);
//			params.setVisibility(1);
//			params.setVisibilityDescription("全程不可见");
//			if (dataType == "table") {
//				params.setPolicy("0,1,2,3:C");
//				params.setPolicyDescription("数据中所有字段均采用AES加密提供。");
//			}else {
//				params.setPolicy("0:A;1,2,3:C");
//				params.setPolicyDescription("安全等级为低级的文件提供原文件，安全等级为普通、中级和高级的文件隐藏加密。");
//			}
//		}		
		return params;
	}
}