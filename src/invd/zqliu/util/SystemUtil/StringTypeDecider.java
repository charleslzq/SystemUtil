package invd.zqliu.util.SystemUtil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StringTypeDecider {
	INSTANCE;
	
	private static Map<String, String> stringTypes;
	static{
		stringTypes = new HashMap<String, String>();
		Properties properties = SystemConfigurator.INSTANCE.getProperties(StringTypeDecider.class.getName());
        Enumeration<?> names = properties.propertyNames();
        for ( Object name : Collections.list( names ) ){
            String typeName = name.toString();
            String reg = properties.getProperty( typeName );
            stringTypes.put(typeName, reg);
        }
		
	}
	
	public Set<String> getTypeNames(){
		return stringTypes.keySet();
	}
	
	public String evaluateString(String s){
		Iterator<String> it = stringTypes.keySet().iterator();
		while(it.hasNext()){
			String typeName = it.next();
			Pattern pattern = Pattern.compile(stringTypes.get(typeName));
			Matcher matcher = pattern.matcher(s.trim());
			if(matcher.matches())
				return typeName;
		}
		
		return "NO_MATCH";
	}
}
