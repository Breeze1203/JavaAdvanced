package org.pengtao.csdemo.controller;

import jakarta.validation.Valid;
import org.pengtao.csdemo.dto.NewsRequestDTO;
import org.pengtao.csdemo.entity.News;
import org.pengtao.csdemo.service.impl.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/news")
@Validated
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    public String createNews(@Valid @RequestBody NewsRequestDTO request) {
        newsService.createNews(request);
        return "SUCCESS";
    }


    @GetMapping
    public Page<News> getNewsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> tags) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createTime");

        return newsService.getNewsList(tags, pageable);
    }
}
