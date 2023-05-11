package com.vstavaystrana.vstavaystrana_site.bot;

import com.vstavaystrana.vstavaystrana_site.services.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private enum State {
        START,
        USERNAME,
        PASSWORD,
        PROJECT,
        NEWS_CONTENT
    }

    @Autowired
    private UserService userService;
    private final String botUsername;
    private Map<String, String> chatIdToDatabaseUsername = new HashMap<>();
    private Map<String, State> chatIdToState = new HashMap<>();

    @Autowired
    public TelegramBot() throws TelegramApiException {
        super(Dotenv.load().get("BOT_TOKEN"));
        botUsername = Dotenv.load().get("BOT_NAME");
        telegramBotsApi().registerBot(this);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            String chatId = update.getMessage().getChatId().toString();
            String userMessage = update.getMessage().getText();
            String outMessage = "";

            if (userMessage.equals("/start")
                    || userMessage.equals("Начать сначала")
                    || !chatIdToState.containsKey(chatId)) {
                chatIdToState.put(chatId, State.START);
                chatIdToDatabaseUsername.remove(chatId);
            }

            switch (chatIdToState.get(chatId)) {
                case START -> {
                    outMessage = "Введите логин";
                    chatIdToState.put(chatId, State.USERNAME);
                }

                case USERNAME -> {
                    try {
                        System.out.println(userService.loadUserByUsername(userMessage));
                    } catch (UsernameNotFoundException e)
                    {
                        outMessage = "Такого логина нет. Введите логин";
                        break;
                    }

                    outMessage = "Логин верный. Введите пароль";
                    chatIdToState.put(chatId, State.PASSWORD);
                }


                // в любой момент в chatIdToDatabaseUsername или chatIdToState может не оказаться нужной инфы
                // Тогда: "возникла ошибка
                default -> {
                }
            }

            setButtons(message);
            message.setChatId(chatId);
            message.setText(outMessage);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Начать сначала"));

        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    @Override
    public String getBotUsername() { return botUsername; }
}