package org.NasaTelegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class NasaTelegramBot extends TelegramLongPollingBot {
    public static final String BOT_TOKEN = "";
    public static final String BOT_USERNAME = "KosmoPicture_bot";
    public static final String URI = "https://api.nasa.gov/planetary/apod?api_key=WW9WDPnKyGDcfv6oduTpUpTvyOi51kr06PQrNeAA";
    public static long CHAT_ID; // если прописан конкретно, то только этот пользователь сможет работать с нашим ботом.

    // конструктор произведёт автоматическую регистрацию при выходе в сеть
    public NasaTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() { return BOT_USERNAME; }

    @Override
    public String getBotToken() { return BOT_TOKEN; }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            CHAT_ID = update.getMessage().getChatId();
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Привет, я бот NASA! Я высылаю ссылки на картинки по запросу." +
                            "Картинки обновляются раз в сутки.");
                    break; // если убрать break, то выполнится сразу /help и /give

                case "/give":
                    try { sendMessage(Utils.getUrl(URI)); }
                    catch  (IOException e) { throw new RuntimeException(e); }

                    break;

            }
        }
    }
    public void sendMessage (String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(CHAT_ID);
        message.setText(messageText);
        try { execute(message); }
        catch (TelegramApiException e) { e.printStackTrace(); }
    }

}