import java.util.Random;

public class Robot extends Monster implements SpecialAttack {
    Robot() {
        super("Robot");
    }

    Random r = new Random();

    @Override 
    int attack(int enemyHp) {
        if (Math.random() < 0.25) { // Kollar ifall attacken blir en special attack, 25% chans
            useSpecialAttack(enemyHp);
            System.out.println(name + " använder sin specialattack och gör " + damage + " skada.");
        } else {
            damage = r.nextInt(10, 20);
            System.out.println(name + " attackerar och gör " + damage + " skada.");
        }
        return damage;
    }

    @Override 
    void description() {
        System.out.println("\n" + getName() + "\nHP: " + getHealth() + "\nSpecial attack: Roboten gör 3 snabba attacker efter varandra.");
    }

    @Override 
    public int useSpecialAttack(int enemyHp) {
        damage = 0;
        for (int i = 0; i < 3; i++) {
            damage += r.nextInt(10, 20);
        }
        return damage;
    }
}
