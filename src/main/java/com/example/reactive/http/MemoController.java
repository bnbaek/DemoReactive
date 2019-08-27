package com.example.reactive.http;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class MemoController {

  private final MemoService memoService;

  public MemoController(MemoService memoService) {
    this.memoService = memoService;
  }

  @PostMapping("/memos")
//  public Mono<MemoResponseDTO> save(@Valid @RequestBody MemoRequestDTO memoRequestDTO) {
  public Mono<Memo> save(@Valid @RequestBody Memo memo) {
    log.info("===== MemoController 시작 =====");
//    return memoService.save(memoRequestDTO).map(MemoResponseDTO::of).log();
    return memoService.save(memo).log();
  }
}
