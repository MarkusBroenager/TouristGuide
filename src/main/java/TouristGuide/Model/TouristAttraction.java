package TouristGuide.Model;
import TouristGuide.Model.Tags;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {
    private String name;
    private String description;
    private Cities city;
    private List<Tags> tags;

    public TouristAttraction(String name, String description, Cities city, List<Tags> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public List<Tags> getTags () {
        return tags;
    }

    public void setTags (List<Tags> tags) {
        this.tags = tags;
    }


}
