package com.example.task02;

import java.io.*;
import java.nio.file.Files;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {
    private final File file;
    private final List<E> list = new ArrayList<>();

    public SavedList(File file) {
        this.file = file;
        loadFromFile();
    }

    private void loadFromFile() {
        if (!file.exists()) return;
        try (ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            @SuppressWarnings("unchecked")
            List<E> loadedList = (ArrayList<E>) stream.readObject();
            list.clear();
            list.addAll(loadedList);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error downloading data from a file", e);
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            stream.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException("Error saving data to file", e);
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E oldElement = list.set(index, element);
        saveToFile();
        return oldElement;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        saveToFile();
    }

    @Override
    public E remove(int index) {
        E removedElement = list.remove(index);
        saveToFile();
        return removedElement;
    }
}