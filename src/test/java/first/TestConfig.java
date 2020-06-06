package first;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/config/*-servlet.xml", 
		"classpath*:config/spring/context-*.xml",
		"classpath:config/spring/test-context-*.xml"})
@WebAppConfiguration
@Transactional
public class TestConfig {
    Logger log = Logger.getLogger(this.getClass());
    
    private static String filePath;
    private static SqlSessionTemplate sqlSession;
    
    @Autowired
    public void setFilePath(String filePath){
        TestConfig.filePath = filePath;
    }
    
    @Autowired
    public void setSqlSession(SqlSessionTemplate sqlSession){
        TestConfig.sqlSession = sqlSession;
    }
    
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
        				.webAppContextSetup(this.wac)
        				.apply(springSecurity())
        				.build();	
    }
    
    @AfterClass
    public static void afterAll() throws Exception {
    	String staticTestFile = sqlSession.selectOne("test.selectStaticTestFile");
       	File file = new File(filePath);
    	File[] fileList = file.listFiles();
    	for(File f : fileList) {
    		if(!f.getName().equals(staticTestFile)) {
    			f.delete();
    		}
    	}
    } 
   
    @Ignore
    @Test
    @Rollback(false)
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void inset_db_data() throws Exception {
    	for(int i=1; i<=99; i++) {
        	this.mockMvc
	            .perform(fileUpload("/board/insertBoard.do")
	            .param("title", "제목 "+String.valueOf(i))
	            .param("contents", "내용 "+String.valueOf(i)));
    	}

    	MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
    	this.mockMvc
        .perform(fileUpload("/board/insertBoard.do")
		.file(file)
        .param("title", "제목 100")
        .param("contents", "내용 100"));
    	
    	for(int i=101; i<=200; i++) {
        	this.mockMvc
	            .perform(fileUpload("/board/insertBoard.do")
	            .param("title", "제목 "+String.valueOf(i))
	            .param("contents", "내용 "+String.valueOf(i)));
    	}
    	
    }
}
