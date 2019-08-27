package com.example.reactive.board;

import com.example.reactive.http.Memo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BoardPostController {

  @Autowired
  private BoardPostRepository repository;

  @PostMapping("/hi")
  public Mono<ServerResponse> list(@RequestBody Memo memo) {
    List<BoardPost> posts = repository.findAll();
    Flux<BoardPost> boardPostFlux = Flux.fromIterable(posts);
    return ServerResponse.ok().body(boardPostFlux, BoardPost.class);
  }

}
