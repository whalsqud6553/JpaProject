package com.bootProject.bootProject.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
	
	// 현재 연결된 세션들
	private final List<WebSocketSession> sessions = new ArrayList<>();
	
	// 소켓 연결 확인
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("{} 연결됨", session.getId());
		sessions.add(session);
		
		int sessionSize = sessions.size();
		for(WebSocketSession sess : sessions) {
			sess.sendMessage(new TextMessage("** 현재 접속자 수 : " + sessionSize + "명 **"));
		}
	}
	
	// 소켓 통신 시 메시지의 전송을 다루는 부분
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		// payload는 전송된 데이터를 의미
		String payload = message.getPayload();
		log.info("payload {}", payload);
		
		for(WebSocketSession sess : sessions) {
			sess.sendMessage(message);
		}
	}
	
	// 소켓 종료 확인
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("{} 연결 끊김", session.getId());
		sessions.remove(session);
		
		int sessionSize = sessions.size();
		for(WebSocketSession sess : sessions) {
			sess.sendMessage(new TextMessage("** 현재 접속자 수 : " + sessionSize + "명 **"));
		}
	}
}
