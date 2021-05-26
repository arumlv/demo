package eu.aheads.demo.repository;

import eu.aheads.demo.model.Beneficial;
import eu.aheads.demo.model.Register;
import java.util.Calendar;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@DataJpaTest
public class RegisterRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    RegisterRepository registerRepository;

    @Test
    public void testFindByParams() {

        Register register = new Register();
        Beneficial beneficial = new Beneficial();
        long time = Calendar.getInstance().getTimeInMillis();
        String regCode = "" + time;
        beneficial.setId(time);
        beneficial.setLeRegCode(regCode);
        register.setName(regCode);
        register.setRegCode(regCode);
        entityManager.persist(beneficial);
        entityManager.persist(register);

        Page<Register> page = registerRepository.findByParams(new PageRequest(0, 10, Sort.unsorted()) {
        }, regCode, null, null, null);

        assertThat(page.getContent().iterator().next().getName(), equalTo(regCode));
    }

}
