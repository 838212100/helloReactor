package com.reactor.test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Test {
	
	public static void main(String[] args) {
		Mono<String> monoMap = Mono.just("1").map(a -> a + "-----------1");
		monoMap.subscribe(System.out::println);
		
//		Mono<String> fluxMap = Flux.just("1").map(a -> a + "1");
//		fluxMap.subscribe(System.out::println);
		
		/*
		 * Flux<Integer> ints = Flux.range(2, 3); ints.subscribe(System.out::println);
		 * 
		 * ints.subscribe( i -> System.out.println(i) );
		 */
		
		
		Flux<Integer> ints = Flux.range(1, 4);
		ints.subscribe(i -> System.out.println(i),
		    error -> System.err.println("Error " + error),
		    () -> System.out.println("Done"),
		    sub -> sub.request(2));
	}

}
