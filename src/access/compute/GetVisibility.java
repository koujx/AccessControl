package access.compute;

import access.db.SQLServer;
import access.db.Visibility;

public class GetVisibility{
	
	public Visibility getVisibility(int behaviorLevel,int uid,int ownerid){
		Visibility visibility = new Visibility();
		if (uid==10) {
			visibility = SQLServer.getVisibility(5);
		}
		else {
			if (uid==ownerid) {
				visibility = SQLServer.getVisibility(3);
			}
			else {
				visibility = SQLServer.getVisibility(behaviorLevel);
			}
		}

		return visibility;
	} 
}