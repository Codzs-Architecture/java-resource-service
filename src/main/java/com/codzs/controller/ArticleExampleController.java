package com.codzs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleExampleController {
    @GetMapping("/articles")
    public String[] getArticles() {
        return new String[] {
                "Article 1",
                "Article 2",
                "Article 3"
        };
    }
}
