package com.example.amadeus.app.controller;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is an automated unit tests using Spring-test and JUnit
 *
 * @author TheNhan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-servlet-context.xml"})
public class RestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private RestController restController;

    private final String DATA_FILE = "data.xml";
    private final String DATA_FILE2 = "data2.xml";

    private final String FILE_NOT_FOUND = "file";

    final ClassLoader loader = RestControllerTest.class.getClassLoader();

    public RestControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Setup the web service
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of calculateAverage method, of class PersonController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCalculateAverage() throws Exception {
        this.mockMvc.perform(
                post("/average").content(loader.getResource("").toString().substring("file:/".length()) + DATA_FILE)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[*].range", containsInAnyOrder("0-19", "20-39", "40-59", "60-79")))
                .andExpect(jsonPath("$[*].average", containsInAnyOrder(17.666666, 25.333334, 47.0, 63.0)));
    }

    /**
     * Test of a lack of age range, of class PersonController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCalculateAverage2() throws Exception {
        this.mockMvc.perform(
                post("/average").content(loader.getResource("").toString().substring("file:/".length()) + DATA_FILE2)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[*].range", containsInAnyOrder("0-19", "20-39", "40-59", "60-79")))
                .andExpect(jsonPath("$[*].average", containsInAnyOrder(17.666666, 0.0, 47.0, 63.0)));
    }

    /**
     * Test of reading a file not found
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testFileNotFound() throws Exception {
        this.mockMvc.perform(
                post("/average").content(loader.getResource("") + FILE_NOT_FOUND)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotFound());
    }
}
