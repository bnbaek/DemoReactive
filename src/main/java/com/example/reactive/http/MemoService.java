package com.example.reactive.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MemoService {

  private final MemoRepository memoRepository;

  public MemoService(MemoRepository memoRepository) {
    this.memoRepository = memoRepository;
  }


  public Mono<Memo> save(Memo memo) {
    log.info("===== MemoService 시작 =====");
//    return Mono.just(memoRepository.save(Memo.of(memoRequestDTO))).log();
    return Mono.just(memoRepository.save(memo)).log();
  }
}
