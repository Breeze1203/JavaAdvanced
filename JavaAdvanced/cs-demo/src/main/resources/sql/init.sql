-- 主表：新闻
CREATE TABLE `news` (
                        `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                        `author_nickname` VARCHAR(16) NOT NULL COMMENT '作者昵称',
                        `author_avatar` VARCHAR(200) COMMENT '作者头像',
                        `content` TEXT COMMENT '文本内容',
                        `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

-- 附表：标签
CREATE TABLE `news_tags` (
                             `news_id` BIGINT NOT NULL,
                             `tag` VARCHAR(50) NOT NULL,
                             INDEX `idx_news_id` (`news_id`),
                             INDEX `idx_tag` (`tag`)
);

-- 附表：媒体资源
CREATE TABLE `news_medias` (
                               `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                               `news_id` BIGINT NOT NULL,
                               `type` VARCHAR(10) NOT NULL COMMENT 'IMAGE/VIDEO',
                               `original_url` VARCHAR(200) NOT NULL,
                               `local_path` VARCHAR(255) COMMENT '本地存储路径',
                               `field_id` VARCHAR(64) COMMENT '视频ID',
                               INDEX `idx_news_id` (`news_id`)
);