package com.maomao2.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Future;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    // 注解指定发生错误时的回调方法。 同步执行
    @HystrixCommand(fallbackMethod = "helloFallback")
    public String helloService() {
        // Get请求调用服务，restTemplate被@LoadBalanced注解标记，Get方法会自动进行负载均衡
        return restTemplate.getForObject("http://SERVICE-AB/index", String.class);
    }

    public String helloFallback() {
        return "Error occurred ！";
    }

    //异步执行
    @HystrixCommand(fallbackMethod = "getUserNameFallback")
    public Future<String> getUsername(final Long id){
        return new AsyncResult<String>(){

            @Override
            public String invoke() {
                int i = 1 /0;
                return "id: "+ id;
            }
        };
    }

    public String getUserNameFallback(){
        return "failed";
    }

    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER, fallbackMethod = "getUserByIdFallBack")
    public Observable<String> getUserById(final Long id){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onNext("tom id:" );
                        int i = 1/ 0;
                        subscriber.onNext(String.valueOf(i));
                        subscriber.onCompleted();
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    public String getUserByIdFallback(final Long id){
        return "failed: "+ id;
    }

    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.LAZY, fallbackMethod = "getUserByIdFallBack")
    public Observable<String> getUserById2(final Long id){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onNext("tom id:" );
                        int i = 1/ 0;
                        subscriber.onNext(String.valueOf(i));
                        subscriber.onCompleted();
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

}
