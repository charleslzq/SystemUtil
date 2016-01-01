package invd.zqliu.util.SystemUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public enum SystemConfigurator {
	INSTANCE;
	
	private Map<String, Properties> configurator;
	private String configBase;
	SystemConfigurator(){
		configurator = new HashMap<String, Properties>();
		try {
			configBase = EnviromentConfigurator.INSTANCE.getProperty("ConfigBase");
			String config = EnviromentConfigurator.INSTANCE.getProperty("Config");
			loadConfigFile(config);
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
	
	public Properties loadPropertiesFromFile(String filePath) throws IOException{
		Properties properties = new Properties();
		File file = new File(configBase+filePath);
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
	
	public void loadConfigFile(String configPath) throws IOException{
		Properties config = loadPropertiesFromFile(configPath);
		loadConfigProperties(config);
	}
	
	public void loadConfigProperties(Properties config){
		for(Object key:config.keySet()){
			try{
				String name = (String)key;
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
		return (Properties) configurator.get(name).clone();
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
