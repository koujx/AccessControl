package access.util;

public class PositionModel{
	public static enum posiModel{
		member("无职务",0),soldier("士兵",1),advicer("参谋",2),staff("干事",2),commissar("政委",3),minister("部长",3);

		private int index;
		private String context;
		
		private posiModel(String context,int index){
			this.context = context;
			this.index = index;
		}
		
	    public static int getIndex(String context){
	    	for(posiModel name:posiModel.values()){
	    		if (name.context.equals(context)) {
	    			return name.index;
	   			}
	      	 }
	      	 return 0;  
	    }
	    
	    public static String getContext(int index) {
			for(posiModel name:posiModel.values()){
				if (name.index == index) {
					return name.context;
				}
			}
			return null;
		}
	}
}