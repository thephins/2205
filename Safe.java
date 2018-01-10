import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Safe {
    private boolean open, code_found, generated;
    private String id;
    private int code, money, meds, oxygen;

    Safe(){
        this.open = false;
        this.id = UUID.randomUUID().toString();
        this.code = ThreadLocalRandom.current().nextInt(10000,99999);
        this.money = ThreadLocalRandom.current().nextInt(0,10000);
        this.meds = ThreadLocalRandom.current().nextInt(0,3);
        this.oxygen = ThreadLocalRandom.current().nextInt(0,3000);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isCode_found() {
        return code_found;
    }

    public void setCode_found(boolean code_found) {
        this.code_found = code_found;
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
}
