package eu.aheads.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private final Logger logger = LogManager.getLogger(JobControllerTest.class);

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetJobs() throws Exception {
        this.mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk()).andDo((MvcResult mvcResult) -> {
            String responseTxt = mvcResult.getResponse().getContentAsString();
            logger.debug("server response: {}", responseTxt);
            assertThat(responseTxt, equalTo("[\"beneficialJob\",\"registerJob\"]"));
        });
    }

    @Test
    public void testGetJobInfo() throws Exception {
        this.mockMvc.perform(get("/api/jobs/registerJob"))
                .andExpect(status().isOk()).andDo((MvcResult mvcResult) -> {
            String responseTxt = mvcResult.getResponse().getContentAsString();
            logger.debug("server response: {}", responseTxt);
        });
    }
}
