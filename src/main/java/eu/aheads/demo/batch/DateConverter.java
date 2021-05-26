package eu.aheads.demo.batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final Log logger = LogFactory.getLog(DateConverter.class);

    @Override
    public Date convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{2}$")) {
            source += "-01 00:00:00";
        } else if (source.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            source += " 00:00:00";
        }
        try {
            return df.parse(source);
        } catch (ParseException e) {
            logger.error(e.toString());
        }
        return null;
    }

}
