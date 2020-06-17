package model;

import java.io.*;

public class Editor {

    private Grid grid;
    private final String textFileURL;
    private final String tmpFileURL;

    public Editor() {
        String fileName = "custom_map";
        textFileURL = String.format("src/maps/%s", fileName);
        tmpFileURL = String.format("src/maps/%s.tmp", fileName);
        loadMap();
    }

    private void loadMap() {
//        deserializeMap(tmpFileURL);
        loadMapTextFile(textFileURL);
    }

    public void saveMap() {
        if (grid.isValid()) {
            serializeMap(tmpFileURL);
            saveMapTextFile(textFileURL);
        } else {
            throw new RuntimeException("Cannot save invalid Map");
        }
    }

    /**
     * Saves the current map into a tmp file using serialization
     * @param fileURL path of the tmp file that to be saved
     */
    private void serializeMap(String fileURL) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = new FileOutputStream(fileURL);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(grid);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map from the tmp file through deserialization
     * @param fileURL path of the tmp file to be deserialized
     */
    private void deserializeMap(String fileURL) {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        try {
            fileInputStream = new FileInputStream(fileURL);
            objectInputStream = new ObjectInputStream(fileInputStream);
            grid = (Grid)objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the map into a text file
     * @param fileURL path of the text file to be saved
     */
    private void saveMapTextFile(String fileURL) {
        StringBuilder mapData = new StringBuilder();
        for(int j = 0; j < 12; j++) {
            for(int i = 0; i < 18; i++) {
                mapData.append(this.convertToInteger(this.grid.getTile(i, j)));
            }
        }
        try {
            File file = new File(fileURL);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(mapData.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map from the text file
     * @param fileURL path of the text file to be read
     */
    private void loadMapTextFile(String fileURL) {
        grid = new Grid(fileURL);
    }

    /**
     * Coverts a tile to its corresponding integer string for text file saving
     * @param tile tile to be converted into an integer string
     * @return the corresponding integer string
     */
    private String convertToInteger(Tile tile) {
        String integer;
        switch (tile.getName()) {
            case "GRASS": integer = "0"; break;
            case "LAND_SPAWN": integer = "1"; break;
            case "LAND": integer = "2"; break;
            case "LAND_END": integer = "3"; break;
            case "WATER_SPAWN": integer = "4"; break;
            case "WATER": integer = "5"; break;
            case "WATER_END": integer = "6"; break;
            case "SHALLOW_WATER": integer = "7"; break;
            case "DEEP_WATER": integer = "8"; break;
            default: throw new IllegalStateException("Unexpected value: " + tile.getName());
        }
        return integer;
    }

    public Grid getGrid() { return grid; }

}
