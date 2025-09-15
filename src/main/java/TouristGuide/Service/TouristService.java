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

    public Tags[] getAttractionNameTags (TouristAttraction touristAttraction) {
        return touristRepository.getAttractionNameTags(touristAttraction);
    }

    public TouristAttraction addAttraction (TouristAttraction touristAttraction) {
        touristRepository.addAttraction(touristAttraction);
        return touristAttraction;
    }

    public List<Cities> getCities (List<Cities> cities) {
        return touristRepository.getCities();
    }

    public List<Tags> getTags (List<Tags> tags) {
        return touristRepository.getTags();
    }

    public TouristAttraction updateTouristAttraction (TouristAttraction touristAttraction) {
        return touristRepository.updateTouristAttraction(touristAttraction);
    }

    public TouristAttraction deleteAttractionByName (String name) {
        return touristRepository.deleteAttractionByName(name);
    }
}
