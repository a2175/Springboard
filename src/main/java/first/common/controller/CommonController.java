package first.common.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.board.vo.FileVO;
import first.common.common.CommandMap;
import first.common.service.CommonService;

@Controller
public class CommonController {
	private static String filePath;
	Logger log = Logger.getLogger(this.getClass());
	
	private CommonService commonService;
	
	@Autowired
    public CommonController(CommonService commonService) {
    	String os = System.getProperty("os.name");
    	if(os.toLowerCase().contains("windows")) {
    		filePath = "C:\\Users\\Administrator\\Desktop\\file\\";
    	}
    	else {
    		filePath = "/var/lib/tomcat8/file/";
    	}
    	
    	this.commonService = commonService;
    }
    
	@RequestMapping(value="/common/downloadFile.do")
	public void downloadFile(CommandMap commandMap, HttpServletResponse response) {
		FileVO file = commonService.selectFileInfo(commandMap.getMap());
		String storedFileName = file.getStored_file_name();
		String originalFileName = file.getOriginal_file_name();
		
		byte fileByte[];
		try {
			fileByte = FileUtils.readFileToByteArray(new File(filePath+storedFileName));
			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/common/openErrorPage.do")
    public ModelAndView openErrorPage(CommandMap commandMap, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/common/commonError");
        
        mv.addObject("MSG", request.getAttribute("MSG"));
        mv.addObject("ADDRESS", request.getAttribute("ADDRESS"));
        
        return mv;
    }
}