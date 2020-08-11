package com.cos.validEx01;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RespDto<T> {
	private int statusCode; // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 -> 이거만 읽어서 정상이면 좋고 실패면 메세지랑 데이터를 읽으면 된다.
	private String msg;
	private T data; //여기에 에러도 담고 데이터도 담을 거다.
}
