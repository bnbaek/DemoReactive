package com.example.reactive.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "board_post")
@Getter
@Setter
@Builder
public class BoardPost {
  @Column(name = "id", nullable = false)
  @Id
  @GeneratedValue(generator = "board-post-seq-gen", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "board-post-seq-gen", sequenceName = "board_post_id_seq")
  private long id;

  @Column(name = "title", length = 200, nullable = false)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "created_date", nullable = false)
  @Builder.Default
  private Date createdAt = new Date();

  @Column(name = "updated_date", nullable = false)
  @Builder.Default
  private Date updatedAt = new Date();

  @Column(name = "author_id", nullable = false)
  private int authorId;

  @Column(name = "visibleYn")
  @Builder.Default
  private Boolean visibleYn = true;
}
