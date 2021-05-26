package eu.aheads.demo.repository;

import eu.aheads.demo.model.Beneficial;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BeneficialRepository extends PagingAndSortingRepository<Beneficial, Long> {

}
