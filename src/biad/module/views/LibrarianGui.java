package biad.module.views;

import biad.module.SubscribersContainer;
import biad.module.agents.Librarian;
import jade.wrapper.ContainerController;

import javax.swing.*;

public abstract class LibrarianGui {
    protected JFrame frame;
    protected Librarian librarian;
    protected ContainerController subscribersContainer;

    public LibrarianGui(Librarian librarian) {
        this.librarian = librarian;

    }

    protected abstract void initialize() ;

    public void setVisible(boolean b) {
        this.frame.setVisible(b);
    }

    public void showAlert(String message) {
        JOptionPane.showMessageDialog(this.frame, message);
    }

    public void showErrorAlert(String message) {
        JOptionPane.showMessageDialog(this.frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
