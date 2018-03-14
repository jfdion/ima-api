package ca.ulaval.gif3101.ima.api.infrastructure.message;

import ca.ulaval.gif3101.ima.api.domain.Distance.Distance;
import ca.ulaval.gif3101.ima.api.domain.location.Location;
import ca.ulaval.gif3101.ima.api.domain.message.MessageDto;
import ca.ulaval.gif3101.ima.api.infrastructure.message.dao.MessageDAO;
import ca.ulaval.gif3101.ima.api.infrastructure.message.dto.MessageEntity;
import ca.ulaval.gif3101.ima.api.utils.RandomGenerator;
import com.github.javafaker.Faker;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public class MessageFakeDataFactory {

    private final RandomGenerator randomGenerator;
    private MessageDAO messageDAO;
    private Faker faker;

    public MessageFakeDataFactory(MessageDAO messageDAO, Faker faker, RandomGenerator randomGenerator) {
        this.messageDAO = messageDAO;
        this.faker = faker;
        this.randomGenerator = randomGenerator;
    }

    public void create(int numberOfEntries) throws Exception {
        System.out.println(String.format("Creating %d fake messages entries", numberOfEntries));
        Faker faker = new Faker(randomGenerator.random());
        MessageDto dto;
        Date expiresStart = createDate(2017, 10, 1);
        Date expiresEnd = createDate(2020, 1, 1);
        Date expires;

        Date createdStart = createDate(2016, 7, 1);
        Date createdEnd = createDate(2019, 7, 1);
        Date created;

        // Coordonnées du centre de Québec
        Location location = new Location(46.816667, -71.216667);
        Location currentLocation;

        for (int i = 0; i < numberOfEntries; i++) {
            dto = new MessageDto();
            dto.title = faker.lorem().sentence();
            dto.body = faker.lorem().paragraph();

            created = faker.date().between(createdStart, createdEnd);
            do {
                expires = faker.date().between(expiresStart, expiresEnd);
            } while (expires.before(created));

            dto.created = dateToString(created);
            dto.expires = dateToString(expires);

            // 65 KM autour de québec
            currentLocation = location.add(
                    Distance.fromMeters(faker.number().numberBetween(0, 65000)),
                    Distance.fromMeters(faker.number().numberBetween(0, 65000))
            );

            dto.latitude = currentLocation.getLatitude();
            dto.longitude = currentLocation.getLongitude();

            messageDAO.create(dto);
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
