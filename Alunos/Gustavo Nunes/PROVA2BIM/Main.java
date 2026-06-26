import view.MainFrame;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        try {

            new MainFrame();

        } catch (Exception exception) {

            exception.printStackTrace();

        }

    }

}