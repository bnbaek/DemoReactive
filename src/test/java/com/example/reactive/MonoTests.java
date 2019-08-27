package com.example.reactive;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonoTests {

  @Test
  public void test_mono_just() {
    List<Signal<Integer>> signals = new ArrayList<>(4);

    final Integer[] result = new Integer[1];

    Mono<Integer> mono = Mono.just(1).log()
        .doOnEach(integerSignal -> {
          signals.add(integerSignal);
          System.out.println("Signal... : " + integerSignal);
        });

    mono.subscribe(integer -> result[0] = integer);

    assertThat(signals.size()).isEqualTo(2);
    assertThat(signals.get(0).getType().name()).isEqualTo("ON_NEXT");
    assertThat(signals.get(1).getType().name()).isEqualTo("ON_COMPLETE");
    assertThat(result[0].intValue()).isEqualTo(1);

  }

  @Test
  public void test_mono_empty() {

    List<Signal<Integer>> signals = new ArrayList<>(4);
    Mono<Integer> mono = Mono.<Integer>empty()
        .doOnEach(signals::add);

    mono.subscribe();

    assertThat(signals.size()).isEqualTo(1);
    assertThat(signals.get(0).isOnComplete());
  }

  @Test
  public void test_mono_null() {

    Mono<String> result = Mono.empty();
    assertThat(result.block()).isEqualTo(null);


        /*
        String expected = null;
        Mono<String> result = Mono.empty();
        Assert.assertEquals(expected, result.block());
        */

  }


  @Test
  public void test_flux_subscribe_complete() {

    List<String> names = new ArrayList<>();

    List<Signal<String>> signals = new ArrayList<>(10);

    Flux<String> flux = Flux.just("에디킴", "아이린", "아이유", "수지")
        .log()
        .doOnEach(signals::add);

    flux.subscribe(names::add,
        error -> {
        },
        () -> {
          assertEquals(names, Arrays.asList("에디킴", "아이린", "아이유", "수지"));
          assertEquals(signals.size(), 5);
          assertFalse(signals.get(3).isOnComplete());
          assertTrue(signals.get(3).isOnNext());
          assertTrue(signals.get(4).isOnComplete());
        });

  }

  @Test
  public void test_flux_custom_subscriber() {

    List<Integer> integerList = new ArrayList<>();

    Flux.just(1, 2, 3, 4)
        .log()
        .subscribe(new Subscriber<Integer>() {
          @Override
          public void onSubscribe(Subscription s) {
            s.request(Long.MAX_VALUE);
          }

          @Override
          public void onNext(Integer integer) {
            integerList.add(integer);
          }

          @Override
          public void onError(Throwable t) {

          }

          @Override
          public void onComplete() {
            //TODO: request(1) 인 경우, Complete 가 되지 않은 상황에서 테스트가 통과하는것은 잘못된 테스트인듯.. 검토해보자.
            //TODO: 결국 Reactor 에서 제공하는 StepVerifier 와 같은 Test 코드를 사용하는게 좋을 듯
            assertEquals(integerList.size(), 4);
          }
        });

  }

  @Test
  public void test_flux_custom_baseSubscriber() throws InterruptedException {

    CountDownLatch latch = new CountDownLatch(1);

    Flux<String> flux = Flux.just("에디킴", "아이린", "아이유", "수지");
    List names = new ArrayList();

    flux.subscribe(new BaseSubscriber<String>() {

      @Override
      protected void hookOnSubscribe(Subscription subscription) {
        request(1);

        //super.hookOnSubscribe(subscription);
        //subscription.request(Long.MAX_VALUE)
      }

      @Override
      protected void hookOnNext(String value) {
        names.add(value);
        System.out.println(value);
        super.hookOnNext(value);
        request(1);
      }

      @Override
      protected void hookOnComplete() {
        Assert.assertEquals(names.size(),4);

        Assert.assertEquals(names.get(0),"에디킴");
        Assert.assertEquals(names.get(3),"수지");
        super.hookOnComplete();
      }
    });

    latch.await(1000, TimeUnit.MILLISECONDS);
  }


}