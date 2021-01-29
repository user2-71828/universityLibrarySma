package biad.module;

import biad.module.agents.Librarian;
import biad.module.agents.Student;
import biad.module.beans.Book;
import biad.module.beans.LibrarianType;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

public class Main {

    ContainerController mainContainer, libraryContainer, subscribersContainer;
    private AgentController librarianAid,librarianClerk;
    public static void main(String[] args) throws StaleProxyException {
        new Main();
    }

    public Main() throws StaleProxyException {
        //Both the list of subscribers and book catalogue is shared between the clerk and the aid
        List<Student> subscribers = new ArrayList<>();
        List<Book> catalogue = new ArrayList<>();

        mainContainer = new MainContainer().getContainer();
        libraryContainer = new LibraryContainer().getContainer();
        subscribersContainer = new SubscribersContainer().getContainer();
        /**
         * The args passed to both agents will be accessed in the setup method via <code>getArguments()</code> method;
         * */
        Object [] aidArgs = {
                /**
                 * args[0]
                 * */
                subscribers,
                /**
                 * args[1]
                 * */
                catalogue,
                /**
                 * args[2]
                 * */
                LibrarianType.Aid
        };
        Object [] clerkArgs = {
                /**
                 * args[0]
                 * */
                subscribers,
                /**
                 * args[1]
                 * */
                catalogue,
                /**
                 * args[2]
                 * */
                LibrarianType.Clerk
        };
        librarianAid = libraryContainer.createNewAgent("LibrarianAid", Librarian.class.getName(),aidArgs);
        librarianClerk = libraryContainer.createNewAgent("LibrarianClerk", Librarian.class.getName(),clerkArgs);

        librarianAid.start();
        librarianClerk.start();

    }
}
