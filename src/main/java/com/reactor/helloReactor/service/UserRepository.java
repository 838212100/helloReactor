package com.reactor.helloReactor.service;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.reactor.helloReactor.modle.User;

import reactor.core.publisher.Mono;

/**
 * ReactiveCrudRepository的泛型分别是User和ID的类型；
 * @author 
 *
 */
public interface UserRepository extends ReactiveCrudRepository<User, String>{
	
	/**
	 * ReactiveCrudRepository已经提供了基本的增删改查的方法
	 * @param username
	 * @return
	 */
	Mono<User> findByUsername(String username);
	
	Mono<Long> deleteByUsername(String username);
	
}
