package eu.aheads.demo.batch;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobInfo {

    private String name;
    private Date startTime;
    private Date endTime;
    private String status;
    private Integer writeCount;

}
