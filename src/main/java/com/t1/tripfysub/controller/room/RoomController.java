package com.t1.tripfysub.controller.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.t1.tripfysub.domain.dto.chat.ChatMessageDTO;
import com.t1.tripfysub.domain.dto.chat.ChatRoomDTO;
import com.t1.tripfysub.domain.dto.chat.CreateRoomData;
import com.t1.tripfysub.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

//채팅을 보여주기 위한 컨트롤러
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Log4j2
public class RoomController {
	
	private final ChatService chatservice;
	
//	//채팅방 조회 레퍼런스
//	@GetMapping(value = "/rooms")
//	public ModelAndView rooms() {
//		
//		log.info("# All chat Rooms");
//		ModelAndView mv = new ModelAndView("chat/rooms");
//		
//		mv.addObject("list", repository.findAllRooms());
//		
//		return mv;
//	}
	@GetMapping("/rooms")
	@ResponseBody
	public Map<String, Object> getRooms(HttpServletRequest req){
		Map<String, Object> datas = new HashMap<>();
		//req에서 세션 아이디 세팅
		String loginUser = "apple";
		//채팅방 다 긁어오기
		List<String> orgroomids = chatservice.getMyRoomIds(loginUser);
		List<ChatRoomDTO> rooms = new ArrayList<>();
		if(orgroomids.size()>0) {
			for (String orgroomid : orgroomids) {
				rooms.add(chatservice.getRoom(orgroomid));
			}
		}
		datas.put("rooms", rooms);
		// 유저썸네일 또는 채팅방사진
		//채팅방의 인원수
		return datas;
	}
	
	
//	//채팅방 개설 레퍼런스
//	@PostMapping(value = "/room")
//	public String create(@RequestParam String roomname, RedirectAttributes rttr) {
//		log.info("# Create Chat Room , name: " + roomname);
//		rttr.addFlashAttribute("roomName", repository.createChatRoomDTO(roomname));
//		return "redirect:/chat/rooms";
//	}
	
	@PostMapping(value = "/createroom")
	@ResponseBody
	public Map<String, Object> create(HttpServletRequest req, @RequestBody CreateRoomData infos) {
		Map<String, Object> datas = new HashMap<>();
		//자바스크립트 어레이나 객체로 값 받아서 연결해 넣기
		String roomname = infos.getRoomname();
		String masterid = infos.getMasterid();
		String[] followers = infos.getParr();
		
		//팔로워가 1명이면 개인챗, 팔로워가 2명 이상이면 단체챗. 첫 메시지 생성 후 db저장 id=system
		
		List<String> orgroomids = chatservice.getMyRoomIds(masterid);
		if(orgroomids.size()>0) {
			for (String orgroomid : orgroomids) {
				datas.put(orgroomid, chatservice.getRoom(orgroomid));
			}
		}
		
		ChatRoomDTO room = ChatRoomDTO.create(roomname, masterid);
		
		if(chatservice.createRoom(room)==1) {
			datas.put(room.getRoomId(), room);
			chatservice.invite(room.getRoomId(), masterid);
		}
		
		for (String follower : followers) {
			chatservice.invite(room.getRoomId(), follower);
		}
		
		return datas;
	}
	
//	//채팅방 입장 레퍼런스
//	@GetMapping("/room")
//	public String getRoom(String roomId, Model model) {
//		log.info(" # get Chat Room, roomId : " + roomId);
//		
//		model.addAttribute("room", repository.findRoomById(roomId));
//		System.out.println("야"+repository.findRoomById(roomId));
//		return "/chat/room";
//	}
	// 채팅방 입장
	@GetMapping(value = "/room")
	@ResponseBody
	public Map<String, Object> showRooms(HttpServletRequest req) {
		Map<String, Object> datas = new HashMap<>();
		String roomId = req.getParameter("roomId");
		
		//룸 정보 세팅
		ChatRoomDTO room = chatservice.getRoom(roomId);
		datas.put("room", room);

		req.getSession().setAttribute("differ", 0);
		int startrow = 0;
		
		//메시지 15개씩 불러오기
		List<ChatMessageDTO> messagelist = chatservice.getMessages(roomId, startrow);
		datas.put("messagelist", messagelist);
		
		//유저 썸네일 리스트 세팅

		return datas;
	}
	
	//더 로드
	@GetMapping(value = "/load")
	@ResponseBody
	public Map<String, Object> loadMsgs(HttpServletRequest req) {
		Map<String, Object> datas = new HashMap<>();
		String roomId = req.getParameter("roomId");
		int differ = (Integer)req.getSession().getAttribute("differ");
		int startrow = Integer.parseInt(req.getParameter("page"))+differ;
		
		//메시지 15개씩 불러오기
		List<ChatMessageDTO> messagelist = chatservice.getMessages(roomId, startrow);
		datas.put("messagelist", messagelist);
		
		//유저 썸네일 리스트 세팅

		return datas;
	}
	
}
