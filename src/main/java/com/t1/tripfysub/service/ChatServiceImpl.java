package com.t1.tripfysub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.t1.tripfysub.domain.dto.chat.ChatMessageDTO;
import com.t1.tripfysub.domain.dto.chat.ChatRoomDTO;
import com.t1.tripfysub.domain.dto.chat.ChatRoomUser;
import com.t1.tripfysub.mapper.chat.ChatMapper;
import com.t1.tripfysub.mapper.chat.ChatRoomMapper;
@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	private ChatMapper chatmapper;
	
	@Autowired
	private ChatRoomMapper roommapper;
	@Override
	public List<String> getMyRoomIds(String userid) {
		return roommapper.getRoomIdsByUserId(userid);
	}
	@Override
	public ChatRoomDTO getRoom(String roomid) {
		return roommapper.getRoom(roomid);
	}
	@Override
	public int createRoom(ChatRoomDTO room) {
		return roommapper.createRoom(room);
	}
	@Override
	public boolean invite(String roomId, String follower) {
		return roommapper.invite(roomId, follower)==1;
	}
	@Override
	public List<ChatMessageDTO> getMessages(String roomId, int startrow) {
		return chatmapper.getLog(roomId ,startrow);
	}
	@Override
	public boolean addMsg(ChatMessageDTO chat) {
		return chatmapper.sendmsg(chat)==1;
	}
	@Override
	public Long getLastMessagenum(String roomId) {
		return chatmapper.getLastMessagenum(roomId);
	}
	@Override
	public boolean updateLastMsg(ChatRoomUser cruser) {
		return chatmapper.updateLastMessageInfo(cruser)==1;
	}
}
