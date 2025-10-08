package TouristGuide;

import TouristGuide.Exceptions.TouristAttractionNotFoundException;
import TouristGuide.Model.Cities;
import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import TouristGuide.Repository.TouristRepository;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = { "classpath:schemah2.sql", "classpath:datah2.sql"}, executionPhase = BEFORE_TEST_METHOD)

class PersonRepositoryTest {

    @Autowired
    private TouristRepository repo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void readAll() {
        List<TouristAttraction> all = repo.getAttractions();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(4);

        List<String> names = new ArrayList<>();
        for (TouristAttraction ta : all) {
            names.add(ta.getName());
        }

        assertThat(names).containsExactlyInAnyOrder(
                "Water Park",
                "Amusement Park",
                "Museum",
                "Royal Garden"
        );
    }


    @Test
    void insertAndReadBack() {
        TouristAttraction tivoli = new TouristAttraction(
                "Tivoli",
                "Der er mange sjove rutsjebaner",
                Cities.Copenhagen,
                List.of(Tags.Active, Tags.Entertainment)
        );
        repo.addAttraction(tivoli);
        TouristAttraction readBack = repo.getAttractionByName("Tivoli");

        assertThat(readBack).isNotNull();
        assertThat(readBack.getName()).isEqualTo("Tivoli");
        assertThat(readBack.getDescription()).isEqualTo("Der er mange sjove rutsjebaner");
        assertThat(readBack.getCity()).isEqualTo(Cities.Copenhagen);
        assertThat(readBack.getTags()).containsExactlyInAnyOrder(Tags.Active, Tags.Entertainment);
    }

    @Test
    void insertExistingAttractionDoesNotInsertDuplicate() {
        List<TouristAttraction> all = repo.getAttractions();
        assertThat(all.size()).isEqualTo(4);

        TouristAttraction wp = new TouristAttraction(
                "Water Park",
                "Fun with water",
                Cities.Glostrup,
                List.of(Tags.Adult, Tags.Children, Tags.Entertainment, Tags.Active)
        );

        repo.addAttraction(wp);

        all = repo.getAttractions();
        assertThat(all.size()).isEqualTo(4);
    }

    @Test
    void insertExistingAttractionDoesNotInsertNewTagsOrAttractionTagsOrCitiesIfNotNew() {
        TouristAttraction wp = new TouristAttraction(
                "Water Park",
                "Fun with water",
                Cities.Glostrup,
                List.of(Tags.Adult, Tags.Children, Tags.Entertainment, Tags.Active)
        );

        int nAttractionTagsBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM AttractionTags", Integer.class);
        assertThat(nAttractionTagsBefore = 4);

        int nTagsBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Tags", Integer.class);
        assertThat(nTagsBefore = 5);

        int nCitiesBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Tags", Integer.class);
        assertThat(nCitiesBefore = 2);

        repo.addAttraction(wp);

        int nAttractionTagsAfter = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM AttractionTags", Integer.class);
        assertThat(nAttractionTagsAfter = 4);

        int nTagsAfter = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Tags", Integer.class);
        assertThat(nTagsAfter = 5);

        int nCitiesAfter = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Tags", Integer.class);
        assertThat(nCitiesAfter = 2);
    }

    @Test
    void updateExistingAttractionChangesDescriptionAndTagsAndCitySuccessfully() {
        TouristAttraction wpNew = new TouristAttraction(
                "Water Park",
                "Woe with water",
                Cities.Copenhagen,
                List.of(Tags.Children, Tags.Entertainment, Tags.Active)
        );

        repo.updateTouristAttraction(wpNew);

        TouristAttraction wpFromDB = repo.getAttractionByName(wpNew.getName());
        assertThat(wpFromDB.equals(wpNew)); // See TouristAttraction.equals() if you wonder why this works.
    }

    @Test
    void updateNonExistentAttractionDoesNothingAndThrowsEmptyResultException() {
        TouristAttraction wpNew = new TouristAttraction(
                "Water Parque",
                "Woe with water",
                Cities.Copenhagen,
                List.of(Tags.Children, Tags.Entertainment, Tags.Active)
        );

        assertThrows(EmptyResultDataAccessException.class, () -> {repo.updateTouristAttraction(wpNew); });
    }
}
