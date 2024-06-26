package com.t1.tripfysub.mapper.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.t1.tripfysub.domain.dto.chat.ChatMessageDTO;
import com.t1.tripfysub.domain.dto.chat.ChatRoomUser;

@Mapper
public interface ChatMapper {
	//C
	int sendmsg(ChatMessageDTO message);
	//R
	List<ChatMessageDTO> getLog(String roomId, int startrow);
	Long getLastMessagenum(String roomId);
	//U
	int updateLastMessageInfo(ChatRoomUser cruser);
	//D
	int deleteAllMsg(long roomnum);
}
