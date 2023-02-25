package main.dto;

import main.model.Message;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MessageMapper {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter
            .ofPattern("HH:mm:SS dd.MM.yyyy", new Locale("ru", "RU"));

    public static MessageDTO map (Message message) {
        MessageDTO dtoMessage = new MessageDTO();
        dtoMessage.setDatetime(message.getDateTime().format(FORMAT));
        dtoMessage.setUsername(message.getUser().getName());
        dtoMessage.setText(message.getMessage());
        return dtoMessage;
    }
}
