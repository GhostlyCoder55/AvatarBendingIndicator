import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AvatarBendingIndicator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> element = new ArrayList<>();
        Collections.addAll(name, "Aang", "Sokka", "Toph", "Ty Lee", "Hakoda", "Roku", "Kyoshi", "Yangchen", "Momo", "Kuruk", "Zuko", "Iroh", "Appa", "Suki", "Mai", "Kitara", "Azula", "Ozai", "Sozin", "Bumi", "Yue", "Gyatso");
        Collections.addAll(element, "Avatar from the Southern Air Temple", "Non-Bender from the Southern Water Tribe", "EarthBender from the Earth Nation", "Non-Bender from the Fire Nation", "Non-Bender from the Southern Water Tribe",
                "Avatar from the Fire Nation", "Avatar from the Earth Nation", "Avatar from the Western Air Temple", "Aang's Animal Companion", "Avatar from the Northern Water Tribe", "FireBender from the Fire Nation", "FireBender of the White Lotus", "Aang's Animal Companion",
                "Kyoshi Warrior", "Non-Bender from the Fire Nation", "WaterBender from the Southern Water Tribe", "FireBender from the Fire Nation", "Fire-Lord of the Fire Nation", "Ozai's Father (Past Fire-Lord)", "EarthBender of the White Lotus", "Moon Spirit", "AirBending Master from the Southern Air Temple");
        String response;
        do {
            System.out.print("Enter a famous character from \"Avatar the Last AirBender\": ");
            String input = scanner.nextLine();
            int index = name.indexOf(input);
            if (index != -1) {
                System.out.println(input + " is: " + element.get(index));
            } else {
                System.out.println(input + " is not in \"Avatar the Last AirBender\"");
            }
            System.out.print("\nDo you want to enter another name? y/n: ");
            response = scanner.nextLine();
            if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")) {
                System.out.println("Air...Water...Earth...Fire...GoodBye!");
            }
        } while (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes"));
        scanner.close();
    }
}