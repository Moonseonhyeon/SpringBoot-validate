package com.cos.validEx01.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.validEx01.RespDto;
import com.cos.validEx01.StatusCode;

//공통관심사 : advice
@Component
@Aspect
public class BindingAdvice {

	@Before("execution(* com.cos.validEx01.test.BindControllerTest.*(..))")	
	public void test1() {
		System.out.println("BindController에 오신것을 환영합니다.");

	}

	@After("execution(* com.cos.validEx01.test.BindControllerTest.*(..))")	
	public void test2() {
		System.out.println("BindControllerTest를 이용해 주셔서 감사합니다.");

	}

	// @Before @After @Around
	@Around("execution(* com.cos.validEx01..*Controller.*(..))") // 외우지말고 필요할 때 찾아보기. @Around 직전, 직후에 다 할 수 있다.
	public Object validationHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { // joinPoint(메서드의
																								// context)를 proxy로 데리고
																								// 온거임.

		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String method = proceedingJoinPoint.getSignature().getName();
		System.out.println("type : " + type);
		System.out.println("method : " + method);

		// joinPoint에 접근하는 이유는 bindingResult에

		Object[] args = proceedingJoinPoint.getArgs(); // 조인 포인트의 파라메터

		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;

				// bindingResult에 에러가 났을 때만
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();

					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					// 이 밑에는 함수로 만들어두면 되는데 일단 적어둠.
					RespDto<?> respDto = RespDto.builder().statusCode(StatusCode.FAIL).msg(method + "요청에 실패하였습니다.")
							.data(errorMap).build();

					return new ResponseEntity<RespDto>(respDto, HttpStatus.BAD_REQUEST); // 이렇게 return하면 다시 가지도 않아요
				} // end of if
			}
		}

		// 이후 처리를 위해서는 request에 접근하고 싶은건데 joinPoint안에 들어가서 지지고 볶고 난 후의 request를 내가 눈으로 볼
		// 수 있어야 해요 .
		// 이걸로 검증을 한다거나 하는 뒤에서 접근해서 무언가를 함(응답도 내가 할 수 있다.)

		return proceedingJoinPoint.proceed(); // 핵심 로직 타
	}

}
