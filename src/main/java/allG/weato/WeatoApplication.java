package allG.weato;


import allG.weato.oauth2.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class WeatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatoApplication.class, args);
	}

}
