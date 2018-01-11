import java.util.*;

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
                    safes.add(new Safe());
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
        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(rooms[i][j].getType() == 5){
                    story_counter++;
                }
                return !(i > 0 && rooms[i-1][j].getType() == 0 && i < width && rooms[i+1][j].getType() == 0 && j > 0 && rooms[i][j-1].getType() == 0 && j < height && rooms[i][j+1].getType() == 0);
            }
        }
        return story_counter >= 3;
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
        System.out.println(s);
    }

    private Main(){
        ini();
        while (!isMapOk())
            ini();
        Scanner s = new Scanner(System.in);

        if (false)
            rooms[0][0].getEvent().execute();

        while (!game_over)
        {
            int x, y;
            String input;
            printRooms(player);

            do {
                System.out.print("Wohin möchtest du gehen? ");

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
            } else if(input.contains("vor")){
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

            rooms[player.getPos_x()][player.getPos_y()].getEvent().execute();
        }
    }

    public static void main(String[] args){
        new Main();
    }
}
