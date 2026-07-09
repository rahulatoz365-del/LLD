interface Character{
    String getAbility();
}
//Concrete Component: A basic mario character with no abilities
class Mario implements Character{
    @Override
    public String getAbility() {
        return "I am Mario, I can jump!";
    }
}
// Abstract Decorator: A decorator class that implements the Character interface and has a reference to a Character object
abstract class CharacterDecorator implements Character{
    protected Character character;
    public CharacterDecorator(Character character){
        this.character = character;
    }
}
// Concrete Decorator: A decorator class that adds the ability to shoot fireballs to the character
class FireFlowerDecorator extends CharacterDecorator{
    public FireFlowerDecorator(Character character){
        super(character);
    }
    @Override
    public String getAbility() {
        return character.getAbility() + " I can also shoot fireballs!";
    }
}
// Concrete Decorator: A decorator class that adds the ability to fly to the character
class CapeDecorator extends CharacterDecorator{
    public CapeDecorator(Character character){
        super(character);
    }
    @Override
    public String getAbility() {
        return character.getAbility() + " I can also fly!";
    }
}
// Concrete Decorator: A decorator class that adds the ability to become invincible to the character
class StarDecorator extends CharacterDecorator{
    public StarDecorator(Character character){
        super(character);
    }
    @Override
    public String getAbility() {
        return character.getAbility() + " I can also become invincible!";
    }
}
// Concrete Decorator: A decorator class that adds the ability to swim to the character
class FrogSuitDecorator extends CharacterDecorator{
    public FrogSuitDecorator(Character character){
        super(character);
    }
    @Override
    public String getAbility() {
        return character.getAbility() + " I can also swim!";
    }
}
// Client code
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        // Create a basic Mario character
        Character mario = new Mario();
        System.out.println(mario.getAbility());
        // Decorate the Mario character with a Fire Flower
        Character fireMario = new FireFlowerDecorator(mario);
        System.out.println(fireMario.getAbility());
        // Decorate the Mario character with a Cape
        Character capeMario = new CapeDecorator(mario);
        System.out.println(capeMario.getAbility());
        // Decorate the Mario character with a Star
        Character starMario = new StarDecorator(mario);
        System.out.println(starMario.getAbility());
        // Decorate the Mario character with a Frog Suit
        Character frogMario = new FrogSuitDecorator(mario);
        System.out.println(frogMario.getAbility());
        // Decorate the Mario character with multiple abilities
        Character superMario = new FireFlowerDecorator(new CapeDecorator(new StarDecorator(new FrogSuitDecorator(mario))));
        System.out.println(superMario.getAbility());
    }
}