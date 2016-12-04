package no.gnome;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WorldcitiesApplicationTests {

    @Autowired
    private MockMvc mvc;
    
    @Test
    public void getUnknowPath() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/greetingtest").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void getEmptyCity() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/city/a").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", org.hamcrest.Matchers.hasSize(0)));
    }
    
    @Test
    public void getOneCity() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/city/Sandane").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", org.hamcrest.Matchers.hasSize(1)));
    }
    
    @Test
    public void getCitiesNearBreimWithDefaultRadius() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cities/61.7428745/6.3968833").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", org.hamcrest.Matchers.hasSize(29)));
    }

    @Test
    public void getCitiesNearBreimWithMalformedLatitudeAndLongitude() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cities/a/b").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", org.hamcrest.Matchers.hasSize(29)));
    }

    @Test
    public void getCitiesNearBreimWithMalformedLatitudeAndLongitudeAnd10kmRadius() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cities/a/b/10").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", org.hamcrest.Matchers.hasSize(7)));
    }

    @Test
    public void getCitiesNearBreimWith10kmRadius() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cities/61.7428745/6.3968833/10").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", org.hamcrest.Matchers.hasSize(7)));
    }

    @Test
    public void getCitiesNearBerlinWithDefaultRadius() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cities/52.516666/13.4").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", org.hamcrest.Matchers.hasSize(254)));
    }

    @Test
    public void getCitiesNearBerlinWith25kmRadius() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cities/52.516666/13.4/25").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", org.hamcrest.Matchers.hasSize(377)));
    }

}
