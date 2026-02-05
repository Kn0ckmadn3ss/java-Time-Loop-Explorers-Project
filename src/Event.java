package thetimeloopexplorers1;

import java.util.Random;


public class Event {

    private String description;
    private int hpChange;
    private int treasureChange;
    private String affectedStone;
    private boolean isReverseEvent;

    public Event(String description, int hpChange, int treasureChange, String affectedStone, boolean isReverseEvent) {
        this.description = description;
        this.hpChange = hpChange;
        this.treasureChange = treasureChange;
        this.affectedStone = affectedStone;
        this.isReverseEvent = isReverseEvent;
    }

    public static Event generateRandomEvent(int roomNumber) {
        Random rand = new Random();
        int eventTypeRoll = rand.nextInt(100);

        if (eventTypeRoll < 20) {
            String stone = "EchoStone-" + (rand.nextInt(5) + 1);
            if (roomNumber > 50 && rand.nextInt(10) == 0) {
                stone = "ParadoxStone";
            }
            return new Event("You found stone " + stone, 0, 0, stone, false);

        } else if (eventTypeRoll < 50) {
            int harmfulType = rand.nextInt(4);
            switch (harmfulType) {
                case 0:
                    return new Event("Poison Box -2HP", -2, 0, null, false);
                case 1:
                    return new Event("Predator Attack -3HP", -3, 0, null, false);
                case 2:
                    return new Event("Toadstool -1HP", -1, 0, null, false);
                case 3:
                default:
                    return new Event("Cliff Fall -3HP", -3, 0, null, false);
            }

        } else if (eventTypeRoll < 80) {
            int helpfulType = rand.nextInt(4);
            switch (helpfulType) {
                case 0:
                    return new Event("Healing Potion +2HP", +2, 0, null, false);
                case 1:
                    return new Event("Energy Drink +1HP", +1, 0, null, false);
                case 2:
                    return new Event("Drink Clean Water +3HP", +3, 0, null, false);
                case 3:
                default:
                    return new Event("Health Kit +1HP", +1, 0, null, false);
            }

        } else {
            return new Event("The flow of time reversed!", 0, 0, null, true);
        }
    }

    public String getDescription() {
        return description;
    }

    public int getHpChange() {
        return hpChange;
    }

    public int getTreasureChange() {
        return treasureChange;
    }

    public String getAffectedStone() {
        return affectedStone;
    }

    public boolean isReverseEvent() {
        return isReverseEvent;
    }
}
