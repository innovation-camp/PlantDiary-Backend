package com.sparta.plantdiary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE post SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Post extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String thumbnail;

    @Column(nullable = true)
    private Long countComments = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    public Post(String title, String content, String thumbnail, Member writer) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.writer = writer;
    };
}
