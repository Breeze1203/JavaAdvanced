package org.pengtao.csdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.pengtao.csdemo.dto.NewsRequestDTO;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CsDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateNews_Success() throws Exception {
        NewsRequestDTO request = new NewsRequestDTO();

        NewsRequestDTO.Author author = new NewsRequestDTO.Author();
        author.setNickname("张三");
        author.setAvatar("http://test.com/avatar.jpg");
        request.setAuthor(author);

        request.setTags(Collections.singletonList("头条"));

        NewsRequestDTO.Body body = new NewsRequestDTO.Body();
        body.setContent("这是一个测试内容");
        request.setBody(body);
        // 执行 POST 请求
        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
        .andDo(print());
    }

    @Test
    void testCreateNews_Fail_Validation() throws Exception {
        NewsRequestDTO request = new NewsRequestDTO();
        NewsRequestDTO.Author author = new NewsRequestDTO.Author();
        request.setAuthor(author);
        request.setBody(new NewsRequestDTO.Body());
        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
        .andDo(print());
    }

    @Test
    void testGetNewsList() throws Exception {
        mockMvc.perform(get("/news")
                        .param("page", "0")
                        .param("size", "10")
                        .param("tags", "头条"))
                .andExpect(status().isOk())
                .andDo(print());;

    }

}
