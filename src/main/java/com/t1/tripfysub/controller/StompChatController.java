package com.t1.tripfysub.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.t1.tripfysub.domain.dto.chat.ChatMessageDTO;
import com.t1.tripfysub.domain.dto.chat.ChatRoomUser;
import com.t1.tripfysub.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {

	private final ChatService chatservice;
	/*
	 * @MessageMapping 을 통해 WebSocket으로 들어오는 메세지 발행을 처리한다. Client에서는 prefix를 붙여
	 * "/pub/chat/enter"로 발행 요청을 하면 Controller가 해당 메세지를 받아 처리하는데, 메세지가 발행되면
	 * "/sub/chat/room/[roomId]"로 메세지가 전송되는 것을 볼 수 있다.
	 * 
	 * 
	 * 
	 * Client에서는 해당 주소를 SUBSCRIBE하고 있다가 메세지가 전달되면 화면에 출력한다. 이때
	 * /sub/chat/room/[roomId]는 채팅방을 구분하는 값이다.
	 * 
	 * 
	 * 
	 * 기존의 핸들러 ChatHandler의 역할을 대신 해주므로 핸들러는 없어도 된다.
	 */

	// 특정 브로커로 메시지 전달
	private final SimpMessagingTemplate template;

	// Client가 SEND할 수 있는 경로
	// stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
	// "/pub/chat/enter"
	@MessageMapping(value = "/chat/enter")
	public void enter(ChatMessageDTO message) {
		System.out.println("StompController - "+message.getUserid()+": enter");
		message.setMessage(message.getUserid() + "님이 참여하였습니다.");
		System.out.println("엔터 - "+message);
		template.convertAndSend("/sub/chat/room" + message.getRoomId(), message);
		System.out.println("엔터 - 보냈음?");
	}

	@MessageMapping(value = "/chat/message")
	public void message(ChatMessageDTO message) {
		ChatMessageDTO chat = new ChatMessageDTO();
		chat.setRoomId(message.getRoomId());
		chat.setMessage(message.getMessage());
		chat.setUserid(message.getUserid());
		//이미지 가르는 로직
		chat.setEmSysname("no");
		
		if(chatservice.addMsg(chat)) {
			System.out.println("디비처리 완료!");
			
			//라스트메시지추가
			ChatRoomUser cruser = new ChatRoomUser();
			cruser.setMessagenum(chatservice.getLastMessagenum(message.getRoomId()));
			cruser.setRoomId(message.getRoomId());
			cruser.setUserid(message.getUserid());
			
			if(chatservice.updateLastMsg(cruser)) {
				System.out.println("last message 업데이트 완료!");
			}
		}
		
		System.out.println("StompController - "+message.getUserid()+": "+message.getMessage());
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}
}
