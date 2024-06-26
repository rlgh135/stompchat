package com.t1.tripfysub.domain.dto.chat;

import lombok.Data;

@Data
public class CreateRoomData {
	private String roomname;
	private String masterid;
	private String[] parr;
}
