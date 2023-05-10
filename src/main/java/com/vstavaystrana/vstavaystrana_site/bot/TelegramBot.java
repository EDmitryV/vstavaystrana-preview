package com.vstavaystrana.vstavaystrana_site.bot;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    private final String botUsername;
    private State state = State.START;
    private Map<String, String> chatIdToDatabaseUsername = new HashMap<>();

    public TelegramBot() {
        super(Dotenv.load().get("BOT_TOKEN"));
        botUsername = Dotenv.load().get("BOT_NAME");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            String chatId = update.getMessage().getChatId().toString();
            String userMessage = update.getMessage().getText();
            String outMessage = "";

            if (userMessage.equals("/start")) {
                state = State.START;
                chatIdToDatabaseUsername.remove(chatId);
            }

            switch (state) {
                case START -> {
                    outMessage = "Введите логин";
                    state = State.USERNAME;
                }

                case USERNAME -> {
                    outMessage = "Введите пароль";
                    state = State.PASSWORD;
                }
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
        keyboardFirstRow.add(new KeyboardButton("/start"));

        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    @Override
    public String getBotUsername() { return botUsername; }
}