package com.example.reactive.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MemoRepository {

  public Memo save(Memo memo) {
    log.info("===== MemoRepository 시작 =====");
    // ...blocking persistence APIs (JPA, JDBC) or networking APIs to use return memo; }
    return null;
  }
}
