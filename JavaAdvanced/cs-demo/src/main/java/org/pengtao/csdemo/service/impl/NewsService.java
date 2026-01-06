package org.pengtao.csdemo.service.impl;

import org.pengtao.csdemo.dto.NewsRequestDTO;
import org.pengtao.csdemo.entity.News;
import org.pengtao.csdemo.entity.NewsMedia;
import org.pengtao.csdemo.service.NewsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Value("${file.upload-dir:./downloads/}")
    private String downloadDir;

    @Transactional
    public void createNews(NewsRequestDTO request) {
        // 1. 逻辑校验：Content 和 Medias 必须二选一
        boolean hasContent = StringUtils.hasText(request.getBody().getContent());
        boolean hasMedias = request.getBody().getMedias() != null && !request.getBody().getMedias().isEmpty();

        if (!hasContent && !hasMedias) {
            throw new IllegalArgumentException("文本内容(content)和媒体列表(medias)必须至少存在一项");
        }

        // 2. 转换实体
        News news = new News();
        news.setAuthorNickname(request.getAuthor().getNickname());
        news.setAuthorAvatar(request.getAuthor().getAvatar());
        news.setContent(request.getBody().getContent());
        news.setTags(request.getTags());
        news.setCreateTime(new Date());

        // 3. 处理媒体资源 (逻辑校验 + 下载)
        if (hasMedias) {
            List<NewsMedia> mediaEntities = new ArrayList<>();
            for (NewsRequestDTO.Media mediaDto : request.getBody().getMedias()) {
                // 校验：如果是 VIDEO，fieldId 必填
                if ("VIDEO".equals(mediaDto.getType()) && !StringUtils.hasText(mediaDto.getFieldId())) {
                    throw new IllegalArgumentException("当类型为 VIDEO 时，fieldId 必填");
                }

                NewsMedia mediaEntity = new NewsMedia();
                mediaEntity.setType(mediaDto.getType());
                mediaEntity.setOriginalUrl(mediaDto.getUrl());
                mediaEntity.setFieldId(mediaDto.getFieldId());
                String localPath = downloadFile(mediaDto.getUrl());
                mediaEntity.setLocalPath(localPath);

                mediaEntities.add(mediaEntity);
            }
            news.setMedias(mediaEntities);
        }

        newsRepository.save(news);
    }

    /**
     * 简单的文件下载实现
     */
    private String downloadFile(String urlStr) {
        try {
            URL url = new URL(urlStr);
            String fileName = UUID.randomUUID().toString() + "_" + urlStr.substring(urlStr.lastIndexOf("/") + 1);
            // 处理文件名过长或非法字符，这里简化处理
            if(fileName.length() > 50) fileName = UUID.randomUUID().toString() + ".dat";

            Path targetPath = Paths.get(downloadDir + fileName);
            Files.createDirectories(targetPath.getParent());

            try (InputStream in = url.openStream()) {
                Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
            return targetPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "DOWNLOAD_FAILED";
        }
    }

    // 获取列表
    public Page<News> getNewsList(List<String> tags, Pageable pageable) {
        if (tags != null && !tags.isEmpty()) {
            return newsRepository.findDistinctByTagsIn(tags, pageable);
        } else {
            return newsRepository.findAll(pageable);
        }
    }
}
