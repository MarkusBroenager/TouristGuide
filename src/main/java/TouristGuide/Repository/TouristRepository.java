package TouristGuide.Repository;

import TouristGuide.Model.Cities;
import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    List<TouristAttraction> TAList = new ArrayList<>();

    public TouristRepository () {
        TouristAttraction TA1 = new TouristAttraction("Water Park", "Fun with Water", Cities.Glostrup, List.of(Tags.Adult, Tags.Children, Tags.Entertainment, Tags.Active));
        TouristAttraction TA2 = new TouristAttraction("Amusement Park", "Fun with Roller coasters", Cities.Copenhagen, List.of(Tags.Adult, Tags.Children, Tags.Entertainment, Tags.Active));
        TouristAttraction TA3 = new TouristAttraction("Museum", "Enjoy the Culture", Cities.Copenhagen, List.of(Tags.Adult, Tags.Culture));
        TouristAttraction TA4 = new TouristAttraction("Royal Garden", "Enjoy the Beauty",Cities.Copenhagen , List.of(Tags.Adult, Tags.Children, Tags.Culture));

        TAList.add(TA1);
        TAList.add(TA2);
        TAList.add(TA3);
        TAList.add(TA4);
    }

    public List<TouristAttraction> getAttractions () {
        return TAList;
    }

    public List<Tags> getAttractionNameTags(TouristAttraction touristAttraction) {
        List<Tags> attractionTags = touristAttraction.getTags();
        return attractionTags;
    }

    public TouristAttraction getAttractionByName(String name) {
        TouristAttraction touristAttraction = null;
        for (int i = 0; i < TAList.size(); i++) {
            if (name.equals(TAList.get(i).getName())) {
                touristAttraction = TAList.get(i);
            }
        }
        return touristAttraction;
    }

    public void addAttraction (TouristAttraction touristAttraction) {
        TAList.add(touristAttraction);
    }

    public List<Cities> getOptionCities () {
        return List.of(Cities.values());
    }

    public List<Tags> getOptionTags () {
        return List.of(Tags.values());
    }

    public  void updateTouristAttraction (TouristAttraction touristAttraction) {
        for (TouristAttraction attraction: TAList) {
            if (attraction.getName().equals(touristAttraction.getName())) {
                attraction.setDescription(touristAttraction.getDescription());
                attraction.setCity(touristAttraction.getCity());
                attraction.setTags(touristAttraction.getTags());
            }
        }
    }

    public void deleteAttractionByName (String name) {
        for (TouristAttraction attraction: TAList) {
            if (name.equals(attraction.getName())) {
                TAList.remove(attraction);
            }
        }
    }

}
