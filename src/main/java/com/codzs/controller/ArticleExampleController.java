package com.codzs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleExampleController {
    private static Logger LOGGER = LoggerFactory.getLogger(ArticleExampleController.class);

    @GetMapping("/articles")
    public String[] getArticles() {
        LOGGER.info("Returning articles");

        return new String[] {
                "Article 1",
                "Article 2",
                "Article 3"
        };
    }
}
