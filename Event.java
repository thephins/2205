import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Event {
    private boolean isVoid;
    private int room_type;
    private Player player;
    private boolean executed;
    private String safeID;

    Event(int room_type, Player player){
        this.isVoid = room_type == 0;
        this.room_type = room_type;
        this.player = player;
    }

    public boolean getExecuted()
    {
        return executed;
    }

    public void forceExecute(boolean b)
    {
        executed = b;
    }

    void execute(){
        if(!isVoid){
            switch (room_type){
                case 1:
                    safe();
                    break;
                case 2:
                    if(!executed) fight();
                    break;
                case 3:
                    passive();
                    break;
                case 4:
                    if(!executed) treasure();
                    break;
                case 5:
                    if(!executed) story();
                    break;
                case 6:
                    shop();
                    break;
                case 7:
                    if(!executed) secret();
                    break;
            }
            executed = true;
        }
    }

    void charMinigame(){

    }

    void fight(){
        int enemyType = ThreadLocalRandom.current().nextInt(0,100);
        if(enemyType >= 0 && enemyType <= 70){//Typhone
            printText("ACHTUNG! Du befindest dich in einem Raum mit Typhone!");
            for (int i = 0; i < 3;){
                char key = (char) ThreadLocalRandom.current().nextInt(97,123);
                char capKey = (char) (key - 32);

                printText("Drücke schnell " + key);
                try {
                    long t1 = System.currentTimeMillis();
                    char input = (char) RawConsoleInput.read(true);
                    if(input == key || input == capKey){
                        long t2 = System.currentTimeMillis();
                        if((t2 - t1) < 2000){
                            i++;
                            printText("Du konntest dem Typhon schaden!");
                        } else {
                            player.setHealth(player.getHealth() - 15);
                            printText("Typhon hat dich attackiert!");
                        }
                    } else {
                        player.setHealth(player.getHealth() - 15);
                        printText("Typhon hat dich attackiert!");
                    }
                    RawConsoleInput.resetConsoleMode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            printText("Du hast den Typhon getötet!");
        } else if(enemyType > 70 && enemyType <= 90){//Typhone mit Raumanzug
            printText("ACHTUNG! Du befindest dich in einem Raum mit einem Typhon mit Raumanzug!");
            if(player.isWeapon()){
                int number = ThreadLocalRandom.current().nextInt(1000,100000);
                printText("Merke dir folgenden Deaktivierungscode: " + number);
                printText("");
                char key = (char) ThreadLocalRandom.current().nextInt(97,123);
                char capKey = (char) (key - 32);

                printText("Drücke schnell " + key);
                try {
                    long t1 = System.currentTimeMillis();
                    char input = (char) RawConsoleInput.read(true);
                    if(input == key || input == capKey){
                        long t2 = System.currentTimeMillis();
                        if((t2 - t1) < 1500){
                            printText("Du konntest dem Typhon schaden!");
                            printText("Mit dem Deaktivierungscode des Raumanzuges kannst du den Typhon entgültig eliminieren");
                            cls();
                            printText("Bitte hier eingeben: ");
                            if(new Scanner(System.in).nextLine().equals(String.valueOf(number))){
                                printText("Du hast den Typhon getötet!");
                            }
                        } else {
                            player.setHealth(player.getHealth() - 25);
                            printText("Typhon hat dich attackiert!");
                        }
                    } else {
                        player.setHealth(player.getHealth() - 25);
                        printText("Typhon hat dich attackiert!");
                    }
                    RawConsoleInput.resetConsoleMode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {//Typhone mit Waffe
            printText("Hier befindet sich ein Typhon mit Waffe. Möchtest du fliehen oder kämpfen?");
            Scanner s = new Scanner(System.in);
            String input = s.nextLine().toLowerCase(Locale.GERMAN);
            if(input.contains("kämpfen")){
                int chances = ThreadLocalRandom.current().nextInt(0,5) + (player.isWeapon() ? 30 : 0) + (player.getLuck() / 2) + (player.isArmor() ? 10 : 0) + player.getHealth() / 4;
                if(chances >= 75){
                    printText("Deine Chancen standen gut. Du konntest den Typhon erfolgreich eliminieren.");
                    int money_found = ThreadLocalRandom.current().nextInt(0,1000);
                    printText("Der Typhon hatte " + money_found + "$, eine Sauerstoffflasche und eine Waffe.");
                    System.out.print("Möchtest du die Gegenstände mitnehmen? ");
                    String input_objects = s.nextLine().toLowerCase(Locale.GERMAN);
                    if(input_objects.contains("ja")){
                        player.setMoney(player.getMoney() + money_found);
                        player.setWeapon(true);
                        player.setLuck((player.getLuck() < 10 ? 11 : player.getLuck()) - 10);
                    } else if(input_objects.contains("nein")){
                        printText("Ok.");
                    } else {
                        printText("Das habe ich nicht verstanden.");
                    }
                } else {
                    printText("Deine Chancen standen nicht gut. Der Typhon hat dich getötet.");
                    Main.game_over = true;
                }
            }
        }
    }
    void shop(){
        printText("Vor dir befindet sich ein Automat.");
        printText("Es gibt hier noch Sauerstoffflaschen, Snacks, Medikamente, Waffen, Glücksbringer und Rüstungen.");
        System.out.print("Du hast aktuell " + player.getMoney() + "$. Möchtest du etwas kaufen? ");
        Scanner s = new Scanner(System.in);
        if(s.nextLine().toLowerCase(Locale.GERMAN).contains("ja")){
            System.out.print("Was darf es denn sein? ");
            String input = s.nextLine().toLowerCase(Locale.GERMAN);
            String nem = "Nicht genug Geld!";
            if(input.contains("sauerstoff")){
                if(player.getMoney() >= 10) {
                    player.setMoney(player.getMoney() - 10);
                    player.setOxygen(player.getOxygen() + 100);
                } else {
                    printText(nem);
                }
            } else if(input.contains("snack")){
                if(player.getMoney() >= 30){
                    player.setMoney(player.getMoney() - 30);
                    if(player.getHealth() <= 100) {
                        player.setHealth(player.getHealth() + 15);
                    }
                } else {
                    printText(nem);
                }
            } else if(input.contains("med")){
                if(player.getMoney() >= 70){
                    player.setMoney(player.getMoney() - 70);
                    if(player.getHealth() <= 100) {
                        player.setHealth(player.getHealth() + 48);
                    }
                } else {
                    printText(nem);
                }
            } else if(input.contains("waffe")){
                if (!player.isWeapon()){
                    if(player.getMoney() >= 200){
                        player.setMoney(player.getMoney() - 200);
                        player.setWeapon(true);
                    } else {
                        printText(nem);
                    }
                } else {
                    printText("Du hast schon eine Waffe!");
                }
            } else if(input.contains("glück")){
                if(player.getMoney() >= 800){
                    player.setMoney(player.getMoney() - 800);
                    player.setLuck(player.getLuck() + 25);
                } else {
                    printText(nem);
                }
            } else if(input.contains("rüstung")){
                if(!player.isArmor()) {
                    if (player.getMoney() >= 1000) {
                        player.setMoney(player.getMoney() - 1000);
                        player.setArmor(true);
                    } else {
                        printText(nem);
                    }
                } else {
                    printText("Du hast schon eine Rüstung!");
                }
            }
        } else {
            printText("Ok.");
        }
    }
    void safe(){
        if(safeID != null) {
            for (Safe safe : Main.safes) {
                if (!safe.isOpen() && !safe.isGenerated()) {
                    safe.setGenerated(true);
                    safeID = safe.getId();
                    _safe(safe);
                    break;
                }
            }
        } else {
            for(Safe safe : Main.safes){
                if(safe.getId().equals(safeID)){
                    _safe(safe);
                }
                break;
            }
        }
    }

    void _safe(Safe safe){
        printText("Hier befindet sich ein Safe mit der ID \"" + safe.getId() + "\"\nMöchtest du einen Code eingeben?");
        Scanner s = new Scanner(System.in);
        String input = s.nextLine().toLowerCase(Locale.GERMAN);
        if (input.contains("ja")) {
            printText("Code: ", 30, false);
            if (Integer.parseInt(s.nextLine()) == safe.getCode()) {
                printText("Du hast den Safe geöffnet!");
                printText("Du hast " + safe.getMoney() + "$, " + safe.getOxygen() + " an Sauerstoff und " + safe.getMeds() + " Medikament(e) gefunden.");
                player.setMoney(player.getMoney() + safe.getMoney());
                player.setOxygen(player.getOxygen() + safe.getOxygen());
                player.setHealth(player.getHealth() + safe.getMeds() * 48);
                if (player.getHealth() > 100) {
                    player.setHealth(100);
                }
                safe.setOpen(true);
            } else {
                printText("Der Code ist leider falsch!");
            }
        } else if (input.contains("nein")) {
            printText("Ok.");
        }
    }

    void treasure(){
        // 1. bit = oxygen
        // 2. bit = money
        // 3. bit = weapon
        // 4. bit = Lucky charm
        byte treasure = 0;
        treasure |= ThreadLocalRandom.current().nextInt(0, 4) == 0 ? 1 : 0;
        treasure |= ThreadLocalRandom.current().nextInt(0, 4) == 0 ? 2 : 0;
        treasure |= ThreadLocalRandom.current().nextInt(0, 4) == 0 ? 4 : 0;
        treasure |= ThreadLocalRandom.current().nextInt(0, 4) == 0 ? 8 : 0;

        if (treasure == 0)
        {
            treasure |= (byte) Math.pow(2,
                    ThreadLocalRandom.current().nextInt(0, 3));
        }

        int chanceOxygen = ThreadLocalRandom.current().nextInt(0,100);
        int chanceMoney = ThreadLocalRandom.current().nextInt(0,100);
        int chanceWeapon = ThreadLocalRandom.current().nextInt(0,100);
        int chanceLuckyCharm = ThreadLocalRandom.current().nextInt(0,100);

        if((treasure & 1) != 0 && chanceOxygen >= 0 && chanceOxygen <= 80){
            printText("Du hast hier Sauerstoff gefunden!");
            player.setOxygen(player.getOxygen() + 1000);
        }

        if((treasure & 2) != 0 && chanceMoney >= 0 && chanceMoney <= 75){
            int money = 20 * player.getLuck();
            printText("Du hast hier " + money + "$ gefunden!");
            player.setMoney(player.getMoney() + money);
        }

        if((treasure & 4) != 0 && (chanceWeapon >= 0 && chanceWeapon <= 50) && !player.isWeapon()){
            printText("Du hast hier eine Waffe gefunden!");
            player.setWeapon(true);
        }

        if((treasure & 8) != 0 && chanceLuckyCharm >= 0 && chanceLuckyCharm <= 35){
            printText("Du hast hier einen Glücksbringer gefunden!");
            player.setLuck(player.getLuck() + 25);
        }
    }
    void story(){
        int chapter = player.getStory_chapter();
        player.setStory_chapter(chapter + 1);
        Scanner s = new Scanner(System.in);
        if(chapter == 0){
            printText("Du wachst aus einem Hyperschlaf auf.\n" +
                    "Du befindest dich auf der Talos-1-Spacestation.\n" +
                    "Alle deine Team Kollegen befinden sich noch im Hyperschlaf. Aber wieso...");
            boolean chapter_complete = false;
            while (!chapter_complete){
                System.out.print("Was möchtest du tun/wissen? ");
                String input = s.nextLine().toLowerCase(Locale.GERMAN);
                if(input.contains("ich")){
                    printText("Du arbeitest hier mit den besten Wissenschaftlern der Erde zusammen, du bist einer von ihnen, ein hochbegabter Physiker und Techniker. Dein Name: Morgan.\n");
                } else if(input.contains("mission")){
                    printText("Die Mission war es durch einen Hyperschlaf mehrere Jahre in die Zukunft zu reisen ohne zu altern um einen langwierigen Forschungsprozess zu überspringen.\n" +
                            "Während des Schlafens soll ein Programm den Forschungsprozess automatisieren.");
                } else if(input.contains("forschung")){
                    printText("Die Erforschung einer neuen Lebensform, die Typhon. Normalerweise dauert die Analyse des Gewebes einer Lebensform nur ein bis zwei Tage doch das Gewebe der Typhon ist viel komplexer und erfordert daher mehrere Fachgebiete zum Analysieren was die Forschung auf mehrere Jahre strecken könnte. Viel zu viele Jahre... …dafür war keine Zeit.");
                } else if(input.contains("schlaf")){
                    printText("Du schaust dir deine Hyperschlaf-Kapsel genauer an. Ein Fehler! Du bist 100 Jahre zu früh aufgewacht! Geplant war das Jahr 2205 doch du bist im Jahr 2105 aufgewacht. 2032 war das Jahr in dem sich alle in den Hyperschlaf gestürzt haben.");
                } else if(input.contains("gehe") && input.contains("schlaf")){
                    printText("Funktioniert nicht! Die Hyperschlaf_Kapsel ist defekt. Wenn du diese Kapsel nicht zum Laufen bekommst oder eine neue funktionierende Kapsel findest könnte es dein Tod bedeuten, da du unmöglich 100 Jahre leben kannst.");
                } else if(input.contains("aufwecken")){
                    printText("Dir ist der Fehler Unbekannt. Du möchtest nicht riskieren das der gleiche Fehler auch bei deinen Team Kollegen auftritt und du jemanden mit in deine Situation hinein ziehst.");
                } else if(input.contains("raus")){
                    printText("Du entscheidest dich aus dem Hyperschlaf-Raum raus zugehen. Du öffnest die Tür und kannst deinen Augen nicht glauben. Alles verwüstet überall schwarze Spuren an den Wänden wie du sie eigentlich nur aus der Typhon Forschung kennst... ...sind die Typhon ausgebrochen? Auf einem Informations-Bildschirm erkennst du das es ebenfalls das überall   \n" +
                            "auf der Talos-1 Sauerstoff Lecks gibt du ziehst dir einen Raumanzug an dessen Sauerstoff-Versorgung noch 75% beträgt. Ebenfalls nimmst du vorerst als temporäre Bewaffnung eine Rohrzange die auf dem Boden zwischen den Trümmern herum lag.\n" +
                            "Der Raumanzug gibt ebenfalls Informationen über deinen Gesundheitlichen Status aus:\n" +
                            "Gesundheit = 100% | Krankheiten = Keine\n\n" +
                            "Schwarze Flächen an der Wand, alles zertrümmert, Sauerstoff Lecks. Hier ist nicht wirklich von Interesse für dich, das einzige was dir Sorgen macht sind diese Schwarzen Flecken.\n" +
                            "Diese Flecken sehen dem Gewebe der Typhons sehr ähnlich. Überall wo die Typhen hingehen hinterlassen sie so welche Flecken. Es macht dir Sorgen das sie hier im zerstörten Raum auch aufgetaucht sind.\n" +
                            "Die Typhons sind tatsächlich ausgebrochen. Hinter dir und im ganzen Raum schließen sich die Türen wegen einer Warnmeldung, die Technologie auf der Raumstation scheint auch schon beschädigt zu sein.");
                    chapter_complete = true;
                } else {
                    printText("Das geht leider nicht.. Du kannst aber mehr über dich, deine Mission oder der Hyperschlaf-Kapsel erfahren.");
                }
            }
        } else if(chapter == 1){
            printText("Nachdem du dich durch verschiedenen Räumen der Talos-1 durch gekämpft hast, triffst du auf einen Raum der durch ein Kraftfeld geschützt ist. Zum Glück, verfügst du über einen Sicherheitscode, der Kraftfelder ausschalten kann.\n" +
                    "Das Kraftfeld ist so etwas wie ein tödliches und dichtes Laserfeld das nichts hindurch lässt.\n" +
                    "Es wundert dich das du so ein Feld hier vorfindest, normalerweise können die nur manuell von Hand aufgestellt werden, und nicht durch das Sicherheitssystem.\n" +
                    "Es ist unmöglich durch dieses Feld irgendetwas zu erkennen.");
            boolean chapter_complete = false;
            while(!chapter_complete){
                System.out.print("Was möchtest du tun/wissen? ");
                String input = s.nextLine().toLowerCase(Locale.GERMAN);
                if(input.contains("code")){
                    printText("Der Code lautet 2-4-1-6.");
                } else if(input.contains("2416") || input.contains("2-4-1-6") || input.contains("2 4 1 6")){
                    printText("Du gibst den Code ein. Das Kraftfeld deaktiviert sich. Du kannst jetzt in den Raum gehen");
                } else if(input.contains("gehe") || input.contains("raum")){
                    printText("Du gehst in den Raum. Eine Person! Sie scheint sich selbst in einer Energie-Draht-Falle gefangen zu haben. Das einzige was sie hat ist ein Laptop der vor ihr liegt und eine freie Hand. " +
                            "Der Raum scheint noch ganz unberührt von der Typhon Katastrophe zu sein. Es befindet sich ebenfalls ein Terminal im Raum... Ob sich damit die Identität der Person herausfinden lässt?");
                    boolean room_complete = false;
                    boolean has_informations = false;
                    while (!room_complete){
                        System.out.print("Was möchtest du tun/wissen? ");
                        String input_room = s.nextLine().toLowerCase(Locale.GERMAN);
                        if(input_room.contains("person")){
                            printText("Anhand der Uniform lässt sich erkennen das sie zu den Volontären auf der Talos-1 gehört.\n" +
                                    "Sie scheint ansprechbar zu sein und anhand eines Terminals und ihrer Volontär ID, \n" +
                                    "die auf ihrem Anzug erkennbar ist, ist es möglich mehr über sie heraus zu finden.");
                        } else if(input.contains("volontär")){
                            printText("Volontäre, sind Freiwillige die ihren Körper für die Versuche und Experimente auf der Talos-1 zur Verfügung gestellt haben, ...also so etwas wie Laborratten.\n" +
                                    "Meistens handelt es sich um Strafgefangene die ihr Leben sowieso lebenslänglich im Gefängnis verbracht hätten.");
                        } else if(input.contains("id") || input.contains("genau")){
                            printText("Auf der Uniform steht die ID: V-010655-37");
                        } else if(input.contains("terminal") && input.contains("V-010655-37")){
                            printText("Name: Olivia\n" +
                                    "Nachname: Colomar\n" +
                                    "Straftat: Versuchter Hackerangriff auf die größte Bank der Welt.\n" +
                                    "\t\t13 weitere gelungene Hackerangriffe auf verschieden große Firmen.\n" +
                                    "\t\tDiebstahl von 50 Millionen Dollar.\n" +
                                    "\t\tDatendiebstahl an mehreren Millionen Personen.\n" +
                                    "Warnung:  keine Technischen Geräte im Radius von 10m vom Testsubjekt aufbewahren.");
                            has_informations = true;
                        } else if(input.contains("reden")){
                            if(has_informations){
                                printText("Olivia: \t„Ich schätze du weißt schon, anhand meiner Akten, wer ich bin.\n" +
                                        "\t\tIch wurde ebenfalls in den Hyperschlaf versetzt. Du wunderst dich, wieso ich auch schon wach bin.\nBevor man mich Schlafen lies hatte ich genug Zeit mich in ein anderes Terminal zu hacken und meine Schlafdauer zu verkürzen.\nNur leider ist mein Umgang mit Waffen miserabel und ich habe mich in\n" +
                                        "\t\teiner dieser Energie-Draht-Fallen selbst gefangen. Ich hatte eigentlich vor, mit einem Shuttle zu fliehen, aber bemerkte schon früh, dass ich die Sicherheitssystem-Software und das Kommunikationssystem\n" +
                                        "\t\tmit meinen Softwaremanipulationen zerschossen habe und damit eure Forschungssubjekte befreit habe. Ich habe dich ebenfalls aus dem Hyperschlaf geweckt und dir die richtigen Türen geöffnet damit du zu " +
                                        "\t\tmir kommst und mich befreist. Ich habe den Doktor dieser Raumstation dabei beobachtet mit welchen Code er die Tür zur Krankenstation öffnet und soweit ich weiß, ist der Operationstisch fähig einen Hyperschlaf\n" +
                                        "\t\tdurchzuführen. Wenn du mich befreist und hilfst zum Shuttle zu kommen und zu fliehen verrate ich dir den Code.“");
                                System.out.print("Olivia Colomar befreien? ");
                                String input_room_decision = s.nextLine().toLowerCase(Locale.GERMAN);
                                if(input_room_decision.contains("ja")){
                                    printText("Du befreist Olivia. Sie bedankt sich sehr und sagt das du es nicht bereuen wirst.\n" +
                                            "Sie öffnet auf ihrem Laptop einen Karte und berechnet den kürzesten Weg zum Shuttle und öffnet dir die richtigen Türen.\n");
                                } else if(input_room_decision.contains("nein")){
                                    printText("Du reißt Olivia ihr Laptop aus der Hand, wirfst ihn gegen eine Wand, nachdem du dir eine Karte über die Raumstation heruntergeladen hast und lässt sie am Boden gefesselt liegen.\n" +
                                            "\tDein Neues Ziel: Das Shuttle finden um zur Erde zu fliegen und darüber zu Informieren was hier vor sich geht. Und noch während du den Raum verlässt, hat sie dich auf Spanisch auf 10 verschiedene Arten beleidigt.");
                                }
                            } else {
                                printText("Vielleicht solltest du dich lieber erst über sie Informieren...\nDu weißt nicht, wen du vor dir hast.");
                            }
                        }
                    }
                }
            }

        }
    }
    void secret() {
        printText("Du hast einen geheimen Raum gefunden!");
    }
    void passive(){
        printText("Du bist in einem scheinbar leeren Raum.");
        int chanceSafe = ThreadLocalRandom.current().nextInt(0,2);
        if(safeID != null) {
            if (chanceSafe == 1) {
                for (Safe safe : Main.safes) {
                    if (!safe.isCode_found()) {
                        safe.setCode_found(true);
                        _passive(safe);
                        break;
                    }
                }
            }
        } else {
            for (Safe safe : Main.safes){
                _passive(safe);
                break;
            }
        }
        int chance = ThreadLocalRandom.current().nextInt(0,100);
        if(chance == 99){
            printText("Seltsam. Was war das?");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.setLuck(player.getLuck() * 3);
            printText("Dein Glück hat sich erhöht!");
        }
    }

    void _passive(Safe safe){
        printText("An der Wand befindet sich ein Zettel. Möchtest du diesen genauer anschauen?");
        String input = new Scanner(System.in).nextLine().toLowerCase(Locale.GERMAN);
        if (input.contains("ja")) {
            printText("Auf dem Zettel steht:\n\tSafe-ID: " + safe.getId() + "\n\tCode: " + safe.getCode());
        } else if (input.contains("nein")) {
            printText("Ok.");
        }
    }

    public static void cls(){
        for (int i = 0; i < 50; ++i) System.out.println();
    }
    public static void printText(String s){
        printText(s, 30, true);
    }

    public static void printText(String s, int t, boolean newLine){
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            System.out.print(c);
            if(c != ' ') {
                try {
                    Thread.sleep(t);
                } catch (InterruptedException ignored) {}
            }
        }
        if(newLine) System.out.println();
    }
}
