package eu.aheads.demo.repository;

import eu.aheads.demo.model.Beneficial;
import java.util.Calendar;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class BeneficialRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    BeneficialRepository beneficialRepository;

    @Test
    public void findById() {

        Beneficial beneficial = new Beneficial();
        long time = Calendar.getInstance().getTimeInMillis();
        String name = "" + time;
        beneficial.setForeName(name);
        beneficial.setId(time);

        entityManager.persist(beneficial);

        assertThat(beneficialRepository.findById(time).get().getForeName(), equalTo(name));

    }

}
