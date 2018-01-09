public class Room {

    private int type;
    private String name;
    private Event event;
    private Player player;

    Room(int type, Player player){
        this.type = type;
        if(type == 0 || type == 7){
            name = "Wand";
        } else {
            name = "Tür";
        }
        this.event = new Event(type, player);
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
}
