package first.common.logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ppap extends HandlerInterceptorAdapter {
    Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("======================================          START         ======================================");
			log.debug(" Request URI \t:  " + request.getRequestURI());
		}
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("ID") == null) {
			request.setAttribute("MSG", "로그인 해주세요.");
			request.setAttribute("ADDRESS", "/first/sample/openBoardList.do");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/common/openErrorPage.do");
			dispatcher.forward(request, response);
			//response.sendRedirect("/first/login/openBoardList.do");
			return false;
		}
		
		String id = request.getParameter("CREA_ID");
		if (id != null)
			if(!id.equals((String)session.getAttribute("ID"))) {
				request.setAttribute("MSG", "정상적인 접근이 아닙니다.");
				request.setAttribute("ADDRESS", "/first/sample/openBoardList.do");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/common/openErrorPage.do");
				dispatcher.forward(request, response);
				//response.sendRedirect("/first/sample/openErrorPage.do");
				return false;
			}
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("======================================           END          ======================================\n");
		}
	}
}