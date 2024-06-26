package com.t1.tripfysub.domain.dto.chat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;

@Data
public class ChatRoomDTO {
	private long roomnum;
	private String roomId;
	private String roomname;
	private String masterid;
	//Spring에서 Websocket connection이 맺어진 세션
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatRoomDTO create(String roomname, String masterid) {
		ChatRoomDTO room = new ChatRoomDTO();
		
		room.roomId = UUID.randomUUID().toString();
		room.roomname = roomname;
		room.masterid = masterid;
		return room;
	}
}
