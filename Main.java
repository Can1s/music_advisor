package advisor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean auth = false;
        boolean tr = true;
        String CLIENT_ID = "j4ll351l51h5515j1l51jnflsfs";
        while (tr) {
            String action = scanner.nextLine();
            if (action.equals("exit")) {
                System.out.println("---GOODBYE!---");
                tr = false;
            } else if (action.equals("auth") && (!auth)) {
                auth = true;
                System.out.println("https://accounts.spotify.com/authorize?" +
                        "client_id=" + CLIENT_ID +
                        "redirect_uri=http://localhost:8080&response_type=code\n" +
                        "---SUCCESS---");
            } else if (auth && !(action.equals("auth"))) {
                switch (action) {
                    case "new":
                        System.out.println("---NEW RELEASES---\n" +
                                "Mountains [Sia, Diplo, Labrinth]\n" +
                                "Runaway [Lil Peep]\n" +
                                "The Greatest Show [Panic! At The Disco]\n" +
                                "All Out Life [Slipknot]");
                        break;
                    case "featured":
                        System.out.println("---FEATURED---\n" +
                                "Mellow Morning\n" +
                                "Wake Up and Smell the Coffee\n" +
                                "Monday Motivation\n" +
                                "Songs to Sing in the Shower");
                        break;
                    case "categories":
                        System.out.println("---CATEGORIES---\n" +
                                "Top Lists\n" +
                                "Pop\n" +
                                "Mood\n" +
                                "Latin");
                        break;
                    case "playlists Mood":
                        System.out.println("---MOOD PLAYLISTS---\n" +
                                "Walk Like A Badass  \n" +
                                "Rage Beats  \n" +
                                "Arab Mood Booster  \n" +
                                "Sunday Stroll");
                        break;
                }
            } else {
                System.out.println("Please, provide access for application.");
            }
        }
    }
}
