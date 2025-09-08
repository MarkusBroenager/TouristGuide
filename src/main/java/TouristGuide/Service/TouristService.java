package TouristGuide.Service;

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

    public TouristAttraction addAttraction (TouristAttraction touristAttraction) {
        touristRepository.addAttraction(touristAttraction);
        return touristAttraction;
    }

    public TouristAttraction updateTouristAttraction (TouristAttraction touristAttraction) {
        return touristRepository.updateTouristAttraction(touristAttraction);
    }

    public TouristAttraction deleteAttractionByName (String name) {
        return touristRepository.deleteAttractionByName(name);
    }
}
