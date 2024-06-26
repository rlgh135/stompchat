package com.t1.tripfysub.domain.dto.chat;

import lombok.Data;

@Data
public class ChatRoomUser {
	private String roomId;
	private String userid;
	private Long messagenum;
}
