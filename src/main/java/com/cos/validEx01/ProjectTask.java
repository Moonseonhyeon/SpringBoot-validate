package com.cos.validEx01;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class ProjectTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//ConstrainViolationException
	@Size(max = 10, message = "Summary can not exceed the length")
	@NotBlank(message = "Summary cannot be blank") //내가 적은 메세지
	private String summary;
	@NotBlank(message = "acceptanceCriteria cannot be blank") //내가 적은 메세지
	private String acceptanceCriteria;
	private String status;
	@Email(message = "Your email XXX")
	private String email;
	
	//requestDto는 여러개   페이지 마다 따로따로 다 만들어야한다. 필요해요!
	//reponseDto는 하나만?
	//원래 모델은 setter는 만들면 안되고 getter만 있어야해서 @Data붙이면 안된다. 디비랑 동기화되게하게 하기 위해서.
	
	//컨트롤러
	
	
}



