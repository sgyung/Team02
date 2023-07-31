package log_analysis;


import java.awt.FileDialog;
import java.io.BufferedReader;
//import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logs {

	//임시경로
//	public static final String LOG_PATH = "d:/dev/team/sist_input_1.log"; 
//	public static final String LOG_PATH = "C:/Users/daver/OneDrive/바탕 화면/GroupProject/sist_input_1.log";
	public static final String FILE_EXTENSTION = ".dat";
	//브라우저 리스트
	public static final String[] BROWSER_LIST = {"ie", "Safari", "Chrome", "opera", "firefox"};
	
	private File CurrentLogFile = null; 
	private List<String> logList = null;
	private String[][] logArr = null;
	
	
	//생성자
	public Logs() {
		
	}
	public Logs(File CurrentLogFile) {
		this.CurrentLogFile = CurrentLogFile;
		
		try {
			readLogFile();
			splitLogs();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//log파일 읽어와서 set logList
	public void readLogFile( ) throws IOException {
		
		List<String> logList = null;
		
		if( CurrentLogFile.exists() ) {
			
			logList = new ArrayList<String>();
			BufferedReader br;
			br = new BufferedReader(new FileReader(CurrentLogFile));
			try {
				String temp = "";
				
				while( (temp = br.readLine()) != null ) {
					logList.add(temp);
				}
				
			}  finally {
				if( br != null ) { br.close(); }
			}//end finally
			
		}//end if
		
		this.logList = logList;
		
	}//readLogFile
	
	
	//로그데이터 나누기
	public void splitLogs(){
		
		String[][] logArr = new String[logList.size()][];
		
		for(int i = 0; i<logList.size(); i++) {
			logArr[i] = logList.get(i).split("]");
			for(int j = 0; j<logArr[i].length; j++) {
				logArr[i][j] = logArr[i][j].replace("[", "");
			}
		}
		
		
		this.logArr = logArr;
	}
	
	//로그데이터 나누기 (범위지정)
	public void splitLogs(int startLine, int endLine){
		
		 // 유효한 범위 내에 있는지 확인
		startLine = Math.max(0, startLine); // startLine이 음수가 아닌지 확인
		endLine = Math.min(endLine, logList.size() - 1); // endLine이 logList의 유효한 범위 내에 있는지 확인

	    // 해당 범위를 저장할 이중배열 변수
	    String[][] logArr = new String[endLine - startLine + 1][];
	    
	    int logArrIndex = 0; 
	    
	    for (int i = startLine-1; i < endLine; i++) {
	        String[] logParts = logList.get(i).split("]");
	        for (int j = 0; j < logParts.length; j++) {
	            logParts[j] = logParts[j].replace("[", "");
	        }
	        logArr[logArrIndex] = logParts;
	        logArrIndex++;
	    }

	    this.logArr = logArr;
	}
	
	/**
	 * 선택된 번호에 해당하는 로그데이터 List로 받기
	 * @param select 0 : httpStateCode 1 : keyValue, 2 : Browser, 3 : time
	 * @return logSelectList
	 */
	public List<String> selectLogData(int select) {
		List<String> logSelectList = new ArrayList<String>();		
		
		for(int i = 0; i<logArr.length; i++) {
			for(int j = 0; j<logArr[i].length; j++) {
				if( j == select) {
					logSelectList.add(logArr[i][j]);
				}
			}
		}//end for
		
		return logSelectList;
		
	}// selectLogData
		
	// 최다사용 키의 이름과 횟수 | java xx회
	public String maxKey() {
		
		List<String> httpStateCodeList = selectLogData(1);
		
        Map<String , Integer> keyCnt = new HashMap<String, Integer>();
		
		int startIndex = 0;
		int endIndex = 0;
		
		for( String url : httpStateCodeList ) {
			if(url.contains("key=")) {
				startIndex = url.indexOf("=")+1;
				endIndex = url.indexOf("&");
				String key = url.substring(startIndex,endIndex);
				keyCnt.put(key, keyCnt.getOrDefault(key, 0) + 1);
			}
		}
		
		String mostUsedKey = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : keyCnt.entrySet()) {
            String key = entry.getKey();
            int count = entry.getValue();
            if (count > maxCount) {
                mostUsedKey = key;
                maxCount = count;
            }
        }
        
        return mostUsedKey+ " " + maxCount;
        
	}
	
	// HTTP 상태코드 횟수 (Overload)
	public int parseLogCnt(int httpStatecode) {
		
		int cnt = 0;
		
		String state = Integer.toString(httpStatecode);
		
		List<String> httpStateCodeList = selectLogData(0);
		
		for( String code : httpStateCodeList ) {
			if( code.equals(state)) {
				cnt++;
			}
		}
		
		return cnt;
	}//parseStateCode
	
	// Browser 횟수 (Overload)
	public int parseLogCnt(String browser) {
		
		int cnt = 0;
		String state = browser;
		
		List<String> browserList = selectLogData(2); //2: browser
		
		for( String code : browserList ) {
			if( code.equals(state)) {
				cnt++;
			}
		}//end for
		
		return cnt;
	}//parseStateCode
	
	
	// log 비율 (Overload)
	public double parseLogRatio( int HttpStatecode ) {
		double result = 0;	
		
		int LogsCnt = parseLogCnt(HttpStatecode);
			
		double num = (double)LogsCnt / logArr.length * 100;
		
		result = Math.round(num*100)/100.0;		
		
		return result;
	}
	
	// Browser 비율 (Overload)
	public double parseLogRatio( String browser ) {
		
		int LogsCnt = parseLogCnt(browser);
		
		double result = 0;
		
		double num = (double)LogsCnt / logArr.length * 100;
		result = Math.round(num*100)/100.0;		
		
		return result;
	}	

	// 요청이 가장 많은 시간대
	public String maxReqTime() {		
		
		 Map<String , Integer> timeCnt = new HashMap<String, Integer>();
		 List<String> selectList = selectLogData(3);
		 
			for( String time : selectList ) {				
				String hour = time.split(" ")[1].substring(0,2);
				timeCnt.put(hour, timeCnt.getOrDefault(hour, 0) + 1);
			}
			
			String mostUsedTime = "";
	        int maxCount = 0;
	        for (Map.Entry<String, Integer> entry : timeCnt.entrySet()) {
	            String key = entry.getKey();
	            int count = entry.getValue();
	            if (count > maxCount) {
	            	mostUsedTime = key;
	                maxCount = count;
	            }
	        }
	        
	       	return mostUsedTime+"시";     			
	}
	
	public String logView() {
	    StringBuilder result = new StringBuilder();
	    String[] maxKeyValue = maxKey().split(" ");

	    result.append(String.format("%-20s%s%n", "최다사용 키", maxKeyValue[0]));
	    result.append(String.format("%-20s%s%n%n", "최다사용 횟수", maxKeyValue[1]));

	    result.append("브라우저별 접속 횟수와 비율\n");
	    for (String browser : BROWSER_LIST) {
	        result.append(String.format("%-20s%d회\t%.2f%%%n", browser, parseLogCnt(browser), parseLogRatio(browser)));
	    }

	    result.append("\n");

	    result.append(String.format("%-25s%d회\t%.2f%%%n", "서비스를 성공적으로 수행 (200)", parseLogCnt(200), parseLogRatio(200)));
	    result.append(String.format("%-38s%d회\t%.2f%%%n", "서비스 실패 (404)", parseLogCnt(404), parseLogRatio(404)));
	    result.append(String.format("%-35s%d회\t%.2f%%%n", "비정상적인 요청 (403)", parseLogCnt(403), parseLogRatio(403)));
	    result.append(String.format("%-36s%d회\t%.2f%%%n%n", "요청에 대한 에러 (500)", parseLogCnt(500), parseLogRatio(500)));

	    result.append(String.format("%-25s%s%n", "요청이 가장 많은 시간", maxReqTime()));

	    return result.toString();
	}
	
	// report 생성
	public void saveReport() throws IOException {
		String savePath = "e:/dev/temp/report";
		File file = new File(savePath);
		
		//timeStamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long timeStampDate = timestamp.getTime();
		
		
		// timeStamp => 포멧형식으로 변환
		Date date = new Date(timeStampDate);
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd kk:mm:ss");
		String currentDate = sdf.format(date);
		
		
		String fileName = "/report_" + timeStampDate + FILE_EXTENSTION;
		
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		
		String line = "===========================\n";
		String header = CurrentLogFile.getName()+"\t"+currentDate+"\n";
		
		//디렉토리가 존재하지 않으면 디렉토리 생성
		try {
			file.mkdirs();
			fos = new FileOutputStream(savePath + fileName);
			osw = new OutputStreamWriter(fos);
			osw.write(line+header+line+logView());
			osw.flush();
			
		} finally {
			if( osw != null) {osw.close();}			
		}
	}
	
	//getter,setter
	public String[][] getLogArr() {
		return logArr;
	}
	public void setLogArr(String[][] logArr) {
		this.logArr = logArr;
	}
	public List<String> getLogList() {
		return logList;
	}
	public void setLogList(List<String> logList) {
		this.logList = logList;
	}
	
	

}