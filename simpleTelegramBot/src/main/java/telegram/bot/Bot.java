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
            String message_text = update.getMessage().getText();
            SendMessage message = new SendMessage()     // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId());

            switch (message_text) {
                case "/time":
                    message.setText(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
                    break;
                case "/date":
                    message.setText(LocalDate.now().toString());
                    break;
                case "/datetime":
                    message.setText(String.join(" ",
                            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString().split("T")));
                    break;
                default:
                    message.setText("Hello, " + message_text);
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
