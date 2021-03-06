package com.goldbee.luckdraw.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description 返回消息
 * @author chenxm66777123
 * @Date 2018年12月23日
 * @version 1.0.0
 */

@Getter
@Setter
@NoArgsConstructor
public class TextMessage {

	private String MsgType;

	private String ToUserName;

	private String FromUserName;

	private long CreateTime;

	private String Content;

	private Integer FuncFlag;
	


}
