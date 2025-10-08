package TouristGuide.Repository;

import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
//Hele den her klasse er med meget hjælp fra ChatGPT da test klagede over INSERT IGNORE INTO som bad SQLgrammar.
//Her er det ændret til MERGE, uden at pille ved den overall kode, da det skabte problemer for selve programmet
@Repository
@Profile("test")
@Primary
public class TouristRepositoryH2 extends TouristRepository {

    public TouristRepositoryH2(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void addAttraction(TouristAttraction ta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "MERGE INTO TouristAttractions (CityName, Name, Description) KEY(Name) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, ta.getCity().toString());
            ps.setString(2, ta.getName());
            ps.setString(3, ta.getDescription());
            return ps;
        }, keyHolder);

        int id = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : 0;

        jdbcTemplate.update("MERGE INTO Cities (Name) KEY(Name) VALUES (?)", ta.getCity().toString());

        for (Tags tag : ta.getTags()) {
            jdbcTemplate.update("MERGE INTO Tags (Name) KEY(Name) VALUES (?)", tag.toString());
            jdbcTemplate.update("MERGE INTO AttractionTags (AttractionID, TagName) KEY(AttractionID,TagName) VALUES (?, ?)",
                    id, tag.toString());
        }
    }
}
