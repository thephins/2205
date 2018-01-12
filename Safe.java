import java.util.concurrent.ThreadLocalRandom;

public class Safe {
    private boolean open, generated;
    private String id, codeRoomId;
    private int code, money, meds, oxygen, publicID;

    Safe(String id){
        this.open = false;
        this.generated = false;
        this.id = id;
        this.code = ThreadLocalRandom.current().nextInt(10000,100000);
        this.money = ThreadLocalRandom.current().nextInt(0,10000);
        this.meds = ThreadLocalRandom.current().nextInt(0,4);
        this.oxygen = ThreadLocalRandom.current().nextInt(0,3000);
        boolean found = false;
        do {
            this.publicID = ThreadLocalRandom.current().nextInt(100, 1000);
            if(!Main.safes.isEmpty()) {
                for (Safe safe : Main.safes) {
                    if (safe.getPublicID() != this.publicID) {
                        found = true;
                    }
                }
            } else {
                found = true;
            }
        } while (!found);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public String getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public int getMoney() {
        return money;
    }

    public int getMeds() {
        return meds;
    }

    public int getOxygen() {
        return oxygen;
    }

    public String getCodeRoomId() {
        return codeRoomId;
    }

    public void setCodeRoomId(String codeRoomId) {
        this.codeRoomId = codeRoomId;
    }

    public int getPublicID() {
        return publicID;
    }
}
