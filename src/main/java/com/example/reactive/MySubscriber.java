package com.example.reactive;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class MySubscriber implements Subscriber<String> {


  private Subscription subscription;

  @Override
  public void onSubscribe(Subscription s) {
    subscription = s;
    subscription.request(1);
  }

  @Override
  public void onNext(String name) {
    log.info("시퀀스 수신 : " + name);
    subscription.request(1);
  }

  @Override
  public void onError(Throwable t) {
    log.info("에러 : " + t.getMessage());
  }

  @Override
  public void onComplete() {
    log.info("모든 시퀀스 수신 완료");
  }
}
