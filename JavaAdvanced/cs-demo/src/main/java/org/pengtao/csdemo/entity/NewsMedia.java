package org.pengtao.csdemo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "news_medias")
public class NewsMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String originalUrl;
    private String localPath;
    private String fieldId;
}
