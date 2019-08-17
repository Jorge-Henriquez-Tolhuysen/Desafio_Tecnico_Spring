package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.bootstrap;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 300)
public class Config
        extends AbstractHttpSessionApplicationInitializer {

    @Autowired
    private Environment env;

    @Bean
    @SpringSessionDataSource
    public DataSource dataSource() {
        HikariConfig databaseConfiguration = new HikariConfig();
        databaseConfiguration.setMaximumPoolSize(20);
        databaseConfiguration.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        databaseConfiguration.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
        databaseConfiguration.setUsername(env.getRequiredProperty("spring.datasource.username"));
        databaseConfiguration.setPassword(env.getRequiredProperty("spring.datasource.password"));
        databaseConfiguration.setAutoCommit(true);
        databaseConfiguration.setMaxLifetime(120000000L);
        databaseConfiguration.setConnectionTestQuery("SELECT 1");
        databaseConfiguration.setPoolName("desafiotecnicoserver");
        HikariDataSource ds = new HikariDataSource(databaseConfiguration);
        return ds;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        String schema = env.getRequiredProperty("spring.session.jdbc.schema");
        String driver = env.getRequiredProperty("spring.datasource.url").split(":")[1];
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setContinueOnError(true);
        resourceDatabasePopulator.addScript(new ClassPathResource(schema.replaceAll("classpath:", "").replaceAll("@@platform@@",driver)));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource());
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
