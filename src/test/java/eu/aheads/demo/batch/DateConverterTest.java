package eu.aheads.demo.batch;

import eu.aheads.demo.batch.DateConverter;
import java.text.SimpleDateFormat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;

public class DateConverterTest {

    private final DateConverter converter = new DateConverter();
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testConvert() {

        assertThat(df.format(converter.convert("2021-01")), equalTo("2021-01-01 00:00:00"));
        assertThat(df.format(converter.convert("2021-01-01")), equalTo("2021-01-01 00:00:00"));
        assertThat(df.format(converter.convert("2021-01-01 00:00:00")), equalTo("2021-01-01 00:00:00"));
        assertThat(converter.convert("2021-01-0"), nullValue());
    }
}
