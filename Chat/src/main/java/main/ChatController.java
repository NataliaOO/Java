package main;

import main.model.User;
import main.model.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;
import java.util.List;

@RestController
public class ChatController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/init")
    public HashMap<String, Boolean> init() {
        HashMap<String, Boolean> response = new HashMap<>();
        //TODO: check sessionId.

        response.put("result", true);
        return response;
    }

    @PostMapping("/auth")
    public HashMap<String, Boolean> auth(@RequestParam String name) {
        HashMap<String, Boolean> response = new HashMap<>();
        //TODO: add servise
        if(!Strings.isNotEmpty(name)) {
            response.put("result", false);
            return response;
        }
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = new User();
        user.setName(name);
        user.setSessionId(sessionId);
        userRepository.save(user);

        response.put("result", true);
        return response;
    }

    @PostMapping("/message")
    public Boolean sendMessage(@RequestParam String message) {
        return true;
    }

    @GetMapping("/message")
    public List<String> getMessagesList() {
        return null;
    }

    @GetMapping("/user")
    public HashMap<Integer,String> getUsersList() {
        return null;
    }
}

