package ru.pflb;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException, DummyException {

        Connection connection = new ConnectionImpl();
        connection.start();
        Session session= connection.createSession(true);
        Destination destination = session.createDestination("queueName");
        Producer producer = session.createProducer(destination);

        ArrayList<String> messages = new ArrayList<String>();
        messages.add("Раз");
        messages.add("Два");
        messages.add("Три");

        Iterator<String> itr = messages.iterator();
        while (itr.hasNext()){
            sleep(2000);
            producer.send(itr.next());
        }

        session.close();
        connection.close();
    }
}
