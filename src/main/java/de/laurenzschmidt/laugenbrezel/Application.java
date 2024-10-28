package de.laurenzschmidt.laugenbrezel;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        Laugenbrezel application = Laugenbrezel.create();
        application.start();
        Runtime.getRuntime().addShutdownHook(new Thread(application::stop));
    }

}
