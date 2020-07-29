package com.reactor.helloReactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactor.helloReactor.modle.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 保存或更新
	 * 如果传入的user没有id属性，由于username是unique的，在重复的情况下可能会报错
	 * 这时 找到以保存的user记录用传入的user更新它
	 * @param user
	 * @return
	 */
	public Mono<User> save(User user){
		return userRepository.save(user)
				.onErrorResume(e ->//onErrorResume进行错误处理
						userRepository.findByUsername(user.getUsername())//找到username重复的记录
						.flatMap(originalUser -> {//由于函数式为User -> Publisher，所以用flatMap
							user.setId(originalUser.getId());
							return userRepository.save(user);//拿到ID从而进行更新而不是创建
						}));
	}
	
	public Mono<Long> deleteByUsername(String username){
		return userRepository.deleteByUsername(username);
	}
	
	public Mono<User> findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public Flux<User> findAll(){
		return userRepository.findAll();
	}

}
