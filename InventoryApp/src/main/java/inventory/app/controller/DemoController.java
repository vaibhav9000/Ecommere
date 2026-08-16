package inventory.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class DemoController {

    @RequestMapping("/public/api/hello")
    public String helloPublic() {
        return "Hello, Public!";
    }

    @RequestMapping("/user/api/hello")
    public String helloUser() {
        return "Hello, User!";
    }

    @RequestMapping("/service/api/hello")
    public String helloServiceUser() {
        return "Hello, Service User!";
    }
}
