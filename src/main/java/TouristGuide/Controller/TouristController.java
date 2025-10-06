package TouristGuide.Controller;

import TouristGuide.Exceptions.TouristAttractionNotFoundException;
import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import TouristGuide.Service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("attractions")
public class TouristController {
    TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping()
    public String getAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAttractions();
        model.addAttribute("attractions", attractions);
        return "attractionList";
    }

    @GetMapping("{name}")
    public String getAttractionsName(Model model, @PathVariable String name) throws TouristAttractionNotFoundException {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        model.addAttribute("attractionByName", attraction);
        return "attractionName";
    }

    @GetMapping("{name}/tags")
    public String getAttractionNameTags(Model model, @PathVariable String name) throws TouristAttractionNotFoundException {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        List<Tags> attractionTags = touristService.getAttractionNameTags(attraction);
        model.addAttribute("attractionByName", attraction);
        model.addAttribute("attractionTags", attractionTags);
        return "tags";
    }

    @GetMapping("{name}/edit")
    public String getAttractionsNameEdit(Model model, @PathVariable String name) throws  TouristAttractionNotFoundException {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        model.addAttribute("attractionByName", attraction);
        model.addAttribute("optionTags", touristService.getOptionTags());
        model.addAttribute("optionCities", touristService.getOptionCities());
        return "updateAttraction";
    }

    @GetMapping("/add")
    public String addAttraction(Model model) {
        TouristAttraction newAttraction = new TouristAttraction();
        model.addAttribute("newAttraction", newAttraction);
        model.addAttribute("optionTags", touristService.getOptionTags());
        model.addAttribute("optionCities", touristService.getOptionCities());
        return "addForm";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction newAttraction) {
        touristService.addAttraction(newAttraction);
        return "redirect:/attractions";
    }

    @PostMapping("/update")
    public String updateAttractions(@ModelAttribute TouristAttraction attractionByName) {
        touristService.updateTouristAttraction(attractionByName);
        return "redirect:/attractions";
    }

    @PostMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable String name) throws TouristAttractionNotFoundException {
        touristService.deleteAttractionByName(name);
        return "redirect:/attractions";
    }
}
