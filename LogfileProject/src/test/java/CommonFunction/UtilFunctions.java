package CommonFunction;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UtilFunctions {
	
	private FileInputStream file=null;
	public static Properties prop;
	private static String strFileContent="";
	private static String strFileLine="";
	private static JSONObject JsonObject= null;
	private static JSONObject JsonObject1= null;
	private static JSONArray JsonArray = null;	
	private static String strEventID = "";
	private static String strEventID1 = "";
	private static String strState = "";
	private static String strState1 = "";
	private static long lngTimeStamp,lngTimeStamp1,lngExecutionTime;
	private static String strAlertForJob="";
	private static String strEventDuration="";
	private static List<List<String>> list=null;
	
	//Function to create connection with property file
	public static void fnConnectPropFile(String strPropertyFileLocation) throws IOException{
		
		FileInputStream file = new FileInputStream(strPropertyFileLocation);
		prop = new Properties();
		prop.load(file);
		
	}	

	//Function to traverse through the log file and take values
	public static List<List<String>> fnReadLogFile(String strFileLocation) throws IOException, JSONException{
		
		FileReader file = new FileReader("C:\\LogFile\\logfile.txt");
		BufferedReader fileReader = new BufferedReader(file);
		strFileLine = fileReader.readLine();
		while(strFileLine!=null){
			strFileContent = strFileContent + strFileLine + ",";
			strFileLine = fileReader.readLine();
		}
		
		// Converting the Text file In JSON Array
		strFileContent = "[" + strFileContent ;
		strFileContent = strFileContent.substring(0,strFileContent.length()-1);
		strFileContent = strFileContent + "]";
		
		//Looping through the JSONArray 
		JsonArray = new JSONArray(strFileContent);
		list = new ArrayList<List<String>>();
		for(int iCounter =0;iCounter<JsonArray.length()-1;iCounter++){
			//Taking the Array value for file
			List<String> list1 = new ArrayList<String>();
			JsonObject = (JSONObject) JsonArray.get(iCounter);
			strEventID = (String) JsonObject.get("id");
						
			for(int iInnerCounter =iCounter + 1;iInnerCounter<JsonArray.length()-1;iInnerCounter++){
				//Looping in through end of file to find matching Event ID 
				JsonObject1 = (JSONObject) JsonArray.get(iInnerCounter);
				strEventID1 = (String) JsonObject1.get("id");				
				if(strEventID.equalsIgnoreCase(strEventID1)){
					strState = (String) JsonObject.get("state");
					strState1 = (String) JsonObject1.get("state");
					lngTimeStamp =(Long) JsonObject.get("timestamp");
					lngTimeStamp1 =(Long) JsonObject1.get("timestamp");
					if(strState.equals("STARTED")){
						lngExecutionTime = lngTimeStamp1 - lngTimeStamp;
					}else{
						lngExecutionTime = lngTimeStamp - lngTimeStamp1;
					}
					
					//Setting Alert value 
					if(lngExecutionTime>4){
						strAlertForJob = "True";
					}else{
						strAlertForJob = "False";
					}
					
					//Converting the duration of job to String 
					strEventDuration = String.valueOf(lngExecutionTime);
					// Adding all values in list 
					list1.add(strEventID);
					list1.add(strEventDuration);
					list1.add(strAlertForJob);
				}
			}
			if(!list1.isEmpty()){
				list.add(list1);
			}
			
		}
		return list;

	}

}
