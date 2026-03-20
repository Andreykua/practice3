package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

    static final String FILE_NAME = "tasks.dat";
    static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            printMenu();
            if (!scanner.hasNextInt()) {
                scanner.next();
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showTasks();
                case 2 -> addTask(scanner);
                case 3 -> deleteTask(scanner);
                case 4 -> editTask(scanner);
                case 0 -> {
                    saveTasks();
                    System.out.println("Выход из программы.");
                }
                default -> System.out.println("Неверный выбор!");
            }
        } while (choice != 0);
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== TO DO LIST ===\n1. Показать задачи\n2. Добавить задачу\n3. Удалить задачу\n4. Редактировать задачу\n0. Выход\nВыберите пункт: ");
    }

    static void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    static void addTask(Scanner scanner) {
        System.out.print("Введите название задачи: ");
        String title = scanner.nextLine();
        tasks.add(new Task(title)); // Исправлено: теперь title сохраняется
        System.out.println("Задача добавлена.");
    }

    static void deleteTask(Scanner scanner) {
        showTasks();
        if (tasks.isEmpty()) return;
        System.out.print("Введите номер для удаления: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index > 0 && index <= tasks.size()) {
            tasks.remove(index - 1);
            System.out.println("Задача удалена.");
        }
    }

    static void editTask(Scanner scanner) {
        showTasks();
        if (tasks.isEmpty()) return;
        System.out.print("Введите номер для редактирования: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index > 0 && index <= tasks.size()) {
            System.out.print("Новое название: ");
            tasks.get(index - 1).setTitle(scanner.nextLine());
            System.out.println("Обновлено.");
        }
    }

    static void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения.");
        }
    }

    @SuppressWarnings("unchecked")
    static void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tasks = (ArrayList<Task>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Ошибка загрузки.");
        }
    }

    public static class Task implements Serializable { // Добавлена сериализация
        private String title;
        public Task(String title) { this.title = title; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        @Override
        public String toString() { return title; }
    }
}
