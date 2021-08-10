package CommonFunction;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
 

public class Runner {
	
	public static String strLogFileLocation;	
	private static List<List<String>> list;
	
	public static void main(String[] args) throws IOException, JSONException {
		UtilFunctions.fnConnectPropFile(".\\src\\test\\resources\\properties\\config.properties");
		strLogFileLocation = UtilFunctions.prop.getProperty("FileLocation");
		list =UtilFunctions.fnReadLogFile(strLogFileLocation);
		for(List allList:list){
			System.out.println("Event ID --" + allList.get(0) + "| Event Duration--" + allList.get(1) + "| Alert --" + allList.get(2));
		}
		
	}

}
