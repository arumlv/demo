package eu.aheads.demo.repository;

import eu.aheads.demo.model.Register;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

public interface RegisterRepository extends PagingAndSortingRepository<Register, String> {

    @Query("from Register r where "
            + "(:regCode is null or r.regCode LIKE %:regCode%) and "
            + "r.regCode IN (select b.leRegCode from Beneficial b where "
            + "(:foreName is null or b.foreName LIKE %:foreName%) and"
            + "(:surName is null or b.surName LIKE %:surName%) and"
            + "(:birthDate is null or b.birthDate = :birthDate))")
    Page<Register> findByParams(Pageable pageable, @Param("regCode") String regCode,
            @Param("foreName") String foreName, @Param("surName") String surName,
            @Param("birthDate") @DateTimeFormat(pattern = "yyyyMMdd") Date birthDate);

}
