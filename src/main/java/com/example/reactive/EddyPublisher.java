package com.example.reactive;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

@Slf4j
public class EddyPublisher implements Publisher<Integer> {

  private final ExecutorService executor = Executors.newFixedThreadPool(3);

  @Override
  public void subscribe(Subscriber<? super Integer> subscriber) {

    log.info("publisher - subscribe");

    subscriber.onSubscribe(new EddySubscription(subscriber, executor));
  }
}