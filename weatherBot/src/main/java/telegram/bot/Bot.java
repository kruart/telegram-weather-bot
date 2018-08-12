package telegram.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot  {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Model model = new Model();
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            SendMessage message = new SendMessage()     // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId());

            switch (message_text) {
                case "/help":
                    message.setText("How can I help you?");
                    break;
                case "/settings":
                    message.setText("What settings would you like to change?");
                    break;
                default:
                    try {
                        message.setText(Weather.getWeather(message_text, model));
                    } catch (IOException e) {
                        message.setText("City " + message_text + " not found.");
                    }
                    break;
            }

            try {
                setButtons(message);
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void setButtons(SendMessage message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        //first line of the keyboard
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("/help");
        keyboardFirstRow.add("/settings");

        //second line of the keyboard
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("Kyiv");
        keyboardSecondRow.add("Turin");

        keyboardRows.add(keyboardFirstRow);
        keyboardRows.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    @Override
    public String getBotUsername() {
        return "t-1000";
    }

    @Override
    public String getBotToken() {
        return "691644341:AAH4Jh5p2T-pct1MaxpeYj41qzQZTf8b3i0";
    }
}
