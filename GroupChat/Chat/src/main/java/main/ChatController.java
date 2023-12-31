package main;

import main.dto.MessageDTO;
import main.dto.MessageMapper;
import main.dto.UserDTO;
import main.dto.UserMapper;

import main.model.Message;
import main.model.MessageRepository;
import main.model.User;
import main.model.UserRepository;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ChatController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/init")
    public HashMap<String, Boolean> init() {
        HashMap<String, Boolean> response = new HashMap<>();
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<User> userOpt = userRepository.findBySessionId(sessionId);

        response.put("result", userOpt.isPresent());
        return response;
    }

    @PostMapping("/auth")
    public HashMap<String, Boolean> auth(@RequestParam String name) {
        HashMap<String, Boolean> response = new HashMap<>();
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
    public HashMap<String, Boolean> sendMessage(@RequestParam String message) {
        HashMap<String, Boolean> response = new HashMap<>();
        if (Strings.isEmpty(message)) {
            response.put("result", false);
            return response;
        }
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = userRepository.findBySessionId(sessionId).get();

        Message msg = new Message();
        msg.setDateTime(LocalDateTime.now());
        msg.setMessage(message);
        msg.setUser(user);
        messageRepository.saveAndFlush(msg);

        response.put("result", true);
        return response;
    }

    @GetMapping("/message")
    public List<MessageDTO> getMessagesList() {
        return messageRepository
                .findAll(Sort.by(Sort.Direction.ASC, "dateTime"))
                .stream()
                .map(MessageMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/user")
    public List<UserDTO> getUsersList(){
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(UserMapper::map)
                .collect(Collectors.toList());
    }
}

