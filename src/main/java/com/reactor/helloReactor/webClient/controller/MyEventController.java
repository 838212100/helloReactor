package com.reactor.helloReactor.webClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactor.helloReactor.webClient.model.MyEvent;
import com.reactor.helloReactor.webClient.service.MyEventRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/events")
public class MyEventController {

	@Autowired
	private MyEventRepository myEventRepository;
	
	/**
	 * 放入数据
	 * @param events
	 * @return
	 */
	//consumes 指定传入的数据是 application/stream+json
	@PostMapping(path = "", consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<Void> loadEvents(@RequestBody Flux<MyEvent> events){
		//insert返回的是保存成功的记录的Flux，但我们不需要，
		//使用then方法表示“忽略数据元素，只返回一个完成信号
		return myEventRepository.insert(events).then();
	}
	
	/**
	 * 获取数据
	 * @return
	 */
	//produces 指定返回类型是 application/stream+json
	@GetMapping(path = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<MyEvent> getEvents(){
		return myEventRepository.findBy();
	}
	
}
