import java.util.Random;

public class Dragon extends Monster implements SpecialAttack{
    Dragon() {
        super("Drake");
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
        System.out.println("\n" + getName() + "\nHP: " + getHealth() + "\nSpecial attack: Om motståndaren har med än 30% HP kvar, gör drakens attack = 50% av kvarvarande HP. \nOm motståndaren har mindre än 30% gör draken tillräckligt för att dra ner HP till 0.");
    }

    @Override 
    public int useSpecialAttack(int enemyHp) {
        if(enemyHp < 30) {
            damage = enemyHp;
        } else {
            damage = enemyHp / 2;
        }
        return damage;
    }
}
