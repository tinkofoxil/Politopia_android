package it.mirea.mypolitopia.Map;

public class Cell {

    private Resource resource; // переменная, в которой хранятся ресурсы на клетке
    private CellType type; // тип игровой клетки
    private City city = null;
    private boolean isCity = false;
    private Npc npc;
    private boolean isNPC = false;
    private boolean isEmpty; // пустая ли клетка, или на ней есть игрок\город

    // тип игровой клетки
    public enum CellType {
        GROUND,
        WATER
    }

    // конструктор
    public Cell() {
        Resource resource = new Resource();
        this.resource = resource;
        this.setType(CellType.GROUND);
    }

    // ГЕТТЕРЫ
    // метод возвращает ресурсы с клетки
    public Resource getResource() {
        return resource;
    }

    // метод возвращает тип игровой клетки
    public CellType getType() {
        return type;
    }

    // метод возвразает информацию о том, есть ли на ней NPC или City
    public boolean getIsEmpty() {
        return isEmpty;
    }

    // метод возвращает город
    public City getCity() {
        return city;
    }

    // информирует о том, что есть город
    public boolean getBoolCity() {
        return isCity;
    }

    // метод возвращает обхект NPC
    public Npc getNpc() {
        return npc;
    }

    // метод возвращает, есть ли нпс на клетке
    public boolean isNPC() {
        return isNPC;
    }

    // СЕТТЕРЫ
    // метод устанавливает игровой ресурс в клетку
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    // метод устанавливает тип игровой клетки
    public void setType(CellType type) {
        this.type = type;
    }

    // метод устанавливает, есть ли на клетке NPC или City
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    // метод устанавливает город
    public void setCity(City city) {
        this.city = city;
    }

    // информирует о том, что есть город
    public void setBoolCity(boolean city) {
        isCity = city;
    }

    // метод устанавливает обхект нпс на клетку
    public void setNpc(Npc npc) {
        this.npc = npc;
    }

    // логическая переменная о том, что нпс на клетке есть
    public void setNPC(boolean NPC) {
        isNPC = NPC;
    }
}
