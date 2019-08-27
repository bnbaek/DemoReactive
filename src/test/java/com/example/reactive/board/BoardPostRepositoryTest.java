package com.example.reactive.board;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
public class BoardPostRepositoryTest {
  @Autowired
  private BoardPostRepository repository;

  @Before
  public void init() {
    BoardPost post = BoardPost.builder()
        .title("title")
        .content("content")
        .build();

    repository.save(post);
  }

  @Test
  public void testFindAll() {
    Iterable<BoardPost> posts = repository.findAll();
    assertThat(posts).isNotEmpty();
  }

  @Test
  public void testFindById() {
    BoardPost postOne = BoardPost.builder()
        .title("one title")
        .content("one content")
        .build();

    repository.save(postOne);

    Optional<BoardPost> findOne = repository.findById(postOne.getId());

    assertThat(findOne.get().getTitle()).isEqualTo(postOne.getTitle());
    assertThat(findOne.get().getContent()).isEqualTo(postOne.getContent());
  }
}