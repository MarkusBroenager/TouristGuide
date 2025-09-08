package TouristGuide.Controller;

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
    public ResponseEntity<TouristAttraction> getAttractionsName(Model model, @PathVariable String name) {
        TouristAttraction attractionName = touristService.getAttractionByName(name);
        model.addAttribute("attractionByName", attractionName);
        return new ResponseEntity<>(attractionName, HttpStatus.OK);
    }

    @GetMapping("/attractions/{name}/tags")
    public ResponseEntity<TouristAttraction> getAttractionsNameTags(Model model, @PathVariable String name) {
        TouristAttraction attractionName = touristService.getAttractionByName(name);
        model.addAttribute("attractionByName", attractionName);
        return new ResponseEntity<>(attractionName, HttpStatus.OK);
    }

    @GetMapping("/attractions/{name}/edit")
    public ResponseEntity<TouristAttraction> getAttractionsNameEdit(Model model, @PathVariable String name) {
        TouristAttraction attractionName = touristService.getAttractionByName(name);
        model.addAttribute("attractionByName", attractionName);
        return new ResponseEntity<>(attractionName, HttpStatus.OK);
    }

    @GetMapping("/attractions/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction touristAttraction) {
        TouristAttraction newTouristAttraction = touristService.addAttraction(touristAttraction);
        return new ResponseEntity<> (newTouristAttraction, HttpStatus.CREATED);
    }

    @PostMapping("/attractions/save")
    public ResponseEntity<TouristAttraction> saveAttraction(@PathVariable String name) {
        TouristAttraction removedTouristAttraction = touristService.deleteAttractionByName(name);
        return new ResponseEntity<> (removedTouristAttraction, HttpStatus.OK);
    }

    @PostMapping("/attractions/update")
    public ResponseEntity<TouristAttraction> updateAttractions(@RequestBody TouristAttraction touristAttraction) {
        TouristAttraction updatedAttraction = touristService.updateTouristAttraction(touristAttraction);
        return new ResponseEntity<> (updatedAttraction, HttpStatus.OK);
    }

    @PostMapping("/attractions/delete/{name}")
    public ResponseEntity<TouristAttraction> deleteAttraction(@PathVariable String name) {
        TouristAttraction removedTouristAttraction = touristService.deleteAttractionByName(name);
        return new ResponseEntity<> (removedTouristAttraction, HttpStatus.OK);
    }

}
