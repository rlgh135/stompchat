package com.t1.tripfysub.mapper.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.t1.tripfysub.domain.dto.chat.ChatRoomDTO;

@Mapper
public interface ChatRoomMapper {
	//C
	int createRoom(ChatRoomDTO room); 
	int invite(String roomId, String userid);
	
	//R
	ChatRoomDTO getRoom(String roomId);
	List<ChatRoomDTO> getRooms(String masterid);
	List<String> getRoomIdsByUserId(String userid);
	
	//D
	int deleteAllRooms(long guidenum);
	int deleteRoom(long roomnum);

}
