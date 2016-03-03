package access.util;


import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

public class ReadXml{

	@SuppressWarnings("rawtypes")
	public int queryPolicy(String accessType,String dataType,String env) {
		int pid = (dataType.equals("table"))?0:15;
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(ReadXml.class.getClassLoader().getResource(dataType + "Policies.xml"));
			Element typeList = document.getRootElement();
			for(Iterator i = typeList.elementIterator(); i.hasNext();){
				Element policies = (Element) i.next();
				if (policies.getName().equals(accessType)) {
					for(Iterator j = policies.elementIterator(); j.hasNext();){
						Element node=(Element) j.next();
						if (node.attributeValue("environment").equals(env)) {
							pid = Integer.parseInt(node.getText());
							//System.out.println(node.getName()+":"+node.getText());
						}
					}
				}
			}
		} catch (DocumentException e) {
			//System.out.println(e.getMessage());
		}
	  return pid;
	}


	public void createXml(String fileName) {
		Document document = DocumentHelper.createDocument();
		Element employees=document.addElement("employees");
		Element employee=employees.addElement("employee");
		Element name= employee.addElement("name");
		name.setText("ddvip");
		Element sex=employee.addElement("sex");
		sex.setText("m");
		Element age=employee.addElement("age");
		age.setText("29");
		try {
			Writer fileWriter=new FileWriter(fileName);
			XMLWriter xmlWriter=new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings({ "null" })
	public String[] queryDatabase(){
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(ReadXml.class.getClassLoader().getResource("platform.xml"));
			Element pList = document.getRootElement();

			for(Iterator<?> i = pList.elementIterator(); i.hasNext();){
				Element p = (Element) i.next();
				Iterator<?> j =p.elementIterator();
				Node name = (Node) j.next();
				Node url = (Node) j.next();
				Node user = (Node) j.next();
				Node password = (Node) j.next();
				System.out.println("name="+name.getText());
				System.out.println("url="+url.getText());
				System.out.println("user="+user.getText());
				System.out.println("password="+password.getText());
				Map<String, Node> map = null;
				map.put("name", name);
				map.put("url", url);
				map.put("user", user);
				map.put("password", password);
			}
		} catch (DocumentException e) {
			//System.out.println(e.getMessage());
		}
		return null;
	}

}