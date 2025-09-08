package TouristGuide.Repository;

import TouristGuide.Model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    List<TouristAttraction> TAList = new ArrayList<TouristAttraction>();

    public TouristRepository () {
        TouristAttraction TA1 = new TouristAttraction("Water Park", "Fun with Water");
        TouristAttraction TA2 = new TouristAttraction("Amusement Park", "Fun with Roller coasters");
        TouristAttraction TA3 = new TouristAttraction("Museum", "Enjoy the Culture");
        TouristAttraction TA4 = new TouristAttraction("Royal Garden", "Enjoy the Beauty");

        TAList.add(TA1);
        TAList.add(TA2);
        TAList.add(TA3);
        TAList.add(TA4);
    }

    public List<TouristAttraction> getAttractions () {
        return TAList;
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

    public TouristAttraction addAttraction (TouristAttraction touristAttraction) {
        TAList.add(touristAttraction);
        return touristAttraction;
    }

    public TouristAttraction updateTouristAttraction (TouristAttraction touristAttraction) {
        for (TouristAttraction a: TAList) {
            if (a.getName().equals(touristAttraction.getName())) {
                a.setDescription(touristAttraction.getDescription());
            }
        }
        return touristAttraction;
    }

    public TouristAttraction deleteAttractionByName (String name) {
        TouristAttraction touristAttraction = null;
        for (int i = 0; i < TAList.size(); i++) {
            if (name.equals(TAList.get(i).getName())) {
                touristAttraction = TAList.get(i);
                TAList.remove(touristAttraction);
            }
        }
        return touristAttraction;
    }

}
