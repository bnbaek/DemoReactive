package com.example.reactive;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FluxTests {

  @Test
  public void test_flux_just() {
    List<String> name = new ArrayList<>();
    Flux<String> flux = Flux.just("에디킴", "아이린").log();
    flux.subscribe(name::add);
    assertThat(name.equals(Arrays.asList("에디킴", "아이린")));

  }

  @Test
  public void test_flux_just_consumer() {
    List<String> names = new ArrayList<>();

    Flux<String> flux = Flux.just("에디킴", "아이린").log();
    flux.subscribe(s -> {
      System.out.println("시퀀스 수신 > " + s);
      names.add(s);
    });
    assertThat(names.equals(Arrays.asList("에디킴", "아이린")));
  }

  @Test
  public void test_flux_fromArray() {
    List<String> names = new ArrayList<>();
    Flux<String> flux = Flux.fromArray(new String[]{"에디킴", "아이린", "아이유"});
    flux.subscribe(names::add);
    assertThat(names.equals(Arrays.asList("에디킴", "아이린", "아이유")));
  }

  @Test
  public void test_flux_empty() {

    List<String> names = new ArrayList<>();

    Flux<String> flux = Flux.empty();
    flux.subscribe(names::add);

    assertThat(names.size()).isEqualTo(0);
  }

  @Test
  public void test_flux_subscriber() {

    Flux<String> flux = Flux.just("에디킴", "아이린").log();
    flux.subscribe(new MySubscriber());

    //TODO: 테스트 코드
  }

  @Test
  public void test_flux_lazy() {
    Flux<Integer> flux = Flux.range(1, 9)
        .flatMap(n -> {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return Mono.just(3 * n);
        }).log();

    System.out.println("아직 구독 안한 상황... 데이터 전달을 하지 않는다.");

    flux.subscribe(value -> {
          System.out.println(value);
        }, null,
        () -> {
          System.out.println("데이터 수신 완료");
        }

    );
  }
}
