package com.t1.tripfysub.domain.dto.chat;

import lombok.Data;

@Data
public class ChatMessageDTO {
	private String roomId;
	private String message;
	private String userid;
	private String emSysname;
	private long messagenum;
}
