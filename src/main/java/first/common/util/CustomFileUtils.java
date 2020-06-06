package first.common.util;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
 
@Component
public class CustomFileUtils {
    private String filePath;
    private int maxUploadCount;
    
    @Autowired
    public CustomFileUtils(String filePath, int maxUploadCount) {
    	this.filePath = filePath;
    	this.maxUploadCount = maxUploadCount;
    }
    
    public byte[] readFileToByteArray(String storedFileName) throws IOException {
    	return FileUtils.readFileToByteArray(new File(filePath + storedFileName));
    }
    
    public void deleteFile(String storedFileName) {
    	File file = new File(filePath + storedFileName);
        if(file.exists() == true) {
            file.delete();
        }
    }
    
    public boolean isExist(String storedFileName) {
    	File file = new File(filePath + storedFileName);
        return file.exists();
    }
        
    public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, MultipartHttpServletRequest multipartRequest) {
        Iterator<String> iterator = multipartRequest.getFileNames();
        
        if(multipartRequest.getFileMap().size() > maxUploadCount) 
        	throw new RuntimeException();
        
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String storedFileName = null;
         
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null;
         
        //String boardIdx = (String)map.get("IDX");
        String boardIdx = String.valueOf(map.get("idx"));
         
        File file = new File(filePath);
        if(file.exists() == false){
            file.mkdirs();
        }
        
        while(iterator.hasNext()){
            multipartFile = multipartRequest.getFile(iterator.next());
            
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
                listMap.put("userId", map.get("userId"));
                list.add(listMap);
            }
        }
        return list;
    }

    public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, MultipartHttpServletRequest multipartRequest) {
        Iterator<String> iterator = multipartRequest.getFileNames();
        
        if(multipartRequest.getFileMap().size() > maxUploadCount) 
        	throw new RuntimeException();
        
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String storedFileName = null;
         
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null;
         
        String boardIdx = (String)map.get("idx");
        String requestName = null;
        String idx = null;
         
        while(iterator.hasNext()){
            multipartFile = multipartRequest.getFile(iterator.next());
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
                listMap.put("userId", map.get("userId"));
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
                    listMap.put("userId", map.get("userId"));
                    list.add(listMap);
                }
            }
        }
        return list;
    }
}