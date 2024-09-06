package ru.javaops.topjava2.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.repository.BaseRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.TestUtil.userHttpBasic;
import static ru.javaops.topjava2.testdata.DishTestData.DISH_1_ID;
import static ru.javaops.topjava2.testdata.UserTestData.admin;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    public <T> void getById(String url, int id, MatcherFactory.Matcher<T> matcher, T expectedEntity) throws Exception {
        perform(MockMvcRequestBuilders.get(url + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher.contentJson(expectedEntity));
    }

    public <T> void getNotFound(String url, int id, BaseRepository<T> repository) throws Exception {
        perform(MockMvcRequestBuilders.get(url + id))
                .andExpect(status().isNotFound())
                .andDo(print());
        Assertions.assertThrows(NotFoundException.class, () -> repository.getExisted(id));
    }

    public void getUnauth(String url, int id) throws Exception {
        perform(MockMvcRequestBuilders.get(url + DISH_1_ID))
                .andExpect(status().isUnauthorized());
    }

    public <T> void delete(String url, int id, BaseRepository<T> repository) throws Exception {
        perform(MockMvcRequestBuilders.delete(url + id))
                .andExpect(status().isNoContent())
                .andDo(print());
        Assertions.assertThrows(NotFoundException.class, () -> repository.getExisted(id));
    }

    public <T> void deleteNotFound(String url, int id) throws Exception {
        perform(MockMvcRequestBuilders.delete(url + id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
