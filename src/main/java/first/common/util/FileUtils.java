package first.common.util;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
 
@Component("fileUtils")
public class FileUtils {
    private static String filePath;

    public FileUtils() {
    	String os = System.getProperty("os.name");
    	if(os.toLowerCase().contains("windows")) {
    		filePath = "C:\\Users\\Administrator\\Desktop\\file\\";
    	}
    	else {
    		filePath = "/var/lib/tomcat8/file/";
    	}
    }
    
    public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String storedFileName = null;
         
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null;
         
        //String boardIdx = (String)map.get("IDX");
        String boardIdx = String.valueOf(map.get("IDX"));
         
        File file = new File(filePath);
        if(file.exists() == false){
            file.mkdirs();
        }
        
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                storedFileName = CommonUtils.getRandomString();
                 
                file = new File(filePath + storedFileName);
                try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
                 
                listMap = new HashMap<String,Object>();
                listMap.put("BOARD_IDX", boardIdx);
                listMap.put("ORIGINAL_FILE_NAME", originalFileName);
                listMap.put("STORED_FILE_NAME", storedFileName);
                listMap.put("FILE_SIZE", multipartFile.getSize());
                list.add(listMap);
            }
        }
        return list;
    }

    public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String storedFileName = null;
         
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null;
         
        String boardIdx = (String)map.get("IDX");
        String requestName = null;
        String idx = null;
         
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            //System.out.println("#$)(*%&#)$(*%$%&*)($&%$&%($*%$&(*&#($*)%&$: "+multipartFile.getName());
            //System.out.println("#$)(*%&#)$(*%$%&*)($&%$&%($*%$&(*&#($*)%&$: "+multipartFile.isEmpty());
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                storedFileName = CommonUtils.getRandomString();
                 
                try {
					multipartFile.transferTo(new File(filePath + storedFileName));
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
                 
                listMap = new HashMap<String,Object>();
                listMap.put("IS_NEW", "Y");
                listMap.put("BOARD_IDX", boardIdx);
                listMap.put("ORIGINAL_FILE_NAME", originalFileName);
                listMap.put("STORED_FILE_NAME", storedFileName);
                listMap.put("FILE_SIZE", multipartFile.getSize());
                list.add(listMap);
            }
            else{
                requestName = multipartFile.getName();
                //System.out.println("requestName: "+requestName);
                idx = "IDX_"+requestName.substring(requestName.indexOf("_")+1);
                //System.out.println("idx: "+idx);
                //System.out.println("map.containsKey(idx): "+map.containsKey(idx));
                //System.out.println("map.get(idx): "+map.get(idx));
                if(map.containsKey(idx) == true && map.get(idx) != null){
                    listMap = new HashMap<String,Object>();
                    listMap.put("IS_NEW", "N");
                    listMap.put("FILE_IDX", map.get(idx));
                    list.add(listMap);
                }
            }
        }
        return list;
    }
}