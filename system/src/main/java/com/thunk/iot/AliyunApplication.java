package com.thunk.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: StartEntryClass
 * @Date:     2019/5/11 12:48
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@EnableScheduling
@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan("com.thunk.iot.dao")
public class AliyunApplication {

	public static void main(String[] args) {
		SpringApplication.run(AliyunApplication.class, args);
	}
}
