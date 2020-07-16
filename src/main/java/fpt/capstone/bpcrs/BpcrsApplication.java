package fpt.capstone.bpcrs;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Role;
import fpt.capstone.bpcrs.repository.AccountRepository;
import fpt.capstone.bpcrs.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BpcrsApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(BpcrsApplication.class, args);
  }

  @Value("${sa.mail}")
  private String SA_MAIL;

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(BpcrsApplication.class);
  }

  @Bean
  public CommandLineRunner initData(RoleRepository roleRepository, AccountRepository accountRepository) {
    return args -> {
      if ((roleRepository.findByName(RoleEnum.ADMINISTRATOR.name()) == null) || (roleRepository.findByName(RoleEnum.USER.name()) == null)) {
        Role admin = new Role(RoleEnum.ADMINISTRATOR.name(), true, null);
        roleRepository.save(admin);
        roleRepository.save(new Role(RoleEnum.USER.name(), true, null));
        accountRepository.save(Account.builder().email(SA_MAIL).fullName("ADMIN").role(admin).active(true).imageUrl("default").build());
      }
    };
  }
}
