package com.example.mywork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.mywork.mapper")
@SpringBootApplication
public class MyworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyworkApplication.class, args);
	}

}
