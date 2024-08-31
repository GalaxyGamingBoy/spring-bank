package xyz.mariosm.bank;

import com.mongodb.client.MongoDatabase;

import java.util.Date;

public class MongodConfig {
    public MongodConfig() {
    }

//    public void setup() {
//        try (TransitionWalker.ReachedState<RunningMongodProcess> running = Mongod.instance().start(Version.Main.PRODUCTION)) {
//            com.mongodb.ServerAddress serverAddress = serverAddress(running.current().getServerAddress());
//            try (MongoClient mongo = MongoClients.create("mongodb://" + serverAddress)) {
//                MongoDatabase db = mongo.getDatabase("test");
//                MongoCollection<Document> col = db.getCollection("testCol");
//                col.insertOne(new Document("testDoc", new Date()));
//            }
//        }
//    }
}
