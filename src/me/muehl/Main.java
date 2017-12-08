package me.muehl;

import java.util.Scanner;

public class Main {

    private String PlayerName;
    private int sec = 30;

    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        System.out.println(" 222222222222222     222222222222222         000000000     555555555555555555 \n" +
                           "2:::::::::::::::22  2:::::::::::::::22     00:::::::::00   5::::::::::::::::5\n" +
                           "2::::::222222:::::2 2::::::222222:::::2  00:::::::::::::00 5::::::::::::::::5\n" +
                           "2222222     2:::::2 2222222     2:::::2 0:::::::000:::::::05:::::555555555555\n" +
                           "            2:::::2             2:::::2 0::::::0   0::::::05:::::5\n" +
                           "            2:::::2             2:::::2 0:::::0     0:::::05:::::5\n" +
                           "         2222::::2           2222::::2  0:::::0     0:::::05:::::5555555555\n" +
                           "    22222::::::22       22222::::::22   0:::::0 000 0:::::05:::::::::::::::5\n" +
                           "  22::::::::222       22::::::::222     0:::::0 000 0:::::0555555555555:::::5\n" +
                           " 2:::::22222         2:::::22222        0:::::0     0:::::0            5:::::5\n" +
                           "2:::::2             2:::::2             0:::::0     0:::::0            5:::::5\n" +
                           "2:::::2             2:::::2             0::::::0   0::::::05555555     5:::::5\n" +
                           "2:::::2       2222222:::::2       2222220:::::::000:::::::05::::::55555::::::5\n" +
                           "2::::::2222222:::::22::::::2222222:::::2 00:::::::::::::00  55:::::::::::::55\n" +
                           "2::::::::::::::::::22::::::::::::::::::2   00:::::::::00      55:::::::::55\n" +
                           "2222222222222222222222222222222222222222     000000000          555555555\n");
        printText("Wir schreiben das Jahr 2205.");
        cls();
        printText("Sie befinden sich gerade in dem Raumschiff Delta 2, dass auf dem Weg zu dem Planeten Exodus ist, wo Sie Operation 2634 ausführen werden.");
        cls();
        printText("Bitte geben Sie ihren Namen ein: ");
        Scanner s = new Scanner(System.in);
        PlayerName = s.nextLine();
        printText("Vielen Dank " + PlayerName + ".");
        cls();
        printText("An Board befindet sich Co-Pilot John.");
        cls();
        printText("Aufgrund eines technischen Defektes erhöht sich das Risiko eines Absturzes. Wir wünschen Ihnen dennoch einen Guten Flug!");
        cls();
        printText("Gut. Nun zu Ihrer Mission:");
        printText("Sie werden in Kürze auf dem Planeten Exodus landen wo ihre Aufgabe darin bestehen wird Bodenproben zu entnehmen und diese ansc)=(\\§\\&(\"54\" +");
        printText("wiR stell2n aktu2ell 3inene verherenden technischen Defekt f4st b4t2e begeben Si8 sIch schnellsstm0glich in ienen Rauman7ug!", 100);
        cls();
        Thread thread = new Thread(() -> {
            while (sec != 0) {
                sec--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
            System.out.println("Sie sind erstickt.");
            System.out.println("Game Over!");
            System.exit(0);
        });
        thread.start();
        System.out.println("Luft reicht noch für 30 Sekunden!");
        printText("Ich sehe eine Rote Tür, eine Treppe die nach unten führt und einen Durchgang.", 100);
        String inputRoom = "";
        while (!inputRoom.contains("rot")) {
            printText("Wohin möchten Sie gehen?", 100);
            inputRoom = s.nextLine().toLowerCase();
            if (inputRoom.contains("rot")) {
                thread.stop();
                printText("Sie haben hier einen Raumanzug gefunden.");
            } else if (inputRoom.contains("treppe")) {
                sec += 53;
                printText("Sie haben hier leider nur eine Sauerstoffflasche gefunden. Dies wird ihnen ungefähr Luft für weitere 50 Sekunden geben.", 100);
            } else if (inputRoom.contains("durchgang")) {
                printText("Hier befindet sich leider nichts besonderes..", 100);
            } else {
                printText("Wie bitte?", 100);
            }
        }
        cls();
        printText("Sie sollten jetzt versuchen Kontakt zu John herzustellen.");
        printText("Was möchten Sie ihm sagen?");
        String inputMsgJohn = s.nextLine();
        printText("Die Nachricht wird jetzt übermittelt..");
        cls();
        printText(PlayerName + ": " + inputMsgJohn);
        printText("John: Ich werde es wahrscheinlich nicht überleben. Bin nach dem Defekt aus der Luftschleuse geflogen. Der Sauerstoff reicht nur noch für eine Minute. Ich wünsche dir Viel Glück. Vielleicht kannst du versuchen in die verlassene Raumstation in der Nähe zu gelangen.");
        cls();
        printText("Neues Ziel: Zur Raumstation gelangen");
        cls();
        printText("Sie können mit den integrierten Düsen ihres Raumanzuges versuchen, zur Station zu gelangen oder hier auf Hilfe warten.");
        printText("Was möchten Sie tun?");
        String inputNextStep = s.nextLine().toLowerCase();
        cls();
        if (inputNextStep.contains("düsen") || inputNextStep.contains("anzug") || inputNextStep.contains("station")){
            printText("Mithilfe des Notstromaggregators konnte ich die Luken problemlos öffnen und mich auf den Weg zur Station machen.");
            printText("Dies war jedoch schwieriger als gedacht da herumfliegende Satelliten- und andere Teile eine große Gefahr darstellten.");
            printText("Ein Metallteil verfehlte mich jedoch nur knapp und traf das Raumschiff welches daraufhin explodierte.");
            printText("Die Kraft schleuderte mich in Richtung der Station und ich konnte gerade noch rechtzeitig mithilfe der Düsen abbremsen.");
            cls();
            printText("Gute Entscheidung! Du hast es jetzt in die Raumstation geschafft.");
            //new Raumstation();
        } else if(inputNextStep.contains("warten") || inputNextStep.contains("bleiben")) {
            printText("Tag 1: Ich habe versucht das defekte Funkgerät zu reparieren.");
            printText("Tag 2: Ich konnte das Funkgerät erfolgreich reparieren jedoch hat bisher niemand geantwortet.");
            printText("Tag 3: Weiterhin Funkstille..");
            printText("Tag 4: Die Sauerstoffvorräte sind bald aufgebraucht. Ich mache mich entgegen ihrer Empfehlung deswegen auf dem Weg zur Raumstation und stelle fest das der Sauerstoff doch nicht m%$)/&  H!LFE");
            cls();
            System.out.println("Game Over!");
            System.exit(0);
        }
    }

    public static void printText(String s){
        printText(s, 215);
    }

    public static void printText(String s, int t){
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            System.out.print(c);
            if(c != ' ') {
                try {
                    Thread.sleep(t);
                } catch (InterruptedException ignored) {}
            }
        }
        System.out.println();
    }

    public static void cls(){
        for (int i = 0; i < 50; ++i) System.out.println();
    }

}
