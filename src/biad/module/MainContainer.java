package biad.module;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.ContainerController;

public class MainContainer {
    private ContainerController container;

    public MainContainer() {


        Runtime runtime = Runtime.instance();

        Properties extendedProperties = new ExtendedProperties();

        extendedProperties.setProperty("gui", "true");
        extendedProperties.setProperty("port", "12345");
        ProfileImpl profile = new ProfileImpl(extendedProperties);

        container = runtime.createMainContainer(profile);
    }

    public ContainerController getContainer() {
        return container;
    }
}
