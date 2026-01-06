package org.pengtao.csdemo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class NewsRequestDTO {

    @Valid
    @NotNull(message = "作者信息不能为空")
    private Author author;

    private List<String> tags;

    @Valid
    @NotNull(message = "内容体不能为空")
    private Body body;

    @Data
    public static class Author {
        @NotBlank(message = "昵称不能为空")
        @Size(min = 2, max = 16, message = "昵称长度需在2-16字之间")
        private String nickname;

        @Size(min = 2, max = 200, message = "头像URL长度需在2-200之间")
        private String avatar;
    }

    @Data
    public static class Body {
        private String content;

        @Valid
        private List<Media> medias;
    }

    @Data
    public static class Media {
        @Pattern(regexp = "IMAGE|VIDEO", message = "媒体类型必须是 IMAGE 或 VIDEO")
        private String type;

        @NotBlank(message = "URL不能为空")
        @Size(min = 2, max = 200, message = "URL长度需在2-200之间")
        private String url;

        @Size(min = 6, max = 64, message = "fieldId长度需在6-64之间")
        private String fieldId;
    }
}