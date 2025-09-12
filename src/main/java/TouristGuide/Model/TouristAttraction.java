package TouristGuide.Model;
import TouristGuide.Model.Tags;
import java.lang.reflect.Array;

public class TouristAttraction {
    private String name;
    private String description;
    private String by;
    private final Tags[] tags;

    public TouristAttraction(String name, String description, String by, Tags[] tags) {
        this.name = name;
        this.description = description;
        this.by = by;
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

    public void setBy(String by) {
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    public Tags[] getTags () {
        return tags;
    }
}
