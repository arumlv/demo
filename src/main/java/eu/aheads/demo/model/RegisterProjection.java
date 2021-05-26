package eu.aheads.demo.model;

import java.util.Date;
import java.util.Set;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "registers", types = Register.class)
public interface RegisterProjection {

    public String getRegCode();

    public String getName();

    public String getTypeText();

    public Date getRegistered();

    public Date getTerminated();

    public String getAddress();

    public Set<Beneficial> getBeneficials();
}
