
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PokemonData {

    public static void main(String[] args) throws IOException {
        List<String> pokemonLines = Files.readAllLines(Paths.get("pokemon_dataset.csv"));
        ArrayList<PokemonInfo> pokemonData = new ArrayList<PokemonInfo>();
        String[] tempData;
        for (int i = 1; i < pokemonLines.size(); i++) {
            tempData = pokemonLines.get(i).split(",");
            pokemonData.add(new PokemonInfo(tempData[0], tempData[1], tempData[2], tempData[3], tempData[5],
                    tempData[6], tempData[7]));
        }

        avgBST(pokemonData);
        medianBST(pokemonData);
        System.out.println("\nMost common single typing: ");
        avgType(pokemonData);
        System.out.println("\nMost common dual typing: ");
        avgTypeDual(pokemonData);
    }
    
    public static void avgBST(ArrayList<PokemonInfo> pokemonData) {
        int sum = 0;
        for (PokemonInfo pokemon : pokemonData) {
            sum += pokemon.getBst();
        }
        System.out.println("Average BST: " + sum / pokemonData.size());
    }


    public static void medianBST(ArrayList<PokemonInfo> pokemonData) {
        int[] pokemonStats = new int[pokemonData.size()];
        for (int i = 0; i < pokemonStats.length; i++) {
            pokemonStats[i] = pokemonData.get(i).getBst();
        }
        Arrays.sort(pokemonStats);
        System.out.println("Median BST: " + ((pokemonStats[(int) (Math.floor(pokemonStats.length / 2.0f))] + pokemonStats[(int) (Math.ceil(pokemonStats.length / 2.0f))]) / 2.0f));
        // System.out.println("Median BST: " + pokemonStats[((int) Math.floor(pokemonData.size() / 2) + (int) Math.ceil(pokemonData.size() / 2)) / 2 - 2]);
    }


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
        // for (String i : amtType1.keySet()) {
        //     System.out.println(i);
        // }
        HashMap<String, Integer> getHighest = getHighest(amtType1);
        System.out.println(((String) getHighest.keySet().toArray()[0]));
        System.out.println((int) getHighest.values().toArray()[0]);
    }

    public static void avgTypeDual(ArrayList<PokemonInfo> pokemonData) {
        HashMap<List<String>, Integer> amtTypeDual = new HashMap<List<String>, Integer>();

        for (int i = 0; i < pokemonData.size(); i++) {
            if(!pokemonData.get(i).getType2().equals("")) {

                if (amtTypeDual.containsKey(Arrays.asList(pokemonData.get(i).getType1(), pokemonData.get(i).getType2()))) {
                    // System.out.println("hi");
                    amtTypeDual.replace(Arrays.asList(pokemonData.get(i).getType1(), pokemonData.get(i).getType2()), amtTypeDual.get(Arrays.asList(pokemonData.get(i).getType1(), pokemonData.get(i).getType2())) + 1);
                } else {
                    amtTypeDual.put(Arrays.asList(pokemonData.get(i).getType1(), pokemonData.get(i).getType2()), 1);
                }

            }
        }
        // System.out.println(amtTypeDual.keySet());
        // for (List<String> i : amtTypeDual.keySet()) {
        //     System.out.println(amtTypeDual.get(i) + " " + i.get(0) + " " + i.get(1));
        // }
        // for (List<String> i : amtTypeDual.keySet()) {
        //     System.out.println(i[0] + " " + i[1]);
        // }
        HashMap<List<String>, Integer> getHighestDual = getHighestDual(amtTypeDual);
        // System.out.println(Arrays.asList(getHighestDual.keySet()).get(0));
        System.out.println(getHighestDual.keySet().iterator().next().get(0) + " " + getHighestDual.keySet().iterator().next().get(1));
        System.out.println(getHighestDual.values().iterator().next());

        // HashMap<String, Integer> getHighest = getHighest(amtType1);
        // System.out.println(((String) getHighest.keySet().toArray()[0]));
        // System.out.println((int) getHighest.values().toArray()[0]);
    }

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

    public static HashMap<List<String>, Integer> getHighestDual(HashMap<List<String>, Integer> amtType) {
        HashMap<List<String>, Integer> highest = new HashMap<List<String>, Integer>();
        for (List<String> i : amtType.keySet()) {
            if (highest.isEmpty()) {
                highest.put(i, amtType.get(i));
            } else if ((int) amtType.get(i) > (int) highest.values().toArray()[0]) {
                // System.out.println("hi");
                highest.clear();
                highest.put(i, amtType.get(i));
            }
        }
        return highest;
    }

    // 0 1 2 3 5 6 7
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
