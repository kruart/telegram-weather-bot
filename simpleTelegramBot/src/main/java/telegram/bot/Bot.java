package telegram.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            SendMessage message = new SendMessage();    // Create a SendMessage object with mandatory fields

            switch (message_text) {
                case "/time":
                    message.setChatId(chat_id)
                            .setText(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
                    break;
                case "/date":
                    message.setChatId(chat_id)
                            .setText(LocalDate.now().toString());
                    break;
                case "/datetime":
                    message.setChatId(chat_id)
                            .setText(String.join(" ", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString().split("T")));
                    break;
                default:
                    message.setChatId(chat_id)
                            .setText("Hello, " + message_text);
                    break;
            }

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
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
