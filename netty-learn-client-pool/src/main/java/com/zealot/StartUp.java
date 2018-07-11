package com.zealot;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zealot.netty.learn.client.NettyPoolClientPool;

@SpringBootApplication
public class StartUp implements CommandLineRunner{
	
	@Resource
	private NettyPoolClientPool pool;

	public static void main(String[] args) {

		SpringApplication.run(StartUp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		pool.build();
	}	
	
	
}
