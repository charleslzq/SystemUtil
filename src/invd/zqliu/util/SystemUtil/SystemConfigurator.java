package invd.zqliu.util.SystemUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public enum SystemConfigurator {
	INSTANCE;
	
	private Map<String, Properties> configurator;
	SystemConfigurator(){
		configurator = new HashMap<String, Properties>();
		try {
			loadConfigFile("resources/config.xml");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addProperties(String name, String filePath) throws IOException, IllegalArgumentException{
		configurator.put(name, loadPropertiesFromFile(filePath));
	}
	
	public void addProperties(String name, Properties properties){
		configurator.put(name, properties);
	}
	
	public Properties loadPropertiesFromFile(String filePath) throws IOException, IllegalArgumentException{
		Properties properties = new Properties();
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		
		if(filePath.endsWith(".properties")){
			properties.load(fis);
		}else if(filePath.endsWith(".xml")){
			properties.loadFromXML(fis);
		}else{
			fis.close();
			throw new IllegalArgumentException("Not a property file!");
		}
		
		fis.close();
		return properties;
	}
	
	public void loadConfigFile(String configPath) throws IOException, IllegalArgumentException{
		Properties config = loadPropertiesFromFile(configPath);
		loadConfigProperties(config);
	}
	
	public void loadConfigProperties(Properties config){
		Iterator<Object> it = config.keySet().iterator();
		while(it.hasNext()){
			try{
				String name = (String)it.next();
				String filePath = config.getProperty(name);
				addProperties(name,filePath);
			}catch(IllegalArgumentException iae){
				iae.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public Properties getProperties(String name){
		return configurator.get(name);
	}
	
	public Set<String> keySet(){
		Set<String> result = new HashSet<String>();
		for(String name:configurator.keySet())
			result.add(name);
		return result;
	}
	
	private Object readResolve(){
        return INSTANCE;
    }
	
}
