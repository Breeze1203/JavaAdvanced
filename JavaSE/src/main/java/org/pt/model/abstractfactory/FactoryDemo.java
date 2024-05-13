package org.pt.model.abstractfactory;

public class FactoryDemo {
    public static void main(String[] args) {
        Application application = FactoryDemo.configureApplication();
        application.paint();
    }

    private static Application configureApplication() {
        Application app=null;
        AbstractFactory factory=null;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            factory = new MacFactory();
        } else {
            factory = new WindowFactory();
        }
        app = new Application(factory);
        return app;
    }

}
