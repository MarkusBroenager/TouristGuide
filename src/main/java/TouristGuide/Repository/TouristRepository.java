package TouristGuide.Repository;

import TouristGuide.Exceptions.TouristAttractionNotFoundException;
import TouristGuide.Model.Cities;
import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TouristRepository {
    @Value("${spring.datasource.url}")
    private String dbURL;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    protected final JdbcTemplate jdbcTemplate;

    private final RowMapper<TouristAttraction> rowMapper = (rs, rowNum) -> {
        TouristAttraction attraction = new TouristAttraction();
        attraction.setName(rs.getString("Name"));
        attraction.setDescription(rs.getString("Description"));
        attraction.setCity(Cities.valueOf(rs.getString("City")));
        // Make list, then for tagString in tags { toTagType(tagString); }
        attraction.setTags(Arrays.stream(rs.getString("Tags").split(", ")).map(Tags::valueOf).collect(Collectors.toList()));
        return attraction;
    };

    public TouristRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TouristAttraction> getAttractions () {
        return jdbcTemplate.query("SELECT * FROM TouristAttractionView", rowMapper);
    }

    public List<Tags> getAttractionNameTags(TouristAttraction touristAttraction) {
        return touristAttraction.getTags();
    }

    public TouristAttraction getAttractionByName(String name) throws TouristAttractionNotFoundException {
        for (TouristAttraction attraction : getAttractions()) {
            if (name.equals(attraction.getName())) {
                return attraction;
            }
        }

        return null;
    }

    public void addAttraction (TouristAttraction touristAttraction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO TouristAttractions (CityName, Name, Description) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, touristAttraction.getCity().toString());
            ps.setString(2, touristAttraction.getName());
            ps.setString(3, touristAttraction.getDescription());
            return ps;}, keyHolder);
        int idOfInsertedAttraction;

        try {
            idOfInsertedAttraction = keyHolder.getKey().intValue();
        } catch (NullPointerException npe) {
            return; // Skip the rest, nothing was inserted.
        } // id should be correct if we make it to this point.

        jdbcTemplate.update("INSERT IGNORE INTO Cities (Name) VALUES (?)", touristAttraction.getCity().toString());

        for (Tags tag : touristAttraction.getTags()) {
            jdbcTemplate.update("INSERT IGNORE INTO Tags (Name) VALUES (?)", tag.toString());
            jdbcTemplate.update("INSERT IGNORE INTO AttractionTags (TagName, AttractionID) VALUES (?, ?)", tag.toString(), idOfInsertedAttraction);
        }
    }

    public List<Cities> getOptionCities () {
        return List.of(Cities.values());
    }

    public List<Tags> getOptionTags () {
        return List.of(Tags.values());
    }

    public void updateTouristAttraction (TouristAttraction touristAttraction) throws TouristAttractionNotFoundException {
        int touristAttractionID;

        try {
            touristAttractionID = jdbcTemplate.queryForObject("SELECT ID FROM TouristAttractions WHERE Name = ?", Integer.class, touristAttraction.getName());
        } catch (NullPointerException npe) {
            throw new TouristAttractionNotFoundException("Attraction \"" + touristAttraction.getName() + "\" not found.");
        }


        jdbcTemplate.update("UPDATE TouristAttractions SET CityName = ?, Name = ?, Description = ? WHERE ID = ?", touristAttraction.getCity().toString(), touristAttraction.getName(), touristAttraction.getDescription(), touristAttractionID);

        jdbcTemplate.update("DELETE FROM AttractionTags WHERE AttractionID = ?", touristAttractionID);
        // Assuming you can't add new tags through the form.
        for (Tags tag : touristAttraction.getTags()) { // Update the tags
            jdbcTemplate.update("INSERT INTO AttractionTags (TagName, AttractionID) VALUES (?, ?)", tag.toString(), touristAttractionID);
        }
    }

    public void deleteAttractionByName (String name) {
        int deletedKey;

        try {
            deletedKey = jdbcTemplate.queryForObject("SELECT ID FROM TouristAttractions WHERE Name = ?", Integer.class, name);
        } catch (NullPointerException npe) {
            throw new TouristAttractionNotFoundException("Attraction \"" + name + "\" not found.");
        }

        jdbcTemplate.update("DELETE FROM TouristAttractions WHERE ID = ?", deletedKey);
        // AttractionTags set to CASCADE on delete, so no need to delete explicitly.
    }
}
