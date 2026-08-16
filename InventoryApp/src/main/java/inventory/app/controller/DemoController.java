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

    @RequestMapping("/private/api/hello")
    public String helloPrivate() {
        return "Hello, Private!";
    }
}
