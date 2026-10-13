import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Original console application preserved for the transformation history.
 * Runtime behavior now lives in the Spring Boot REST API.
 */
public class AvatarBendingIndicator {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> elements = new ArrayList<>();
        Collections.addAll(name, "Aang", "Sokka", "Toph", "Ty Lee", "Hakoda", "Roku", "Kyoshi", "Yangchen", "Momo", "Kuruk", "Zuko", "Iroh", "Appa", "Suki", "Mai", "Kitara", "Azula", "Ozai", "Sozin", "Bumi", "Yue", "Gyatso", "Wan Shi Tong", "Chey", "Hama", "Piandao", "Pakku");
        Collections.addAll(elements, "Avatar from the Southern Air Temple", "Non-Bender from the Southern Water Tribe", "EarthBender from the Earth Nation", "Non-Bender from the Fire Nation", "Non-Bender from the Southern Water Tribe",
                "Avatar from the Fire Nation", "Avatar from the Earth Nation", "Avatar from the Western Air Temple", "Aang's Animal Companion", "Avatar from the Northern Water Tribe", "FireBender from the Fire Nation", "FireBender of the White Lotus", "Aang's Animal Companion",
                "Kyoshi Warrior", "Non-Bender from the Fire Nation", "WaterBender from the Southern Water Tribe", "FireBender from the Fire Nation", "Fire-Lord of the Fire Nation", "Ozai's Father (Past Fire-Lord)", "EarthBender of the White Lotus", "Moon Spirit", "AirBending Master from the Southern Air Temple", "Spirit of Knowledge", "FireBender of the White Lotus", "BloodBender from the Southern Water Tribe", "Swordsmen of the White Lotus", "WaterBender of the White Lotus");
        String response;
        do {
            System.out.print("Enter a famous character from \"Avatar the Last AirBender\": ");
            String input = kb.nextLine().toLowerCase();
            int index = -1;
            for (int i = 0; i < name.size(); i++) {
                if (name.get(i).equalsIgnoreCase(input)) {
                    index = i;
                }
            }
            if (index != -1) {
                System.out.println("\n" + name.get(index) + " is: " + elements.get(index));
            } else {
                System.out.println("\n" + input + " is not in \"Avatar the Last AirBender\"");
            }
            System.out.print("\nDo you want to enter another name? yes/no: ");
            response = kb.nextLine().toLowerCase();
            if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")) {
                System.out.println("\nAir...Water...Earth...Fire...GoodBye!");
            }
        } while (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes"));
        kb.close();
    }
}
