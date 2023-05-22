package com.homework.HWBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HwBackendApplication {
	//jpa사용시 스네이크 케이스를 쓰면 안된대여 무조건 카멜 케이스...
	public static void main(String[] args) {
		SpringApplication.run(HwBackendApplication.class, args);
	}

}
