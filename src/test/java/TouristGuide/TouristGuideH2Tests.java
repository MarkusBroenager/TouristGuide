package TouristGuide;

import TouristGuide.Model.Cities;
import TouristGuide.Model.Tags;
import TouristGuide.Model.TouristAttraction;
import TouristGuide.Repository.TouristRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.sql.init.mode=never"
})
@Sql(scripts = { "classpath:schemah2.sql", "classpath:datah2.sql"}, executionPhase = BEFORE_TEST_METHOD)

class PersonRepositoryTest {

    @Autowired
    private TouristRepository repo;

    @Test
    //Virker kun hvis man kører testen selvstændigt
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
}
