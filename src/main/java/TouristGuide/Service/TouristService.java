package TouristGuide.Service;

import TouristGuide.Model.Cities;
import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import TouristGuide.Repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<TouristAttraction> getAttractions () {
        return touristRepository.getAttractions();
    }

    public TouristAttraction getAttractionByName (String name) {
        return touristRepository.getAttractionByName(name);
    }

    public List<Tags> getAttractionNameTags (TouristAttraction touristAttraction) {
        return touristRepository.getAttractionNameTags(touristAttraction);
    }

    public TouristAttraction addAttraction (TouristAttraction touristAttraction) {
        touristRepository.addAttraction(touristAttraction);
        return touristAttraction;
    }

    public List<Cities> getOptionCities () {
        return touristRepository.getOptionCities();
    }

    public List<Tags> getOptionTags () {
        return touristRepository.getOptionTags();
    }

    public TouristAttraction updateTouristAttraction (TouristAttraction touristAttraction) {
        return touristRepository.updateTouristAttraction(touristAttraction);
    }

    public void deleteAttractionByName (String name) {
        touristRepository.deleteAttractionByName(name);
    }
}
