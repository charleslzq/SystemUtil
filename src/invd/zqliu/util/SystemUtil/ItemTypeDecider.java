package invd.zqliu.util.SystemUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public enum ItemTypeDecider {
	INSTANCE;
	private static Map<String, Set<String>> itemTypes;

	static{
		itemTypes = new HashMap<String, Set<String>>();
		Properties properties = SystemConfigurator.INSTANCE.getProperties(ItemTypeDecider.class.getName());
        Enumeration<?> names = properties.propertyNames();
        for ( Object name : Collections.list( names ) ){
            String itemType = name.toString();
            String stringType = properties.getProperty( itemType );
            if(stringType.contains("|")){
	            String[] stringTypes = stringType.split("\\|");
	            Set<String> types = new HashSet<String>();
	            for(String type:stringTypes){
	            	types.add(type.trim());
	            }
	            itemTypes.put(itemType, types);
            }else{
            	Set<String> types = new HashSet<String>();
            	types.add(stringType);
            	itemTypes.put(itemType, types);
            }
        }		
	}
	
	public List<String> evaluateStringType(String stringType){
		List<String> result = new ArrayList<String>();
		Iterator<String> it = itemTypes.keySet().iterator();
		while(it.hasNext()){
			String itemType = it.next();
			if(itemTypes.get(itemType).contains(stringType))
				result.add(itemType);
		}
		return result;
	}
}
