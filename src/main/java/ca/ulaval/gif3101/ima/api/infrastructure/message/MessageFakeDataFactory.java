package ca.ulaval.gif3101.ima.api.infrastructure.message;

import ca.ulaval.gif3101.ima.api.infrastructure.message.dto.MessageEntity;
import ca.ulaval.gif3101.ima.api.utils.RandomGenerator;
import com.github.javafaker.Faker;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MessageFakeDataFactory {

    private final RandomGenerator randomGenerator;
    private MessageDAO messageDAO;
    private Faker faker;

    public MessageFakeDataFactory(MessageDAO messageDAO, Faker faker, RandomGenerator randomGenerator) {
        this.messageDAO = messageDAO;
        this.faker = faker;
        this.randomGenerator = randomGenerator;
    }

    public void create(int numberOfEntries) throws Exception{
        System.out.println(String.format("Creating %d fake messages entries", numberOfEntries));
        Faker faker = new Faker(randomGenerator.random());
        MessageEntity entity;
        Date expiresStart = createDate(2017, 1, 1);
        Date expiresEnd = createDate(2020, 1, 1);
        Date expires;

        Date createdStart = createDate(2016, 7, 1);
        Date createdEnd = createDate(2019, 7, 1);
        Date created;

        for (int i = 0; i < numberOfEntries; i++) {
            entity = new MessageEntity();
            entity.title = faker.lorem().sentence();
            entity.body = faker.lorem().paragraph();

            created = faker.date().between(createdStart, createdEnd);
            do {
                expires = faker.date().between(expiresStart, expiresEnd);
            } while (expires.before(created));

            entity.created = dateToString(created);
            entity.expires = dateToString(expires);
            entity.longitude = Double.valueOf(faker.address().longitude());
            entity.latitude = Double.valueOf(faker.address().latitude());

            messageDAO.create(entity);
        }
    }

    private Date createDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return calendar.getTime();
    }

    private String dateToString(Date date) {
        DateTime dt = new DateTime(date);
        return dt.toDateTimeISO().toString();
    }

}