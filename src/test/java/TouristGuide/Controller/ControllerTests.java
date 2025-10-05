package TouristGuide.Controller;

import TouristGuide.Exceptions.TouristAttractionNotFoundException;
import TouristGuide.Model.Cities;
import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import TouristGuide.Service.TouristService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
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
    void showAllAttractionsWorksAsExpectedOnEmpty() throws Exception {
        List<TouristAttraction> attractions = new ArrayList<TouristAttraction>();
        when(touristService.getAttractions()).thenReturn(attractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("attractionList"));

        verify(touristService).getAttractions();
    }

    @Test
    void showAllAttractionsWorksAsExpectedOnNonEmpty() throws Exception {
        List<TouristAttraction> attractions = new ArrayList<>(List.of(
                new TouristAttraction("Tivoli", "A fun amusement park in the heart of Copenhagen.", Cities.Copenhagen, List.of(Tags.Adult, Tags.Entertainment, Tags.Children)),
                new TouristAttraction("Dronning Louises Bro (Queen Louise's Bridge)", "Designed by Vilhelm Dahlerup, built between 1885 and 1887, it remains today and connects the business hub of Copenhagen K to the vibrant Nørrebro.", Cities.Copenhagen, List.of(Tags.Culture))
        ));
        when(touristService.getAttractions()).thenReturn(attractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("attractions", hasSize(2)))
                .andExpect(model().attribute("attractions", hasItem(hasProperty("name", is("Tivoli")))))
                .andExpect(model().attribute("attractions", hasItem(hasProperty("name", is("Dronning Louises Bro (Queen Louise's Bridge)")))))
                .andExpect(view().name("attractionList"));

        verify(touristService).getAttractions();
    }

    @Test
    void getAttractionByNameWorksOnExistingName() throws Exception {
        TouristAttraction attraction = new TouristAttraction("Museum", "Enjoy the Culture", Cities.Copenhagen, List.of(Tags.Adult, Tags.Culture));

        when(touristService.getAttractionByName(attraction.getName())).thenReturn(attraction);

        mockMvc.perform(get("/attractions/Museum"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("attractionByName", attraction))
                .andExpect(view().name("attractionName"));

        verify(touristService).getAttractionByName("Museum");
    }

    @Test
    void getAttractionByNameFailsOnNonExistentName() throws Exception {
        when(touristService.getAttractionByName("abc"))
                .thenThrow(new TouristAttractionNotFoundException("Attraction \"" + "abc" + "\" not found."));

        mockMvc.perform(get("/attractions/abc"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("error"))
                .andExpect(model().attribute("message", "Attraction \"abc\" not found."));

        verify(touristService).getAttractionByName("abc");
    }

    @Test
    void getTagsWorksOnExistingAttraction() throws Exception {
        TouristAttraction attraction = new TouristAttraction("Museum", "Enjoy the Culture", Cities.Copenhagen, List.of(Tags.Adult, Tags.Culture));

        when(touristService.getAttractionByName("Museum"))
                .thenReturn(attraction);

        when(touristService.getAttractionNameTags(attraction))
                .thenReturn(attraction.getTags());

        mockMvc.perform(get("/attractions/Museum/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("attractionByName", attraction))
                .andExpect(model().attribute("attractionTags", attraction.getTags()))
                .andExpect(view().name("tags"));

        verify(touristService).getAttractionByName("Museum");
        verify(touristService).getAttractionNameTags(attraction);
    }

    @Test
    void getTagsFailsOnNonExistentAttraction() throws Exception {
        when(touristService.getAttractionByName("abc"))
                .thenThrow(new TouristAttractionNotFoundException("Attraction \"" + "abc" + "\" not found."));

        mockMvc.perform(get("/attractions/abc/tags"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"));

        verify(touristService).getAttractionByName("abc");
    }

    @Test
    void editAttractionWorksOnExistentAttraction() throws Exception {
        TouristAttraction attraction = new TouristAttraction("Museum", "Enjoy the Culture", Cities.Copenhagen, List.of(Tags.Adult, Tags.Culture));

        when(touristService.getAttractionByName("Museum")).thenReturn(attraction);
        when(touristService.getOptionTags()).thenReturn(List.of(Tags.values()));
        when(touristService.getOptionCities()).thenReturn(List.of(Cities.values()));

        mockMvc.perform(get("/attractions/Museum/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("attractionByName", attraction))
                .andExpect(model().attribute("optionTags", List.of(Tags.values())))
                .andExpect(model().attribute("optionCities", List.of(Cities.values())))
                .andExpect(view().name("updateAttraction"));

        verify(touristService).getAttractionByName("Museum");
        verify(touristService).getOptionTags();
        verify(touristService).getOptionCities();
    }

    @Test
    void editAttractionFailsOnNonExistentAttraction() throws Exception {
        when(touristService.getAttractionByName("abc"))
                .thenThrow(new TouristAttractionNotFoundException("Attraction \"" + "abc" + "\" not found."));

        mockMvc.perform(get("/attractions/abc/edit"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"));

        verify(touristService).getAttractionByName("abc");
    }

    @Test
    void addAttractionWorks() throws Exception {
        when(touristService.getOptionTags()).thenReturn(List.of(Tags.values()));
        when(touristService.getOptionCities()).thenReturn(List.of(Cities.values()));

        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("newAttraction")) // Check that the newAttraction attribute exists
                .andExpect(model().attribute("optionTags", List.of(Tags.values())))
                .andExpect(model().attribute("optionCities", List.of(Cities.values())))
                .andExpect(view().name("addForm"));

        verify(touristService).getOptionTags();
        verify(touristService).getOptionCities();
    }

    @Test
    void saveAttractionSavesAndRedirects() throws Exception {
        TouristAttraction attraction = new TouristAttraction("Museum", "Enjoy the Culture", Cities.Copenhagen, List.of(Tags.Adult, Tags.Culture));
        when(touristService.addAttraction(attraction)).thenReturn(attraction);

        mockMvc.perform(post("/attractions/save")
                .param("name", attraction.getName())
                .param("description", attraction.getDescription())
                .param("city", attraction.getCity().toString())
                .param("tags", Tags.Adult.toString(), Tags.Culture.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService).addAttraction(attraction);
    }

    @Test
    void updateAttractionUpdatesAndRedirects() throws Exception {
        TouristAttraction attraction = new TouristAttraction("Museum", "Enjoy the Culture", Cities.Copenhagen, List.of(Tags.Adult, Tags.Culture));
        when(touristService.getAttractionByName("Museum")).thenReturn(attraction);

        mockMvc.perform(post("/attractions/update")
                .param("name", attraction.getName())
                .param("description", "NewDescription")
                .param("city", Cities.Glostrup.toString())
                .param("tags", Tags.Adult.toString(), Tags.Active.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService).updateTouristAttraction(argThat(uattraction ->
                "Museum".equals(uattraction.getName()) &&
                "NewDescription".equals(uattraction.getDescription()) &&
                Cities.Glostrup.equals(uattraction.getCity()) &&
                uattraction.getTags().containsAll(List.of(Tags.Adult, Tags.Active))));
    }

    @Test
    void deleteAttractionDeletesAndRedirects() throws Exception {
        mockMvc.perform(post("/attractions/Museum/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService).deleteAttractionByName("Museum");
    }

    @Test
    void deleteAttractionThrowsOnNonExistent() throws Exception {
        doThrow(new TouristAttractionNotFoundException("Attraction \"" + "abc" + "\" not found."))
                .when(touristService).deleteAttractionByName("abc");

        mockMvc.perform(post("/attractions/abc/delete"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"));
    }
}
