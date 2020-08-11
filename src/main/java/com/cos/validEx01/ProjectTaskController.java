package com.cos.validEx01;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/api/board")
public class ProjectTaskController {
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	//1 : 정상
	//2 : 아이디 중복
	//...프로토콜만들어서 협업시 약속아니면 HTTP 상태 코드로 해도된다.
	//ResponseEntity<?>  : 내가 만든 CommonDto같은 거다.
	@PostMapping({"", "/"})
	public ResponseEntity<?> save(@Valid @RequestBody ProjectTask requestProjectTask, BindingResult bindingResult){
		//request안에 있는 거니까 필터에서 처리한다. 컨트롤러 타기 전에 BindingResult가 IoC되어 있다. 그래서 DI해서 쓸 수 있다.
		
		
			//System.out.println(bindingResult.getFieldError());
		
		
		ProjectTask entityProjectTask = projectTaskRepository.save(requestProjectTask);
		RespDto<?> respDto = RespDto.builder()
				.statusCode(StatusCode.OK)
				.msg("요청에 성공하였습니다.")
				.data(entityProjectTask)
				.build();
		//return 할 때 ResponseEntity<?>를 쓰면 편함.
		//?에 내가 return하고 싶은 것을 넣는다
		//ex) SELECT -> return할  object type
		//save했을 때 많이 쓰이는 숫자값(1:정상, 2: 아이디, 3.....)
		
		//http 상태코드 참고
		return new ResponseEntity<RespDto>(respDto, HttpStatus.CREATED); //201
		
		//헤더말고 바디만 까서 status 확인하려고 내가 RequestDto만들어서 하는 방법이 있다
		//예전에 만들었던 CommonRespCto를 만들면 status(상태값)을 하나 더 넣을 수 있어서 추천
		
	}

}
