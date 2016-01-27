package access.compute;

import access.db.SQLServer;
import access.db.Visibility;

public class DataVisibility{

//	public Visibility getVisibility(int behaviorLevel,int uid,int ownerid){
//		Visibility visibility = new Visibility();
//		if (uid==10) {
//			visibility = SQLServer.getVisibility(5);
//		}
//		else {
//			if (uid==ownerid) {
//				visibility = SQLServer.getVisibility(3);
//			}
//			else {
//				visibility = SQLServer.getVisibility(behaviorLevel);
//			}
//		}
//
//		return visibility;
//		
//		
//	} 


	public Visibility getVisibility(int behaviorLevel,int uid,int dataid){
		Visibility visibility = new Visibility();
		
		if (uid == dataid) {
			visibility = SQLServer.getVisibility(4);
		}
		else {
			switch (behaviorLevel) {
			case 0:
				visibility = SQLServer.getVisibility(1);		
				break;
			case 1:
				visibility = SQLServer.getVisibility(5);		
				break;
			case 2:
				visibility = SQLServer.getVisibility(2);
				break;
			case 3:
				visibility = SQLServer.getVisibility(3);
				break;
			case 4:
				visibility = SQLServer.getVisibility(4);
				break;
			default:
				break;
			}
		}
		
		return visibility;
	} 
}