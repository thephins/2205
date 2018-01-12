import java.util.UUID;

public class Room {

    private int type;
    private String name;
    private Event event;
    private String id;

    Room(int type, Player player){
        this.type = type;
        if(type == 0 || type == 7){
            name = "Wand";
        } else {
            name = "Tür";
        }
        this.id = UUID.randomUUID().toString();
        if(type == 1){
            Main.safes.add(new Safe(id));
        }
        this.event = new Event(type, player, id);
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Event getEvent() {
        return event;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }
}
