package invd.zqliu.util.SystemUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public enum EnviromentConfigurator {
	INSTANCE;
	
	private Map<String, String> configurator;
	EnviromentConfigurator(){
		configurator = new HashMap<String, String>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("resources/EnvConfig.xml");
			Properties properties = new Properties();
			properties.loadFromXML(fis);
			for(Object key:properties.keySet()){
				String name = (String)key;
				String value = properties.getProperty(name);
				configurator.put(name, value);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(fis!=null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public String getProperty(String name){
		return configurator.get(name);
	}
	
	public Set<String> getKeys(){
		Set<String> result = new HashSet<String>();
		for(String name:configurator.keySet())
			result.add(name);
		return result;
	}

}
