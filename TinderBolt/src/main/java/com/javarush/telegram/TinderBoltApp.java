package com.javarush.telegram;

import com.javarush.telegram.ChatGPTService;
import com.javarush.telegram.DialogMode;
import com.javarush.telegram.MultiSessionTelegramBot;
import com.javarush.telegram.UserInfo;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;

public class TinderBoltApp extends MultiSessionTelegramBot {
    public static final String TELEGRAM_BOT_NAME = "Personal_GPT_Helper_bot"; //TODO: добавь имя бота в кавычках
    public static final String TELEGRAM_BOT_TOKEN = "7575830388:AAGPLJKIxVsvv9T1uMIPkTIoIp3u64CzbJM"; //TODO: добавь токен бота в кавычках
    public static final String OPEN_AI_TOKEN = "gpt:y8HQgXidYWEPQ52jBuwnJFkblB3T8AScAHOTiNP46pG97Qae"; //TODO: добавь токен ChatGPT в кавычках

    private final ChatGPTService chatGTP = new ChatGPTService(OPEN_AI_TOKEN);
    private DialogMode currentMode;
    private final ArrayList<String> chatHistory = new ArrayList<>();

    private UserInfo me, she;
    private int questionCount;

    public TinderBoltApp() {
        super(TELEGRAM_BOT_NAME, TELEGRAM_BOT_TOKEN);
        this.currentMode = DialogMode.MAIN;
    }

    @Override
    public void onUpdateEventReceived(Update update) {
        //TODO: основной функционал бота будем писать здесь
        String message = getMessageText();
        switch (message) {
            case "/start":
                handleStartCommand();
                break;
            case "/gpt":
                switchToGptMode();
                break;
            case "/date":
                switchToDateMode();
                break;
            case "/message":
                switchToMessageMode();
                break;
            case "/opener":
                startOpener();
                break;
            case "/profile":
                startProfile();
                break;
            default:
                handleDefaultMessage(message);
                break;
        }
    }

    private void handleStartCommand() {
        currentMode = DialogMode.MAIN;
        sendPhotoMessage("main");
        sendTextMessage(loadMessage("main"));
        showMainMenu(
                "Главное меню бота", "/start",
                "Генерация Tinder-профиля \uD83D\uDE0E", "/profile",
                "Сообщение для знакомства \uD83E\uDD70", "/opener",
                "Переписка от вашего имени \uD83D\uDE08", "/message",
                "Переписка со звездами \uD83D\uDD25", "/date",
                "Задать вопрос чату GPT \uD83E\uDDE0", "/gpt"
        );
    }

    private void switchToGptMode() {
        currentMode = DialogMode.GPT;
        sendPhotoMessage("gpt");
        sendTextMessage(loadMessage("gpt"));
    }

    private void switchToDateMode() {
        currentMode = DialogMode.DATE;
        sendPhotoMessage("date");
        sendTextButtonsMessage(loadMessage("date"),
                "Ариана Гранде","date_grande",
                "Марго Робби","dare_robbie",
                "Зендея","date_zendaya",
                "Райн Гослинг","date_gosling",
                "Том харди","date_hardy");
    }

    private void switchToMessageMode() {
        currentMode = DialogMode.MESSAGE;
        sendPhotoMessage("message");
        sendTextButtonsMessage("Пришлите в чат вашу переписку",
                "Следующее сообщение", "message_next",
                "Пригласить на свидание", "message_date");
    }

    private void startOpener() {
        currentMode = DialogMode.OPENER;
        sendPhotoMessage("opener");
        she = new UserInfo();
        questionCount = 1;
        sendTextMessage("Имя девушки?");
    }

    private void startProfile() {
        currentMode = DialogMode.PROFILE;
        sendPhotoMessage("profile");
        me = new UserInfo();
        questionCount = 1;
        sendTextMessage("Сколько вам лет?");
    }

    private void handleDefaultMessage(String message) {
        switch (currentMode) {
            case GPT:
                processGptMessage(message);
                break;
            case MESSAGE:
                processUserChatMessage(message);
                break;
            case DATE:
                processDateMessage(message);
                break;
            case OPENER:
                processOpenerMessage(message);
                break;
            case PROFILE:
                processProfileMessage(message);
                break;
            default:
                showGeneralMessage(message);
                break;
        }
    }

    private void processGptMessage(String userMessage) {
        String gptResponse = chatGTP.sendMessage(loadPrompt("gpt"), userMessage);
        sendTextMessage(gptResponse);
    }

    private void processUserChatMessage(String message) {
        String query = getCallbackQueryButtonKey();
        if (query.startsWith("message_")) {
            String userChatHistory = String.join("\n\n", chatHistory);
            Message waitingMessage = sendTextMessage("Подождите пару секунд - ChatGPT думает...");
            String response = chatGTP.sendMessage(loadPrompt(query), userChatHistory);
            updateTextMessage(waitingMessage, response);
        }
        chatHistory.add(message);
    }

    private void processDateMessage(String message) {
        String query = getCallbackQueryButtonKey();
        if (query.startsWith("date_")) {
            sendPhotoMessage(query);
            sendTextMessage("Отличный выбор! 💕\n*Пригласите на свидание за 5 сообщений. Начинайте!*");
            chatGTP.setPrompt(loadPrompt(query));
            return;
        }
        Message waitingMessage = sendTextMessage("Печатает...✏");
        String response = chatGTP.addMessage(message);
        updateTextMessage(waitingMessage, response);
    }

    private void processOpenerMessage(String userMessage) {
        switch (questionCount) {
            case 1:
                she.name = userMessage;
                questionCount++;
                sendTextMessage("Сколько ей лет?");
                break;
            case 2:
                she.age = userMessage;
                questionCount++;
                sendTextMessage("Есть ли у нее хобби и какие?");
                break;
            case 3:
                she.hobby = userMessage;
                questionCount++;
                sendTextMessage("Кем она работает?");
                break;
            case 4:
                she.occupation = userMessage;
                questionCount++;
                sendTextMessage("Цель знакомства?");
                break;
            case 5:
                she.goals = userMessage;
                Message msg = sendTextMessage("Подождите пару секунд - ChatGPT \uD83E\uDDE0 думает...");
                String answer = chatGTP.sendMessage(loadPrompt("opener"), she.toString());
                updateTextMessage(msg, answer);
                break;
        }
    }

    private void processProfileMessage(String userMessage) {
        switch (questionCount) {
            case 1:
                me.age = userMessage;
                questionCount++;
                sendTextMessage("Кем вы работает?");
                break;
            case 2:
                me.occupation = userMessage;
                questionCount++;
                sendTextMessage("У вас есть хобби?");
                break;
            case 3:
                me.hobby = userMessage;
                questionCount++;
                sendTextMessage("Что вам НЕ нравиться в людях?");
                break;
            case 4:
                me.annoys = userMessage;
                questionCount++;
                sendTextMessage("Цель знакомства?");
                break;
            case 5:
                me.goals = userMessage;
                Message msg = sendTextMessage("Подождите пару секунд - ChatGPT \uD83E\uDDE0 думает...");
                String answer = chatGTP.sendMessage(loadPrompt("profile"), me.toString());
                updateTextMessage(msg, answer);
                break;
        }
    }

    private void showGeneralMessage(String message) {
        sendTextMessage("*Hello!*");
        sendTextMessage("Вы написали: " + "_" + message + "_");

        sendTextButtonsMessage("Выберите режим работы:",
                "Start", "start",
                "Stop", "stop");
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TinderBoltApp());
    }
}
