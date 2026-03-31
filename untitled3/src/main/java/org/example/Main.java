package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "tasks.json";

    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        loadTasks();
        boolean running = true;
        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    showTasks();
                    break;
                case 3:
                    deleteTask();
                    break;
                case 4:
                    editTask();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Некорректный выбор");
            }
        }
        saveTasks();
    }

    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Показать все задачи");
        System.out.println("3. Удалить задачу");
        System.out.println("4. Редактировать задачу");
        System.out.println("5. Выйти");
        System.out.print("Выберите действие: ");
    }

    private static void addTask() {
        System.out.print("Введите название задачи: ");
        String name = scanner.nextLine();
        System.out.print("Задача: ");
        String description = scanner.nextLine();
        tasks.add(new Task(name, description));
        System.out.println("Задача добавлена!");
    }

    private static void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            System.out.println("Ваши задачи:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void deleteTask() {
        showTasks();
        if (tasks.isEmpty()) {
            return;
        }
        System.out.print("Введите номер: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index > 0 && index <= tasks.size()) {
            tasks.remove(index - 1);
            System.out.println("Задача удалена!");
        } else {
            System.out.println("Некорректный номер");
        }
    }

    private static void editTask() {
        showTasks();
        if (tasks.isEmpty()) {
            return;
        }
        System.out.print("Введите номер задачи для редактирования: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index > 0 && index <= tasks.size()) {
            System.out.print("Новое название задачи: ");
            String newName = scanner.nextLine();
            System.out.print("Новое описание задачи: ");
            String newDescription = scanner.nextLine();
            Task task = tasks.get(index - 1);
            task = new Task(newName, newDescription); 
            tasks.set(index - 1, task);
            System.out.println("Задача обновлена!");
        } else {
            System.out.println("Некорректный номер");
        }
    }

    private static void saveTasks() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            String json = gson.toJson(tasks);
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении задач: " + e.getMessage());
        }
    }

    private static void loadTasks() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<ArrayList<Task>>(){}.getType();
            ArrayList<Task> loadedTasks = gson.fromJson(reader, listType);
            if (loadedTasks != null) {
                tasks = loadedTasks;
            }
        } catch (IOException e) {
        }
    }

}
