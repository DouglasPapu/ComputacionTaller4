package co.edu.icesi.fi.tics.tssc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.AdminService;
import co.edu.icesi.fi.tics.tssc.services.GameService;
import co.edu.icesi.fi.tics.tssc.services.TopicService;

@SpringBootApplication
//@EnableWebMvc
public class TallerPersistenciaApplication {

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(TallerPersistenciaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(AdminService adminService, TopicService topicService, GameService gameService) {
		return (args) -> {

			// Agregar un usuario superadmin.
			TsscAdmin user = new TsscAdmin();
			user.setUser("superadmin");
			user.setPassword("{noop}123");
			user.setSuperAdmin("superadmin");
			adminService.save(user);

			// Agregar un usuario admin.
			TsscAdmin user2 = new TsscAdmin();
			user2.setUser("admin");
			user2.setPassword("{noop}123");
			user2.setSuperAdmin("admin");
			adminService.save(user2);
			
			
			TsscTopic topic1 = new TsscTopic();
			topic1.setDefaultGroups(8);
			topic1.setDefaultSprints(8);
			topic1.setName("Hola");
			topic1.setName("Ilian");
			topicService.saveTopic(topic1);
			long one =1;
			TsscGame game1 = new TsscGame();
			game1.setAdminPassword("da");
			game1.setGuestPassword("da");
			game1.setName("Juego 1");
			game1.setnGroups(1);
			game1.setnSprints(1);
			game1.setPauseSeconds(one);
			game1.setScheduledDate(LocalDate.of(2020, 05, 01));
			game1.setStartTime(LocalTime.of(9, 26));
			game1.setScheduledTime(LocalTime.of(9, 26));
			game1.setTsscTopic(topic1);
			gameService.saveGame(game1);
			
			TsscGame game2 = new TsscGame();
			game2.setAdminPassword("da");
			game2.setGuestPassword("da");
			game2.setName("Juego 1");
			game2.setnGroups(1);
			game2.setnSprints(1);
			game2.setPauseSeconds(one);
			game2.setScheduledDate(LocalDate.of(2020, 05, 01));
			game2.setStartTime(LocalTime.of(9, 26));
			game2.setScheduledTime(LocalTime.of(9, 26));
			game2.setTsscTopic(topic1);
			gameService.saveGame(game2);
			
			TsscGame game3 = new TsscGame();
			game3.setAdminPassword("da");
			game3.setGuestPassword("da");
			game3.setName("Juego 3");
			game3.setnGroups(1);
			game3.setnSprints(1);
			game3.setPauseSeconds(one);
			game3.setScheduledDate(LocalDate.of(2020, 05, 01));
			game3.setStartTime(LocalTime.of(9, 26));
			game3.setScheduledTime(LocalTime.of(9, 26));
			game3.setTsscTopic(topic1);
			gameService.saveGame(game3);
			
	
			
			
			
		};

	}

}
