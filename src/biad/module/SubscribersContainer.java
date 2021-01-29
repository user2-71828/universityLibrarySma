package biad.module;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;

public class SubscribersContainer {
    private ContainerController container;

    public SubscribersContainer() {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl(false);
        profile.setParameter(ProfileImpl.MAIN_HOST, "localhost");
        profile.setParameter(ProfileImpl.CONTAINER_NAME, "Subscribers");
        container = runtime.createAgentContainer(profile);

    }

    public ContainerController getContainer() {
        return container;
    }
}
