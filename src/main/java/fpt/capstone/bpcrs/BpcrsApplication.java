package fpt.capstone.bpcrs;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Role;
import fpt.capstone.bpcrs.repository.AccountRepository;
import fpt.capstone.bpcrs.repository.RoleRepository;
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

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(BpcrsApplication.class);
  }

  @Bean
  public CommandLineRunner initData(RoleRepository roleRepository, AccountRepository accountRepository) {
    return args -> {
      if (!roleRepository.existsById(1) || !roleRepository.existsById(2)){
        Role admin = new Role(RoleEnum.ADMINISTRATOR.name(), true,null);
        roleRepository.save(admin);
        roleRepository.save(new Role(RoleEnum.USER.name(), true,null));
        accountRepository.save(Account.builder().email("test").fullName("ADMIN").role(admin).build());
      }
    };
  }
}
