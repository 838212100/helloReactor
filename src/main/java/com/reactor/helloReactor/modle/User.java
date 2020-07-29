package com.reactor.helloReactor.modle;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//生成无参构造方法/getter/setter/hashCode/equals/toString
@Data
//生成所有参数构造方法
@AllArgsConstructor
//MongoDB是文档型的NoSQL数据库，因此，我们使用@Document注解User类
@NoArgsConstructor
@Document
public class User {
	// 注解属性id为ID
	@Id
	private String id;
	// 注解属性username为索引，并且不能重复
	@Indexed(unique = true)
    private String username;
    private String phone;
    private String email;
    private String name;
    private Date birthday;

}
//@AllArgsConstructor会导致@Data不生成无参构造方法，需要手动添加@NoArgsConstructor，
//如果没有无参构造方法，可能会导致比如com.fasterxml.jackson在序列化处理时报错
//@NoArgsConstructor