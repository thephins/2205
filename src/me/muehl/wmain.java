package me.muehl;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class Player {
    int luck = 1,
            hitpoints = 100,
            money = 0,
            health = 100,
            oxygen = 1000;
    boolean weapon = false;
    boolean rüstung = false;
}

class Event {
    ArrayList<String> lKeywords;

    void load(){
    }
}

class RoomType {
    // 1 of 1000
    static int[] num = {260, 200, 200, 200, 75, 40, 20, 5}; //wall, decision, fight, passive, treasure, story, shop, secret
}

class Room {
    ArrayList<Event> vEvent;
    private int roomType;
    Room(int roomType){
        this.roomType = roomType;
    }

    int getRoomType() {
        return roomType;
    }
}

public class wmain {
    public static void main(String[] args){
        new wmain();
    }

    private boolean gameOver = false;

    private wmain(){
        Player p = new Player();
        ArrayList<Room> lrWorld = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            for (int j = 0; j < RoomType.num.length; j++){
                if(RoomType.num[j] > 0){
                    lrWorld.add(new Room(j));
                    RoomType.num[j]--;
                }
            }
        }
        int currentRoom = 0;

        Scanner scanner = new Scanner(System.in);

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleWithFixedDelay(() -> {
            if(p.health <= 0 || p.oxygen <= 0){
                System.out.println("Game Over!");
                gameOver = true;
            }
            p.oxygen--;
        }, 0, 1, TimeUnit.SECONDS);

