package com.reactor.helloReactor;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

import org.junit.Test;
import org.reactivestreams.Subscription;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactor.helloReactor.modle.User;
import com.reactor.helloReactor.webClient.model.MyEvent;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class TestControllerTest {

	@Test
	public void testMap() {
		StepVerifier.create(Flux.range(1, 6)
	            .map(i -> i * i))
	            .expectNext(1, 4, 9, 16, 25, 36)
	            .verifyComplete();
	}
	
	@Test
	public void testFlatMap() {
		StepVerifier.create(
				Flux.just("abcd","qwer")
				.flatMap(s -> Flux.fromArray(s.split("\\s*"))
						.delayElements(Duration.ofMillis(100)))
				.doOnNext(System.out::print))
		.expectNextCount(8)
		.verifyComplete();
	}
	
	@Test
	public void testFilter() {
		StepVerifier.create(Flux.range(1, 6)
				.filter(i -> i % 2 ==1)
				.map(i -> i*i))
		.expectNext(1,9,25)
		.verifyComplete();
	}
	
	@Test
	public void testDelayElements() {
	    Flux.range(0, 10)
	            .delayElements(Duration.ofMillis(1000))
	            .log()
	            .blockLast();
	}
	
	
	
	/**
	 * zip - 一对一合并
	  *  看到zip这个词可能会联想到拉链，它能够将多个流一对一的合并起来。
	 * zip有多个方法变体，我们介绍一个最常见的二合一的
	 * @throws InterruptedException 
	 */
	@Test
	public void testZip() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		Flux.zip(
				getZipDescFlux(), Flux.interval(Duration.ofMillis(200)))
		.subscribe(t -> System.out.println(t.getT1()), null, countDownLatch::countDown);
		countDownLatch.await(10, TimeUnit.SECONDS);
	}
	
	/**
	 * 返回用空格拆分的字符串数据流
	 * @return
	 */
	private Flux<String> getZipDescFlux(){
		String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
		return Flux.fromArray(desc.split("\\s+"));
	}
	
	@Test
	public void testSyncAsync() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		Mono.fromCallable(() -> getStringSync())
			.subscribeOn(Schedulers.elastic()) 
			.subscribe(System.out::println, null, countDownLatch::countDown);
		//会等待countDown倒数至0，最多等待10秒钟。
		countDownLatch.await(10, TimeUnit.SECONDS);
	}
	
	private String getStringSync() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return "Hello, Reactor!";
	}
	
	@Test
	public void testErrorHandling() {
		Flux.range(1, 6)
			.map(i -> 10/(i-3))
			.onErrorResume(e -> Mono.just(new Random().nextInt(6)))
			.map(i -> i*i)
			.subscribe(System.out::println, System.err::println);
	}

	
	@Test
	public void tt() {
		LongAdder statsCancel = new LongAdder();
		Flux<String> flux = 
				Flux.just("foo","bar")
				.doFinally(type -> {
					if(type == SignalType.CANCEL)
						statsCancel.increment();
				})
				.take(1);
	}
	
	@Test
	public void testBackressure() {
		Flux.just("foo", "sdf")
			.doOnRequest(n -> System.out.println("Request " + n + " values..."))
			.subscribe(new BaseSubscriber<String>() {

				@Override
				protected void hookOnSubscribe(Subscription subscription) {
					System.out.println("Subscribed and make a request...");
					request(1);
				}

				@Override
				protected void hookOnNext(String value) {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Get value [" + value + "]");
					request(3);
				}
				
			});
	}
	
	@Test
	public void webClientTest1() throws InterruptedException {
		/**
		 * 定义一个CountDownLatch，初始为1，则会等待执行1次countDown方法后结束
		 * 不使用它的话，测试方法所在的线程会直接返回而不会等待数据流发出完毕
		 */
		//CountDownLatch countDownLatch = new CountDownLatch(1);
		//创建WebClient对象并指定baseUrl
		WebClient webClient = WebClient.create("http://localhost:8099/");
		Mono<String> resp = webClient
				//HTTP GET
				.get().uri("/hello")
				//异步地获取response信息
				.retrieve()
				//将response body解析为字符串
				.bodyToMono(String.class).log();
		//打印
		resp.subscribe(System.out::println);
		TimeUnit.SECONDS.sleep(1);
		//由于是异步的，我们将测试线程sleep 1秒确保拿到response，也可以用CountDownLatch
		//resp.subscribe(System.out::println, null, countDownLatch::countDown);
		//等待countDown倒数至0，最多等待10秒钟。
		//countDownLatch.await(1, TimeUnit.SECONDS);
	}
	
	@Test
    public void webClientTest2() throws InterruptedException {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8099").build(); // 1
        webClient
                .get().uri("/user")
                //配置请求Header：Content-Type: application/stream+json
                .accept(MediaType.APPLICATION_STREAM_JSON) 
                //获取response信息，返回值为ClientResponse，retrive()可以看做是exchange()方法的“快捷版”
                .exchange()
                //使用flatMap来将ClientResponse映射为Flux
                .flatMapMany(response -> response.bodyToFlux(User.class))
                //只读地peek每个元素，然后打印出来，它并不是subscribe，所以不会触发流
                .doOnNext(System.out::println)
                //上个例子中sleep的方式有点low，blockLast方法，顾名思义，在收到最后一个元素前会阻塞，响应式业务场景中慎用
                .blockLast();
    }
	
    @Test
    public void webClientTest3() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8099");
        webClient
                .get().uri("/times")
                //配置请求Header：Content-Type: text/event-stream，即SSE；
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                //用log()代替doOnNext(System.out::println)来查看每个元素
                .log()
                //由于/times是一个无限流，这里取前10个，会导致流被取消；
                .take(10)   // 3
                .blockLast();
    }
	
	@Test
    public void webClientTest4() {
		//声明速度为每秒一个MyEvent元素的数据流，不加take的话表示无限个元素的数据流
        Flux<MyEvent> eventFlux = Flux.interval(Duration.ofSeconds(1))
                .map(l -> new MyEvent(System.currentTimeMillis(), "message-" + l)).take(5);
        WebClient webClient = WebClient.create("http://localhost:8099");
        webClient
                .post().uri("/events")
                //声明请求体的数据格式为application/stream+json
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                //body方法设置请求体的数据
                .body(eventFlux, MyEvent.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
	
	
	
}
