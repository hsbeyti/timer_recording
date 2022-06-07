package hsbeyti.time.recording.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AutehticationController {
    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
}