        while (!gameOver){
            Room room = lrWorld.get(currentRoom);
            int type = room.getRoomType();
            if(type == 0){//wall

            } else if(type == 1){//decision

            } else if(type == 2){//fight
                int enemyType = ThreadLocalRandom.current().nextInt(0,100);
                if(enemyType >= 0 && enemyType <= 70){//Zombies
                    Main.printText("ACHTUNG! Du befindest dich in einem Raum mit Zombies!", 100);
                    for (int i = 0; i < 3;){
                        char key = (char) ThreadLocalRandom.current().nextInt(97,123);
                        char capKey = (char) (key - 32);

                        System.out.println("Drücke " + key);
                        try {
                            long t1 = System.currentTimeMillis();
                            char input = (char) RawConsoleInput.read(true);
                            if(input == key || input == capKey){
                                long t2 = System.currentTimeMillis();
                                if((t2 - t1) < 2000){
                                    i++;
                                    System.out.println("Du konntest dem Zombie schaden!");
                                } else {
                                    p.health -= 15;
                                    System.out.println("Zombie hat dich attackiert!");
                                }
                            } else {
                                p.health -= 15;
                                System.out.println("Zombie hat dich attackiert!");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Main.printText("Du hast den Zombie getötet!");
                } else if(enemyType > 70 && enemyType <= 90){//Zombies mit Raumanzug
                    Main.printText("ACHTUNG! Du befindest dich in einem Raum mit einem Zombie mit Raumanzug!", 100);
                    if(p.weapon){
                        int number = ThreadLocalRandom.current().nextInt(1000,100000);
                        Main.printText("Merke dir folgenden Deaktivierungscode: " + number);
                        System.out.println("");
                        char key = (char) ThreadLocalRandom.current().nextInt(97,123);
                        char capKey = (char) (key - 32);

                        System.out.println("Drücke " + key);
                        try {
                            long t1 = System.currentTimeMillis();
                            char input = (char) RawConsoleInput.read(true);
                            if(input == key || input == capKey){
                                long t2 = System.currentTimeMillis();
                                if((t2 - t1) < 1500){
                                    System.out.println("Du konntest dem Zombie schaden!");
                                    System.out.println("Mit dem Deaktvierungscode des Raumanzuges kannst du den Zombie entgültig eliminieren");
                                    Main.cls();
                                    System.out.println("Bitte hier eingeben: ");
                                    if(scanner.nextLine().equals(String.valueOf(number))){
                                        Main.printText("Du hast den Zombie getötet!");
                                    }
                                } else {
                                    p.health -= 25;
                                    System.out.println("Zombie hat dich attackiert!");
                                }
                            } else {
                                p.health -= 25;
                                System.out.println("Zombie hat dich attackiert!");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {//Zombies mit Waffe

                }
            } else if(type == 3){//passive
                Main.printText("Du bist in einem leeren Raum.");
                int chance = ThreadLocalRandom.current().nextInt(0,100);
                if(chance == 99){
                    Main.printText("Seltsam. Was war das?");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    p.luck *= 3;
                    Main.printText("Dein Glück hat sich erhöht!", 100);
                }
            } else if(type == 4){//treasure
                int chanceOxygen = ThreadLocalRandom.current().nextInt(0,100);
                int chanceMoney = ThreadLocalRandom.current().nextInt(0,100);
                int chanceWeapon = ThreadLocalRandom.current().nextInt(0,100);
                int chanceLuckyCharm = ThreadLocalRandom.current().nextInt(0,100);
                if(chanceOxygen >= 0 && chanceOxygen <= 80){
                    System.out.println("Du hast hier Sauerstoff gefunden!");
                    p.oxygen += 1000;
                }
                if(chanceMoney >= 0 && chanceMoney <= 75){
                    int money = 20 * p.luck;
                    System.out.println("Du hast hier " + money + "$ gefunden!");
                    p.money += money;
                }
                if((chanceWeapon >= 0 && chanceWeapon <= 50) && !p.weapon){
                    System.out.println("Du hast hier eine Waffe gefunden!");
                    p.weapon = true;
                }
                if(chanceLuckyCharm >= 0 && chanceLuckyCharm <= 35){
                    System.out.println("Du hast hier einen Glücksbringer gefunden!");
                    p.luck += 25;
                }
            } else if(type == 5){//story

            } else if(type == 6){//shop
                Main.printText("Willkommen im Shop!");
                Main.printText("Wir entschuldigen die hohen Preise aufgrund der geringen Nachfrage!");
                System.out.println("Sauerstoffflasche 10$");
                System.out.println("Snack 30$");
                System.out.println("Medikamente 70$");
                System.out.println("Waffe 200$");
                System.out.println("Glücksbringer 800$");
                System.out.println("Rüstung 1000$");
                Main.printText("Was möchten Sie kaufen?");
                String input = scanner.nextLine().trim().toLowerCase();
                String nem = "Nicht genug Geld!";
                if(input.contains("sauerstoff")){
                    if(p.money >= 10) {
                        p.money -= 10;
                        p.oxygen += 100;
                    } else {
                        System.out.println(nem);
                    }
                } else if(input.contains("snack")){
                    if(p.money >= 30){
                        p.money -= 30;
                        if(p.health <= 100) {
                            p.health += 15;
                        }
                    } else {
                        System.out.println(nem);
                    }
                } else if(input.contains("med")){
                    if(p.money >= 70){
                        p.money -= 70;
                        if(p.health <= 100) {
                            p.health += 48;
                        }
                    } else {
                        System.out.println(nem);
                    }
                } else if(input.contains("waffe")){
                    if (!p.weapon){
                        if(p.money >= 200){
                            p.money -= 200;
                        } else {
                            System.out.println(nem);
                        }
                    } else {
                        System.out.println("Du hast schon eine Waffe!");
                    }
                } else if(input.contains("glück")){
                    if(p.money >= 800){
                        p.money -= 800;
                        p.luck += 25;
                    } else {
                        System.out.println(nem);
                    }
                } else if(input.contains("rüstung")){
                    if(!p.rüstung) {
                        if (p.money >= 1000) {
                            p.money -= 1000;
                            p.rüstung = true;
                        } else {
                            System.out.println(nem);
                        }
                    } else {
                        System.out.println("Du hast schon eine Rüstung!");
                    }
                }
            } else if(type == 7){//secret
                p.luck *= 8;
                p.money *= 2;
                p.health = 100;
            }
            System.out.println("Du hast aktuell " + p.money + "$, " + p.oxygen + " Sekunden Sauerstoff und " + p.luck + " an Glück.");
            Main.printText("Wechsle Raum...");
            currentRoom++;
            Main.cls();
        }

    }
}
