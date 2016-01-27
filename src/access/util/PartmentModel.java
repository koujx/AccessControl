package access.util;

public class PartmentModel{
	public static enum partModel{
		platA("大数据平台A",0),platB("大数据平台B",0),platC("大数据平台C",0),political("总政治部",1),staff("总参谋部",2),equipment("总装备部",3),logistics("总后勤部",4),special("特殊部门",5);

		private int index;
		private String context;
		
		private partModel(String context,int index){
			this.context = context;
			this.index = index;
		}
		
	    public static int getIndex(String context){
	    	for(partModel name:partModel.values()){
	    		if (name.context.equals(context)) {
	    			return name.index;
	   			}
	      	 }
	      	 return 0;  
	    }
	    
	    public static String getContext(int index) {
			for(partModel name:partModel.values()){
				if (name.index == index) {
					return name.context;
				}
			}
			return null;
		}
	}
}