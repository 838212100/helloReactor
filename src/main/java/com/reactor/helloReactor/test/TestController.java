package com.reactor.helloReactor.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class TestController {
	
	@GetMapping("hello")
	public Mono<String> hello() {
		return Mono.just("Welcome to reactive world ~");
	}
	
//	public Mono<Date> time(ServerRequest request){
//		HandlerFunction<ServerResponse> timeFunction = 
//				request -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(
//						Mono.just("Now is " + new SimpleDateFormat("HH:mm:ss").format(new Date())),String .class);
//	}

}
