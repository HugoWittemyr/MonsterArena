import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Deque;
import java.util.Map;
import java.util.Scanner;

public class MonsterArena {

    public static void main(String[] args) {
        // Skapar en lista med olika monstren
        monsters.add(new Dragon());
        monsters.add(new Robot());
        monsters.add(new Slime());

        for(Monster monster : monsters)
            points.put(monster.getName(), 0);
        
        reShuffle();
        while (gameActive) {
            meny();
        }
    }

    // Skapar alla variabler och metoder som static för att jag ville det
    static boolean gameActive = true; 
    static Scanner input = new Scanner(System.in);
    static List<Monster> monsters = new ArrayList<Monster>();
    static Deque<Monster> order = new LinkedList<Monster>();
    static Map<String, Integer> points = new HashMap<>();

    // Metod för menyn, visar valen och sedan kör rätt metod för varje val. efter varje metod som körs så kommer alltid meny att köras fram tills avnändaren väljer att stänga ned.
    static void meny() {
        System.out.print("""

            --- MONSTER ARENA ---
            1. Visa nästa match
            2. Skapa en annan match
            3. Starta matchen
            4. Topplista
            5. Beskrivning av monstren
            6. Stäng av programmet
        """);

        switch (input.nextInt()) { 
            case 1: // Skriver ut vilka två monster som möts nästa gång
                System.out.println("\nNästa match: " + order.peekFirst().getName() + " mot " + order.peekLast().getName());
                input.next();
                break;
            case 2: // Väljer en ny matchup
                System.out.println("\nVill du själv välja monstren eller ska det slumpas? \n1. Välj själv \n2. Slumpa");
                switch (input.nextInt()) {
                    case 1: // Du får välja din champion
                        pickChampion();
                        break;
                    case 2: // Kör metoden som shufflar fram 2 monster
                        do {
                            reShuffle();
                            System.out.println("\nNya matchen: " + monsters.get(0).getName() + " mot " + monsters.get(1).getName() + "\nNöjd med nya matchen? \n1. Ja\n2. Nej");
                        } 
                        while (input.nextInt() != 1);
                        break;
                    default:
                        System.out.println("Snälla välj ett av alternativen");
                }
                break;               
            case 3: // Startar matchen
                match(order);
                reShuffle();
                System.console().readLine();
                break;
            case 4: // Skriver ut topplistan
                rankings();
                break;
            case 5: // Skiver ut beskrivningen för alla monster
                for (Monster monster : monsters)
                    monster.description();
                System.console().readLine();
                break;
            case 6: // Avslutar programmet
                gameActive = false;
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    // Funktion som blandar om ordningen och lägger in de två första i en queue
    static void reShuffle() {

        Collections.shuffle(monsters);

        order.clear();

        // Lägger in de första två monstren i kön
        for (int i = 0; i < 2; i++){
            order.offer(monsters.get(i));
        }
    }

    // Funktion som låter användaren välja ett monster som sin champion, sen ger möjligheten att slumpa eller välja nästa
    static void pickChampion() {
        order.clear();
        int choice;

        System.out.println("\nVälj ditt monster: ");

        for (int i = 0; i < 3; i++)
            System.out.println((i + 1) + ": " + monsters.get(i).getName());

        choice = input.nextInt();
        order.offer(monsters.get(choice - 1));

        System.out.println("\nVill du välja en motståndare eller ska den slumpas? \n1: Ja \n2:nej");

        if(input.nextInt() == 1) {
            System.out.println("\nVälj din motståndare");
            for (int i = 0; i < 3; i++) {
                if(i == (choice - 1)) {
                    System.out.println((i + 1) + ": " + monsters.get(i).getName() + " <- Redan vald");
                    continue;
                }
                System.out.println((i + 1) + ": " + monsters.get(i).getName());
            }


            
            order.offer(monsters.get(input.nextInt() - 1));
        } else {
            Collections.shuffle(monsters);
            if (monsters.get(0) != order.peek()) {
                order.offer(monsters.get(0));
            } else {
                order.offer(monsters.get(1));
            }
        }

        System.out.println("Du valde " + order.peekFirst() + " som möter " + order.peekLast());
    }

    // Funktion som kör själva matchen
    static void match(Deque<Monster> order) {
        // Lägger in de två monstren som ligger först i kön
        Monster first = order.poll();
        Monster second = order.poll();
        int round = 1;

        System.out.println("""
            Matchen startar!!
        """ + first.getName() + " möter " + second.getName() + "!");
        
        /*En for-loop som kör varje attack, 2 ggr per runda. 
        IF-satsen kollar om rundan är jämn eller ojämn för att kolla vems tur det är att attackera. 
        Round skrivs bara ut varannan gång som forloopen körs för att det ska se ut som att båda monstren attackerar en gång per runda.
        I slutet finns det två if-satser som kollar om någon av monstren har hamnat på 0 hp och avslutar då matchen och ger vinnaren sina poäng.
        */  
        for (int i = 1; i < 100; i++) {
            
            if(i%2 != 0) {
                System.out.println("\n--ROUND " + round + "!--\n");

                second.takeDamage(first.attack(second.health));
                System.out.println(second.getName() + " har " + second.getHealth() + " HP kvar\n");
            } else {
                first.takeDamage(second.attack(first.health));
                System.out.println(first.getName() + " har " + first.getHealth() + " HP kvar\n");
                round++;
            }

            if(first.getHealth() <= 0) {
                System.out.println(first.getName() + " har inget HP kvar. " + second.getName() + " har vunnit matchen!");
                first.resetHealth();
                second.resetHealth();
                points.put(second.getName(), points.get(second.getName()) + 3);
                break;
            }

            if(second.getHealth() <= 0) {
                System.out.println(second.getName() + " har inget HP kvar. " + first.getName() + " har vunnit matchen!");
                first.resetHealth();
                second.resetHealth();
                points.put(first.getName(), points.get(first.getName()) + 3);
                break;
            }

            try { // Gör så att det tar 0,5 sekunder mellan varje attack, gör det enklare och roligare att se vad som händer i matchen.
            Thread.sleep(1250);
            }
            catch(InterruptedException ex) {} // Ingen aning vad denna biten gör men den behöver finnas där
        }
    }
    
    // Enkel funktion för att visa vad varje monster har för poäng
    static void rankings() {
        System.out.println("\nPoäng:");
        System.out.println(points);
    }
}

