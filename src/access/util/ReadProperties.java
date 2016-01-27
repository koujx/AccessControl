package access.util;

import java.util.Enumeration;
import java.util.Properties;

public class ReadProperties{
	private  String filename;
	private  Properties prop = new Properties();

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int readPolicyProperties(String file,String env) {
		int pid = 0;
		this.setFilename(file);
		try {
			prop.load(ReadProperties.class.getClassLoader().getResourceAsStream(filename));
			Enumeration<?> enumeration = prop.propertyNames();
			while(enumeration.hasMoreElements()){
				if (enumeration.nextElement().equals(env)) {
					String key = env;
					String value = prop.getProperty(key);
					System.out.println(key + ":" + value);
					pid = Integer.parseInt(value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (pid == 0) {
			System.out.println(env + ":" + pid);
		}
		return pid;
	}	
}
