package TouristGuide.Controller;

import TouristGuide.Model.Cities;
import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import TouristGuide.Service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping()
public class TouristController {
    TouristService touristService;
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("attractions")
    public String getAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAttractions();
        model.addAttribute("attractions", attractions);

        return "attractionList";
    }

    @GetMapping("/attractions/{name}")
    public String getAttractionsName(Model model, @PathVariable String name) {
        TouristAttraction attractionName = touristService.getAttractionByName(name);
        model.addAttribute("attractionByName", attractionName);
        return "attractionName";
    }

    @GetMapping("/attractions/{name}/tags")
    public String getAttractionNameTags(Model model, @PathVariable String name) {
        TouristAttraction attractionName = touristService.getAttractionByName(name);
        List<Tags> attractionTags = touristService.getAttractionNameTags(attractionName);
        model.addAttribute("attractionByName", attractionName);
        model.addAttribute("attractions", attractionTags);
        return "tags";
    }

    @GetMapping("/attractions/{name}/edit")
    public String getAttractionsNameEdit(Model model, @PathVariable String name) {
        TouristAttraction attractionName = touristService.getAttractionByName(name);
        model.addAttribute("attractionByName", attractionName);
        return "updateAttraction";
    }

    @GetMapping("/attractions/add")
    public String addAttraction(String name, String description, Cities city, List<Tags> tags, List<Tags> optionTags, List<Cities> optionCities, Model model) {
        TouristAttraction newAttraction = new TouristAttraction(name, description, city, tags);
        optionTags = touristService.getTags(optionTags);
        optionCities = touristService.getCities(optionCities);
        model.addAttribute("newAttraction", newAttraction);
        model.addAttribute("optionTags", optionTags);
        model.addAttribute("optionCities", optionCities);
        return "addForm";
    }

    @PostMapping("/attractions/save")
    public String saveAttraction(@ModelAttribute TouristAttraction newAttraction) {
        touristService.addAttraction(newAttraction);
        return "redirect:/attractions";
    }

    @PostMapping("/attractions/update")
    public String updateAttractions(@RequestBody TouristAttraction touristAttraction) {
        TouristAttraction updatedAttraction = touristService.updateTouristAttraction(touristAttraction);
        return "redirect:/attractions";
    }

    @PostMapping("/attractions/delete/{name}")
    public ResponseEntity<TouristAttraction> deleteAttraction(@PathVariable String name) {
        TouristAttraction removedTouristAttraction = touristService.deleteAttractionByName(name);
        return new ResponseEntity<> (removedTouristAttraction, HttpStatus.OK);
    }

}
