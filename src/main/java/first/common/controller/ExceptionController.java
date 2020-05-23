package first.common.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    Logger log = Logger.getLogger(this.getClass());
	
    // 권한 에러 발생시
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String AccessDeniedException(Model model, Exception e) {
		log.debug(e.getMessage());
		model.addAttribute("message", "접근 할 수 없습니다.");

		return "/common/exception";
	}
	
	// 런타임 에러 발생시 (DB 관련 등등..)
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String errorException(Model model, Exception e) {
		log.debug(e.getMessage());
		model.addAttribute("message", "에러가 발생했습니다.");
	
		return "/common/exception";
	}
}
