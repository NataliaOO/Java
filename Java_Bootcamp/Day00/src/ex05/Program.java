package ex05;

import java.util.*;

public class Program {
    private static final int MAX_STUDENTS = 10;
    private static final int MAX_NAME_LENGTH = 10;
    private static final int MAX_CLASSES_PER_WEEK = 10;
    private static final int COLUMN_WIDTH = 11;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> schedule = new ArrayList<>();
        HashMap<String, List<String>> september2020 = generateSeptemberDates();
        HashMap<String, HashMap<String, String>> attendance = new HashMap<>();

        inputStudents(scanner, attendance);
        inputSchedule(scanner, schedule, september2020);
        sortSchedule(schedule);
        inputAttendance(scanner, attendance, schedule);
        printAttendance(attendance, schedule);
    }

    private static void inputStudents(Scanner scanner, HashMap<String, HashMap<String, String>> attendance) {
        while (true) {
            String student = scanner.nextLine();
            if (student.equals(".")) {
                break;
            }
            if (student.trim().length() > MAX_NAME_LENGTH) {
                System.err.println("ERROR: Имя студента не должно превышать 10 символов.");
                continue;
            }
            if (attendance.size() < MAX_STUDENTS) {
                attendance.put(student, new HashMap<>());
            } else {
                System.out.println("INFO: Класс уже заполнен (максимум 10 студентов).");
            }
        }
    }

    private static void inputSchedule(Scanner scanner, ArrayList<String> schedule, HashMap<String, List<String>> september2020) {
        ArrayList<String> lessons = new ArrayList<>();
        while (true) {
            String input = scanner.nextLine();
            if (input.equals(".")) {
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.err.println("ERROR: Неверный формат ввода. Используйте <время (число)> <день недели (двухбуквенное сокращение)>.");
                continue;
            }

            int hourOffset = Integer.parseInt(parts[0]);
            if (hourOffset < 1 || hourOffset > 5) {
                System.err.println("ERROR: Время должно быть между 13:00 и 18:00.");
                continue;
            }
            String dayAbbreviation = parts[1];
            if (!september2020.containsKey(dayAbbreviation)) {
                System.err.println("ERROR: Неверный день недели. Используйте сокращения (MO, TU, WE, TH, FR, SA, SU).");
                continue;
            }
            if (lessons.contains(input)) {
                System.out.println("ERROR: Урок уже добавлен");
                continue;
            }

            if (lessons.size() < MAX_CLASSES_PER_WEEK) {
                lessons.add(input);
                for (String date : september2020.get(dayAbbreviation)) {
                    String formattedDate = String.format("%02d", Integer.parseInt(date));
                    schedule.add(hourOffset + ":00" + " " + dayAbbreviation + " " + formattedDate);
                }
            } else  System.out.println("INFO: Расписание заполнено (максимум 10).");
        }
    }

    private static void sortSchedule(ArrayList<String> schedule) {
        schedule.sort((a, b) -> {
            String[] partsA = a.split(" ");
            String[] partsB = b.split(" ");

            int dateA = Integer.parseInt(partsA[2]);
            int dateB = Integer.parseInt(partsB[2]);

            int timeA = Integer.parseInt(partsA[0].split(":")[0]);
            int timeB = Integer.parseInt(partsB[0].split(":")[0]);

            if (dateA != dateB) {
                return Integer.compare(dateA, dateB);
            } else {
                return Integer.compare(timeA, timeB);
            }
        });
    }

    private static void inputAttendance(Scanner scanner, HashMap<String, HashMap<String, String>> attendance, ArrayList<String> schedule) {
        while (true) {
            String input = scanner.nextLine();
            if (input.equals(".")) {
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 4) {
                System.err.println("ERROR: Неверный формат. Используйте <имя студента> <время> <дата> <статус (HERE/NOT_HERE)>.");
                continue;
            }

            String student = parts[0];
            String time = parts[1];
            String date = String.format("%02d", Integer.parseInt(parts[2]));
            String status = parts[3].toUpperCase();

            if (!attendance.containsKey(student)) {
                System.err.println("ERROR: Студент с именем " + student + " не найден.");
                continue;
            }
            if (!status.equals("HERE") && !status.equals("NOT_HERE")) {
                System.err.println("ERROR: Статус должен быть HERE или NOT_HERE.");
                continue;
            }

            if (!addAttendanceForLesson(attendance, schedule, student, time, date, status)) {
                System.out.println("INFO: Занятия нет в расписании");
            }
        }
    }

    private static boolean addAttendanceForLesson(HashMap<String, HashMap<String, String>> attendance, ArrayList<String> schedule, String student, String time, String date, String status) {
        for (String lesson : schedule) {
            String[] lessonParts = lesson.split(" ");
            if (lessonParts[0].equals(time + ":00") && lessonParts[2].equals(date)) {
                attendance.get(student).put(lesson, status);
                return true;
            }
        }
        return false;
    }

    private static void printAttendance(HashMap<String, HashMap<String, String>> attendance, ArrayList<String> schedule) {
        System.out.print("Студенты   |");
        for (String classKey : schedule) {
            System.out.print(" " + classKey + " |");
        }
        System.out.println();
        for (String student : attendance.keySet()) {
            System.out.printf("%-" + COLUMN_WIDTH + "s|", student);
            for (String classKey : schedule) {
                String status = attendance.get(student).getOrDefault(classKey, " ");
                if (!status.equals(" ")) status = status.equals("HERE") ? "1" : "-1";
                System.out.printf("%" + COLUMN_WIDTH + "s |", status);
            }
            System.out.println();
        }
    }

    private static HashMap<String, List<String>> generateSeptemberDates() {
        HashMap<String, List<String>> september2020 = new HashMap<>();
        september2020.put("MO", Arrays.asList("7", "14", "21", "28"));
        september2020.put("TU", Arrays.asList("1", "8", "15", "22", "29"));
        september2020.put("WE", Arrays.asList("2", "9", "16", "23", "30"));
        september2020.put("TH", Arrays.asList("3", "10", "17", "24"));
        september2020.put("FR", Arrays.asList("4", "11", "18", "25"));
        september2020.put("SA", Arrays.asList("5", "12", "19", "26"));
        september2020.put("SU", Arrays.asList("6", "13", "20", "27"));

        return september2020;
    }
}
