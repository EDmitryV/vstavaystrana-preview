package com.vstavaystrana.vstavaystrana_site.bot;

import com.vstavaystrana.vstavaystrana_site.models.Businessman;
import com.vstavaystrana.vstavaystrana_site.models.News;
import com.vstavaystrana.vstavaystrana_site.models.Project;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.repositories.NewsRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.ProjectRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.UserRepository;

import com.vstavaystrana.vstavaystrana_site.services.BusinessmanService;
import com.vstavaystrana.vstavaystrana_site.services.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

import java.util.*;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private enum State {
        START,
        USERNAME,
        PASSWORD,
        PROJECT,
        NEWS
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserService userService;
    @Autowired
    BusinessmanService businessmanService;
    private final String botUsername;
    private Map<String, State> chatIdToState = new HashMap<>();
    private Map<String, User> chatIdToUser = new HashMap<>();
    private Map<String, Project> chatIdToProject = new HashMap<>();

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
            String outMessage;

            if (userMessage.equals("/start")
                    || userMessage.equals("Начать сначала")
                    || !chatIdToState.containsKey(chatId)) {
                chatIdToState.put(chatId, State.START);
                chatIdToUser.remove(chatId);
            }

            switch (chatIdToState.get(chatId)) {
                case START:
                    outMessage = "Введите логин";
                    chatIdToState.put(chatId, State.USERNAME);
                    break;

                case USERNAME:
                    User user = userRepository.findByUsername(userMessage);
                    if (user == null)
                        outMessage = "Пользователя с таким логином не существует. Введите пароль";
                    else {
                        outMessage = "Логин верный. Введите пароль";
                        chatIdToUser.put(chatId, user);
                        chatIdToState.put(chatId, State.PASSWORD);
                    }
                    break;

                case PASSWORD:
                    User userForPassword = chatIdToUser.get(chatId);
                    var encoder = userService.getbCryptPasswordEncoder();
                    if (userForPassword != null && encoder.matches(userMessage, userForPassword.getPassword())) {
                        outMessage = "Пароль верный! Введите название проекта";
                        chatIdToState.put(chatId, State.PROJECT);
                        break;
                    }
                    if (userForPassword != null && !encoder.matches(userMessage, userForPassword.getPassword())) {
                        outMessage = "Пароль неверный. Введите пароль";
                        break;
                    }

                case PROJECT:
                    User userForBusinessman = chatIdToUser.get(chatId);
                    if (userForBusinessman != null) {
                        Businessman businessmen = businessmanService.findBusinessmanByUser(userForBusinessman);
                        if (businessmen != null) {
                            List<Project> userProjects = projectRepository.findAll();
                            Optional<Project> userProject = userProjects
                                    .stream()
                                    .filter(project -> project.getName().equals(userMessage))
                                    .findFirst();
                            if (userProject.isEmpty()) {
                                outMessage = "Нет проекта с таким именем. Введите название проекта";
                                break;
                            }
                            outMessage = "Проект выбран. Введите текст новости";
                            chatIdToProject.put(chatId, userProject.get());
                            chatIdToState.put(chatId, State.NEWS);
                            break;
                        }
                    }

                case NEWS:
                    Project project = chatIdToProject.get(chatId);
                    if (project != null) {
                        News news = new News();
                        news.setContent(userMessage);
                        news.setProject(project);
                        newsRepository.save(news);

                        outMessage = "Новость опубликована";
                        chatIdToState.put(chatId, State.USERNAME);
                        break;
                    }

                default:
                    outMessage = "Возникла ошибка, начинаем сначала. Введите логин";
                    chatIdToState.put(chatId, State.USERNAME);
                    break;
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