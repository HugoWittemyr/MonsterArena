import java.util.Random;

public class Slime extends Monster{
    Slime() {
        super("Slime");
    }

    Random r = new Random();

    @Override 
    int attack(int hp) {
        damage = r.nextInt(15, 35);
        System.out.println(name + " attackerar och gör " + damage + " skada.");
        return damage;
    }

    @Override 
    void description() {
        System.out.println("\n" + getName() + "\nHP: " + getHealth() + "\nSpecial attack: Slime har ingen specialattack utan gör istället med skada vid varje vanlig attack.");
    }
}