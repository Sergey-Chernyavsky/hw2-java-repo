package ru.pflb;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException, DummyException, IOException {

        Connection connection = new ConnectionImpl();
        connection.start();
        Session session= connection.createSession(true);
        Destination destination = session.createDestination("queueName");
        Producer producer = session.createProducer(destination);

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        ArrayList<String> messages = new ArrayList<String>();
        while (reader.ready()){
            messages.add(reader.readLine());
        }
        reader.close();

        /*************
          Вариант 2*
        *************/

        Iterator<String> itr = messages.iterator();
        while (itr.hasNext()) {
            sleep(2000);
            producer.send(itr.next());
        }

        /**************
          Вариант 2**
        **************/
//        while(true){
//            if(true) {
//                Iterator<String> itr = messages.iterator();
//                while (itr.hasNext()) {
//                    sleep(2000);
//                    producer.send(itr.next());
//                }
//            }else break;
//        }

        session.close();
        connection.close();
    }
}
