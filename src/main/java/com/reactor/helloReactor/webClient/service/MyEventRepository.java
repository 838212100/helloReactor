package com.reactor.helloReactor.webClient.service;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import com.reactor.helloReactor.webClient.model.MyEvent;

import reactor.core.publisher.Flux;

public interface MyEventRepository extends ReactiveMongoRepository<MyEvent, Long>{
	
	//作用类似linux的tail命令，被注解的方法将发送无线流，需要注解在返回值为Flux这样的多个元素的Publisher的方法上
	@Tailable
	Flux<MyEvent> findBy();//相当月findByAll()

}
