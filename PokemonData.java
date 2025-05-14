
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PokemonData {
    //reading from file, creating an object for every entry in the csv file, and storing in an arraylist
    public static void main(String[] args) throws IOException {
        List<String> pokemonLines = Files.readAllLines(Paths.get("pokemon_dataset.csv"));
        ArrayList<PokemonInfo> pokemonData = new ArrayList<PokemonInfo>();
        String[] tempData;
        for (int i = 1; i < pokemonLines.size(); i++) {
            tempData = pokemonLines.get(i).split(",");
            pokemonData.add(new PokemonInfo(tempData[0], tempData[1], tempData[2], tempData[3], tempData[5],
                    tempData[6], tempData[7]));
        }

        avgBST(pokemonData);                //calling functions to do the calculations and printing result
        medianBST(pokemonData);
        System.out.println("\nMost common single typing: ");
        avgType(pokemonData);
        System.out.println("\nMost common dual typing: ");
        avgTypeDual(pokemonData);
    }

    //finding the average Base Stat Total among all the pokemon in the csv.
    
    public static void avgBST(ArrayList<PokemonInfo> pokemonData) {
        int sum = 0;
        for (PokemonInfo pokemon : pokemonData) {
            sum += pokemon.getBst();
        }
        System.out.println("Average BST: " + sum / pokemonData.size());
    }

    //finding the median Base Stat Total among all the pokemon in the csv.
    public static void medianBST(ArrayList<PokemonInfo> pokemonData) {
        int[] pokemonStats = new int[pokemonData.size()];
        for (int i = 0; i < pokemonStats.length; i++) {
            pokemonStats[i] = pokemonData.get(i).getBst();
        }
        Arrays.sort(pokemonStats);
        System.out.println("Median BST: " + ((pokemonStats[(int) (Math.floor(pokemonStats.length / 2.0f))] + pokemonStats[(int) (Math.ceil(pokemonStats.length / 2.0f))]) / 2.0f));

    }

    //constructs hashmap of each typing with their respective number of occurances then uses helper function getHighest to do calculations of the typing with the greatest number of occurences.
    public static void avgType(ArrayList<PokemonInfo> pokemonData) {
        HashMap<String, Integer> amtType1 = new HashMap<String, Integer>();
        HashMap<String, Integer> amtType2 = new HashMap<String, Integer>();

        for (int i = 0; i < pokemonData.size(); i++) {


            if (amtType1.containsKey(pokemonData.get(i).getType1())) {
                amtType1.put(pokemonData.get(i).getType1(), amtType1.get(pokemonData.get(i).getType1()) + 1);
            } else {
                amtType1.put(pokemonData.get(i).getType1(), 1);
            }


            if (!pokemonData.get(i).getType2().equals("")) {
                if (amtType2.containsKey(pokemonData.get(i).getType2())) {
                    amtType2.put(pokemonData.get(i).getType2(), amtType2.get(pokemonData.get(i).getType2()) + 1);
                } else {
                    amtType2.put(pokemonData.get(i).getType2(), 1);
                }
            }

        }

        HashMap<String, Integer> getHighest = getHighest(amtType1);
        System.out.println(((String) getHighest.keySet().toArray()[0]));
        System.out.println((int) getHighest.values().toArray()[0]);
    }

    //constructs hashmap of each typing with how many of said typing there are, then calls helper function getHighestDual to do calculation.
    public static void avgTypeDual(ArrayList<PokemonInfo> pokemonData) {
        HashMap<List<String>, Integer> amtTypeDual = new HashMap<List<String>, Integer>();

        for (int i = 0; i < pokemonData.size(); i++) {
            if(!pokemonData.get(i).getType2().equals("")) {

                if (amtTypeDual.containsKey(Arrays.asList(pokemonData.get(i).getType1(), pokemonData.get(i).getType2()))) {

                    amtTypeDual.replace(Arrays.asList(pokemonData.get(i).getType1(), pokemonData.get(i).getType2()), amtTypeDual.get(Arrays.asList(pokemonData.get(i).getType1(), pokemonData.get(i).getType2())) + 1);
                } else {
                    amtTypeDual.put(Arrays.asList(pokemonData.get(i).getType1(), pokemonData.get(i).getType2()), 1);
                }

            }
        }

        HashMap<List<String>, Integer> getHighestDual = getHighestDual(amtTypeDual);

        System.out.println(getHighestDual.keySet().iterator().next().get(0) + " " + getHighestDual.keySet().iterator().next().get(1));
        System.out.println(getHighestDual.values().iterator().next());


    }

    //using given hashmap returns the type that occurs the most
    public static HashMap<String, Integer> getHighest(HashMap<String, Integer> amtType) {
        HashMap<String, Integer> highest = new HashMap<String, Integer>();
        for (String i : amtType.keySet()) {
            if (highest.size() == 0) {
                highest.put(i, amtType.get(i));
            } else if (amtType.get(i) > (int) highest.values().toArray()[0]) {
                highest.remove((String) highest.keySet().toArray()[0]);
                highest.put(i, amtType.get(i));
            }
        }
        return highest;
    }
    //using given hashmap returns the dual typing that occurs the most
    public static HashMap<List<String>, Integer> getHighestDual(HashMap<List<String>, Integer> amtType) {
        HashMap<List<String>, Integer> highest = new HashMap<List<String>, Integer>();
        for (List<String> i : amtType.keySet()) {
            if (highest.isEmpty()) {
                highest.put(i, amtType.get(i));
            } else if ((int) amtType.get(i) > (int) highest.values().toArray()[0]) {

                highest.clear();
                highest.put(i, amtType.get(i));
            }
        }
        return highest;
    }

    // class used to store the csv data as objects.
    public static class PokemonInfo {

        int id;
        String name;
        String type1;
        String type2;
        String gen;
        String cat;
        int bst;

        public PokemonInfo(String id, String name, String type1, String type2, String gen, String cat, String bst) {
            this.id = Integer.parseInt(id);
            this.name = name;
            this.type1 = type1;
            this.type2 = type2;
            this.gen = gen;
            this.cat = cat;
            this.bst = Integer.parseInt(bst);
        }

        //getter functions
        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getType1() {
            return this.type1;
        }

        public String getType2() {
            return this.type2;
        }

        public String getGen() {
            return this.gen;
        }

        public String getCat() {
            return this.cat;
        }

        public int getBst() {
            return this.bst;
        }
    }
}
