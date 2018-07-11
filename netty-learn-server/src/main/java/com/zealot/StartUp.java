package com.zealot;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zealot.netty.learn.server.ChatNettyServer;

@SpringBootApplication
public class StartUp implements CommandLineRunner{
	
	@Resource
	private ChatNettyServer chatNettyServer;

	public static void main(String[] args) {

		SpringApplication.run(StartUp.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		
		chatNettyServer.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				chatNettyServer.close();
			}
		});
	}
	
}
