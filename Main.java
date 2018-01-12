import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private final int width = 20;
    private final int height = 20;


    public static boolean game_over = false;
    public static List<Safe> safes = new ArrayList<>();

    Room[][] rooms = new Room[width+1][height+1];

    private Player player = new Player();

    private void ini(){
        Random random = new Random();
        //0 = wall, 1 = safe, 2 = fight, 3 = passive, 4 = treasure, 5 = story, 6 = shop, 7 = secret
        for(int i = 0; i <= width; i++){
            for(int j = 0; j <= height; j++){
                int r = random.nextInt(1001);
                int type;
                if (r >= 0 && r <= 260){
                    type = 0;
                } else if(r <= 460){
                    type = 1;
                } else if(r <= 660){
                    type = 2;
                } else if(r <= 860){
                    type = 3;
                } else if(r <= 935){
                    type = 4;
                } else if(r <= 975){
                    type = 5;
                } else if(r <= 995){
                    type = 6;
                } else {
                    type = 7;
                }
                rooms[i][j] = new Room(type, player);
            }
        }
        rooms[0][0] = new Room(5, player);
    }

    private boolean isMapOk(){
        int story_counter = 0;
        int posX = 0;
        int posY = 0;
        List<String> ids = new ArrayList<>();
        int i = 0;
        while (story_counter < 4){
            if((rooms[posX][posY].getType() == 5) && !(ids.contains(rooms[posX][posY].getId()))) {
                ids.add(rooms[posX][posY].getId());
                story_counter++;
            }
            int r = ThreadLocalRandom.current().nextInt(0,4);
            if(posY < height && rooms[posX][posY+1].getName().equals("Tür") && r == 0){
                posY++;
            } else if(posX > 0 && rooms[posX-1][posY].getName().equals("Tür") && r == 1){
                posX--;
            } else if(posX < width && rooms[posX+1][posY].getName().equals("Tür") && r == 2){
                posX++;
            } else if(posY > 0 && rooms[posX][posY-1].getName().equals("Tür") && r == 3){
                posY--;
            }
            if(i > 1000) return false;
            i++;
        }
        return true;
    }

    private void printRooms(Player p){
        int x = p.getPos_x();
        int y = p.getPos_y();
        System.out.println("Vor dir befindet sich eine " + (y < height ? rooms[x][y+1].getName() : "Wand") +
                ", Links eine " + (x > 0 ? rooms[x-1][y].getName() : "Wand") +
                ", Rechts eine " + (x < width ? rooms[x+1][y].getName() : "Wand") +
                " und hinter dir eine " + (y > 0 ? rooms[x][y-1].getName() : "Wand") + ".");
    }

    private boolean check(int x, int y) {
        return (x >= 0 && x <= width) && (y >= 0 && y <= height) && rooms[x][y].getType() != 0;
    }

    private void movementError(){
        Random r = new Random();
        String s = null;
        switch (r.nextInt(7)){
            case 0:
                s = "Autsch! Du bist gegen eine Wand gelaufen!";
                break;
            case 1:
                s = "Nein! Das geht leider nicht..";
                break;
            case 2:
                s = "Hier geht es leider nicht lang..";
                break;
            case 3:
                s = "Schade! Ich dachte schon hier kann man lang gehen.";
                break;
            case 4:
                s = "Hmm.. Hier scheint nichts zu sein.";
                break;
            case 5:
                s = "Better luck next time!";
                break;
            case 6:
                s = "Nur eine normale Wand.";
                break;
        }
        Event.printText(s);
    }

    private Main(){
        ini();
        while (!isMapOk())
            ini();
        Scanner s = new Scanner(System.in);

        rooms[0][0].getEvent().execute();

        Event.printText("Du kannst dich ab sofort frei auf der Map bewegen. Und Übrigens: Wände sind nicht immer Wände ;)");

        while (!game_over)
        {

            if(player.getHealth() <= 0 || player.getOxygen() <= 0){
                game_over = true;
                break;
            }

            int x, y;
            String input;
            Event.cls();
            System.out.println("Aktueller Sauerstoffgehalt: " + player.getOxygen());
            printRooms(player);

            do {
                Event.printText("Wohin möchtest du gehen? ", 30, false);

                input = s.nextLine().toLowerCase(Locale.GERMAN);
                x = player.getPos_x();
                y = player.getPos_y();

                if (input.matches(Debug.DEBUG_PREFIX + ".*"))
                {
                    String err = Debug.resolve_debug(input.substring(4), player, rooms);
                    if (err != null)
                        System.out.println("DEBUG&" + err + ";");
                }
            } while (input.matches(Debug.DEBUG_PREFIX + ".*"));

            if(input.contains("links")){
                if(check(x-1, y)) {
                    player.setPos_x(x - 1);
                } else {
                    movementError();
                }
            } else if(input.contains("rechts")){
                if(check(x+1,y)) {
                    player.setPos_x(x + 1);
                } else {
                    movementError();
                }
            } else if(input.contains("vor") || input.contains("geradeaus")){
                if(check(x, y+1)) {
                    player.setPos_y(y + 1);
                } else {
                    movementError();
                }
            } else if(input.contains("hinter") || input.contains("zurück")){
                if(check(x, y-1)) {
                    player.setPos_y(y - 1);
                } else {
                    movementError();
                }
            } else {
                System.out.println("Das habe ich nicht verstanden..");
            }
            player.setOxygen(player.getOxygen() - 10);
            rooms[player.getPos_x()][player.getPos_y()].getEvent().execute();
        }
    }

    public static void main(String[] args){
        new Main();
    }
}
