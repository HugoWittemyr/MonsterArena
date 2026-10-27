public abstract class Monster {
    protected String name;
    protected int health = 100;
    int damage;
    int points;

    Monster(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void resetHealth() {
        this.health = 100;
    }

    void takeDamage(int damage) {
        this.health -= damage;
    }

    abstract int attack(int hp);
    
    abstract void description();
}
