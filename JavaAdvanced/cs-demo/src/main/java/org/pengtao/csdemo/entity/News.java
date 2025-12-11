package org.pengtao.csdemo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Data
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String authorNickname;
    private String authorAvatar;

    @Lob
    private String content;

    private Date createTime;

    @ElementCollection
    @CollectionTable(name = "news_tags", joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "tag")
    private List<String> tags;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private List<NewsMedia> medias;
}
