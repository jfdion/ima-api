package ca.ulaval.gif3101.ima.api.message.infrastructure.message;

import ca.ulaval.gif3101.ima.api.message.domain.VisibilityPeriod.VisibilityPeriod;
import ca.ulaval.gif3101.ima.api.message.domain.date.DateAdapter;
import ca.ulaval.gif3101.ima.api.message.domain.time.TimeAdapter;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import ca.ulaval.gif3101.ima.api.message.domain.distance.Distance;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.message.external.time.JodaTimeTimeAdapter;
import ca.ulaval.gif3101.ima.api.message.utils.RandomGenerator;
import com.github.javafaker.Faker;

import java.util.Random;


public class MessageFakeDataFactory {

    private final RandomGenerator randomGenerator;
    private MessageRepository messageRepository;
    private Faker faker;

    public MessageFakeDataFactory(MessageRepository messageRepository, Faker faker, RandomGenerator randomGenerator) {
        this.messageRepository = messageRepository;
        this.faker = faker;
        this.randomGenerator = randomGenerator;
    }

    public void create(int numberOfEntries) {
        System.out.println(String.format("Creating %d fake messages entries", numberOfEntries));
        Random random = randomGenerator.random();
        Faker faker = new Faker(random);

        Message message;

        DateAdapter expiresStart = new JodaTimeDateAdapter("2017-10-1");
        DateAdapter expiresEnd = new JodaTimeDateAdapter("2020-1-1");
        DateAdapter expires;

        DateAdapter createdStart = new JodaTimeDateAdapter("2016-7-1");
        DateAdapter createdEnd = new JodaTimeDateAdapter("2019-7-1");
        DateAdapter created;

        TimeAdapter visibilityStart;
        TimeAdapter visibilityEnd;

        // Coordonnées du centre de Québec
        Location baseLocation = new Location(46.816667, -71.216667);
        Location location;

        for (int i = 0; i < numberOfEntries; i++) {
            String title = faker.lorem().sentence();
            String body = faker.lorem().paragraph();

            created = new JodaTimeDateAdapter(faker.date().between(createdStart.toDate(), createdEnd.toDate()));
            do {
                expires = new JodaTimeDateAdapter(faker.date().between(expiresStart.toDate(), expiresEnd.toDate()));
            } while (expires.before(created));

            visibilityStart = new JodaTimeTimeAdapter(Math.round(random.nextDouble() * 22) + ":00");
            do {
                visibilityEnd = new JodaTimeTimeAdapter(Math.round(random.nextDouble() * 23) + ":00");
            } while (visibilityEnd.equals(visibilityStart));


            // 65 KM autour de québec
            location = baseLocation.add(
                    Distance.fromMeters(faker.number().numberBetween(0, 65000)),
                    Distance.fromMeters(faker.number().numberBetween(0, 65000))
            );


            if (random.nextBoolean()) {
                message = new Message(title, body, expires, location, new VisibilityPeriod(visibilityStart, visibilityEnd));
            } else {
                message = new Message(title, body, expires, location);
            }
            message.changeCreated(created);

            messageRepository.save(message);
        }
    }

}
