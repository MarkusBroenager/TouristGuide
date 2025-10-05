package TouristGuide;

import TouristGuide.Controller.TouristController;
import TouristGuide.Model.Cities;
import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import TouristGuide.Repository.TouristRepository;
import TouristGuide.Service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristGuideApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService touristService;

    @Test
    void showAllAttractionsWorksAsExpected() throws Exception {
        List<TouristAttraction> attractions = touristService.getAttractions();

        when(touristService.getAttractions())
                .thenReturn(attractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"));
    }
}
