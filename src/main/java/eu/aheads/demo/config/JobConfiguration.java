package eu.aheads.demo.config;

import eu.aheads.demo.batch.DateConverter;
import eu.aheads.demo.model.Beneficial;
import eu.aheads.demo.model.Register;
import eu.aheads.demo.repository.BeneficialRepository;
import eu.aheads.demo.repository.RegisterRepository;
import java.net.MalformedURLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.UrlResource;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BeneficialRepository beneficialRepository;

    @Autowired
    private RegisterRepository registerRepository;

    private final Log logger = LogFactory.getLog(JobConfiguration.class);

    private final String[] registerFields = new String[]{"regCode", "sepa", "name",
        "nameBeforeQuotes", "nameInQuotes", "nameAfterQuotes", "withoutQuotes",
        "regType", "regTypeText", "type", "typeText", "registered", "terminated",
        "closed", "address", "index", "addressId", "region", "city", "atvk",
        "reRegistrationTerm"};

    private final String[] beneficialFields = new String[]{"id", "leRegCode",
        "foreName", "surName", "latvianIdentityNumber", "birthDate",
        "nationality", "residence", "registeredOn", "lastModifiedAt"};

    @Bean
    public Job registerJob(FlatFileItemReader<Register> reader) throws MalformedURLException {
        return jobBuilderFactory.get("registerJob").start(stepBuilderFactory.get("registerJob")
                .<Register, Register>chunk(100)
                .reader(reader).writer(registerWriter()).build()).build();
    }

    @Bean
    public Job beneficialJob(FlatFileItemReader<Beneficial> reader) throws MalformedURLException {
        return jobBuilderFactory.get("beneficialJob").start(stepBuilderFactory.get("beneficialJob")
                .<Beneficial, Beneficial>chunk(100)
                .reader(reader).writer(beneficialWriter()).build()).build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Register> registerReader(
            @Value("#{jobParameters['input']}") String input) throws MalformedURLException {
        return buildReader("registerReader", registerFields, input, Register.class);
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Beneficial> beneficialReader(
            @Value("#{jobParameters['input']}") String input) throws MalformedURLException {
        return buildReader("beneficialReader", beneficialFields, input, Beneficial.class);
    }

    private FlatFileItemReader buildReader(String name, String[] names,
            String input, Class<?> targetType) throws MalformedURLException {
        return new FlatFileItemReaderBuilder()
                .name(name).linesToSkip(1)
                .resource(new UrlResource(input))
                .delimited().delimiter(";")
                .names(names)
                .fieldSetMapper(new BeanWrapperFieldSetMapper() {
                    {
                        DefaultConversionService dateConversionService = new DefaultConversionService();
        dateConversionService.addConverter(new DateConverter());
                        setConversionService(dateConversionService);
                        setTargetType(targetType);
                    }
                })
                .build();
    }

    @Bean
    public ItemWriter<Beneficial> beneficialWriter() {
        return (List<? extends Beneficial> items) -> {
            beneficialRepository.saveAll(items);
        };
    }

    @Bean
    public ItemWriter<Register> registerWriter() {
        return (List<? extends Register> items) -> {
            registerRepository.saveAll(items);
        };
    }
}
