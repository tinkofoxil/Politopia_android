package it.mirea.mypolitopia.Map;

import java.util.ArrayList;
import java.util.List;

public class City {
    private int id; // ID гороода
    private List<Npc> npcs; // список нпс в городе
    private int level; // уровень города
    private static int moneyAmount; // количество денег во всех городах
    private Npc.Species species; // раса
    private int x; // координаты города по х
    private int y; // координаты города по у
    private static int goldAmount = 0; // количество золота во всех городах
    private static int treeAmount = 0; // количество дерева во всех городах
    private static int stoneAmount = 0; // количество камня во всех городах
    private Cell[][] field;


    // конструктор для создания новых городов
    public City(Npc.Species species, int level, int id, int x, int y) {
        this.species = species;
        this.level = level;
        moneyAmount = 0;
        npcs = new ArrayList<>();
        this.id = id;
        this.x = x;
        this.y = y;
        field = Field.getInstance();

    }

    // конструктор для создания первого города
    public City(Npc.Species species, int x, int y) {
        this.species = species;
        level = 1;
        id = 1;
        moneyAmount = 100;
        npcs = new ArrayList<>();
        npcs.add(new Npc(Npc.NpcType.WARRIOR, 0, this, x - 1, y));
        field = Field.getInstance();
        field[x - 1][y].setNPC(true);
        this.x = x;
        this.y = y;
    }

    // метод повышения уровня
    public void levelUp() {
        if (moneyAmount > (level + 1) * 150 && treeAmount > level * 100 && stoneAmount > level * 100) {
            moneyAmount -= (level + 1) * 150;
            treeAmount -= level * 100;
            stoneAmount -= level * 100;
            this.level++;
        }
    }

    // метод, выполянемый перед ходом
    public void newAction() {
        moneyAmount += level * 150;
        this.makeAction();
    }

    // метод выполнения хода
    public void makeAction() {
        for (int i = 0; i < npcs.size(); i++) {
            npcs.get(i).newAction();
        }
    }


    // вариант решения: в классе Map сделать такой же метод, который вокруг города будет искать
    // свободные клетки, куда можно заспавнить нпс
    public void createNpc(Npc.NpcType npcType) {
        npcs.add(new Npc(Npc.NpcType.WARRIOR, 0, this, x - 1, y));
    }

    // ГЕТТЕРЫ
    // метод возврашает позицию по Х
    public int getPosX() {
        return x;
    }

    // метод возвращает позицию по Y
    public int getY() {
        return y;
    }

    // метод возвращает расу
    public Npc.Species getSpecies() {
        return species;
    }

    // метод возвращает количестов дЕнЯк
    public int getMoneyAmount() {
        return moneyAmount;
    }

    // метод возвращает уровень города
    public int getLevel() {
        return level;
    }

    // метод возвращает списко NPC
    public List<Npc> getNpcs() {
        return npcs;
    }

    // метод возвращает id города
    public int getId() {
        return id;
    }

    //СЕТТЕРЫ
    // метод устанавливает позицию по Х
    public void setPosX(int x) {
        this.x = x;
    }

    // метод устанавливает позицию по Y
    public void setPosY(int y) {
        this.y = y;
    }

    // метод устанавливает позицию по X и Y
    public void setPos(int x, int y) { this.x = x; this.y = y; }

    // метод устанавливает ID города
    public void setId(int id) {
        this.id = id;
    }

    // метод устанавливает список NPC города
    public void setNpcs(List<Npc> npcs) {
        this.npcs = npcs;
    }

    // метод устанавливает уровень города
    public void setLevel(int level) {
        this.level = level;
    }

    // метод устанавливает изначальное количество дЕнЯк
    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    // метод устанавлвиает расу города
    public void setSpecies(Npc.Species species) {
        this.species = species;
    }


}
