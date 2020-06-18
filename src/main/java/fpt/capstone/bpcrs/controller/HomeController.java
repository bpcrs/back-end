package fpt.capstone.bpcrs.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping
@RestController
public class HomeController {

    @Value("${server.version}")
    private String version;

    @GetMapping
    public String welcome(){
        return String.format("<code>API %s - deployed at %s</code>",version,new Date());
    }
}
