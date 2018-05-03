package com.websocket.client;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

public class App {
	public static WebSocketClient client;
   public static void main(String[] args) throws URISyntaxException, UnsupportedEncodingException {
	   client = new WebSocketClient(new URI("ws://172.16.9.12:8080/websocket"),new Draft_17()) {

				@Override
				public void onClose(int arg0, String arg1, boolean arg2) {
					 System.out.println("链接已关闭");			
				}
		
				@Override
				public void onError(Exception arg0) {
					arg0.printStackTrace();
		            System.out.println("发生错误，已关闭");
					
				}
		
				@Override
				public void onMessage(String arg0) {
					System.out.println("发到消息："+arg0);
					
				}
				
				
		
				@Override
				public void onMessage(ByteBuffer bytes) {
					 try {
			                System.out.println(new String(bytes.array(),"utf-8"));
			            } catch (UnsupportedEncodingException e) {
			                e.printStackTrace();
			            }
		//			super.onMessage(bytes);
				}
		
				@Override
				public void onOpen(ServerHandshake arg0) {
					System.out.println("打开链接");
					
				}	   
	   
	   };
		
		
	   client.connect();

	    while(!client.getReadyState().equals(READYSTATE.OPEN)){
	        System.out.println("还没打开");
	        try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    System.out.println("打开了");
	    send("hello world".getBytes("utf-8"));
	    client.send("hello world");
   }
   
   public static void send(byte[] bytes){
	    client.send(bytes);
	}
}
