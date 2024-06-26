package com.t1.tripfysub.service;

import java.util.List;

import com.t1.tripfysub.domain.dto.chat.ChatMessageDTO;
import com.t1.tripfysub.domain.dto.chat.ChatRoomDTO;
import com.t1.tripfysub.domain.dto.chat.ChatRoomUser;

public interface ChatService {

	List<String> getMyRoomIds(String userid);

	ChatRoomDTO getRoom(String roomid);

	int createRoom(ChatRoomDTO room);

	boolean invite(String roomId, String follower);

	List<ChatMessageDTO> getMessages(String roomId, int startrow);

	boolean addMsg(ChatMessageDTO chat);

	Long getLastMessagenum(String roomId);

	boolean updateLastMsg(ChatRoomUser cruser);

}
