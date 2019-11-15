package cn.lianhy.demo.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/demo")
public class HelloWorld {

    @GetMapping(value = "sayHello.json")
    public String sayHello(String userName){
        log.info("Hello："+userName);
        log.error("Hello："+userName);
        log.warn("Hello："+userName);
        log.debug("Hello："+userName);
        return "Hello："+userName;
    }
}
