import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Classes:");
        System.out.println("  - User");
        System.out.println("  - Car");
        System.out.println("---------------------");
        System.out.print("Enter class name:\n-> ");
        String className = sc.nextLine();
        Class<?> clazz = Class.forName("classes." + className);

        printClassInfo(clazz);

        Object object = createInstance(clazz, sc);
        System.out.println("Object created: " + object);

        updateObject(object, clazz, sc);
        System.out.println("Object updated: " + object);

        invokeMethod(object, clazz, sc);
        sc.close();
    }

    private static void printClassInfo(Class<?> clazz) {
        System.out.println("---------------------");
        System.out.println("Fields:");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.printf("  %s %s\n", field.getType().getSimpleName(), field.getName());
        }
        System.out.println("Methods:");
        Set<String> excludedMethods = Set.of("toString");
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getDeclaringClass() == Object.class || excludedMethods.contains(method.getName())) continue;
            System.out.printf("  %s %s(", method.getReturnType().getSimpleName(), method.getName());
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                System.out.print(parameters[i].getType().getSimpleName());
                if (i < parameters.length - 1) System.out.print(", ");
            }
            System.out.println(")");
        }
    }

    private static Object createInstance(Class<?> clazz, Scanner sc) throws Exception {
        System.out.println("---------------------");
        System.out.println("Let’s create an object.");
        Object object = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            System.out.print(field.getName() + ":\n-> ");
            String value = sc.nextLine();
            Object convertedValue = convertValue(value, field.getType());
            field.set(object, convertedValue);
        }
        return object;
    }

    private static void updateObject(Object object, Class<?> clazz, Scanner sc) throws Exception {
        System.out.println("---------------------");
        System.out.print("Enter name of the field for changing:\n-> ");
        String name = sc.nextLine();
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        System.out.print("Enter " + field.getType().getSimpleName() + " value:\n-> ");
        String value = sc.nextLine();
        Object convertedValue = convertValue(value, field.getType());
        field.set(object, convertedValue);
    }

    private static void invokeMethod(Object object, Class<?> clazz, Scanner sc) throws Exception {
        System.out.println("---------------------");
        System.out.print("Enter name of the method for call:\n-> ");
        String methodName = sc.nextLine().trim();

        List<Method> candidates = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                candidates.add(method);
            }
        }
        if (candidates.isEmpty()) {
            System.out.println("Method not found!");
            return;
        }
        Method method = null;
        if (candidates.size() == 1) {
            method = candidates.get(0);
        } else {
            System.out.println("Overloaded methods:");
            for (int i = 0; i < candidates.size(); i++) {
                Class<?>[] params = candidates.get(i).getParameterTypes();
                System.out.printf("%d. %s(%s)%n",
                        i + 1,
                        methodName,
                        Arrays.stream(params)
                                .map(Class::getSimpleName)
                                .collect(Collectors.joining(", ")));
            }
            System.out.print("Select method (1-" + candidates.size() + "):\n-> ");
            int choice = Integer.parseInt(sc.nextLine()) - 1;
            method = candidates.get(choice);
        }
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            System.out.print("Enter " + paramTypes[i].getSimpleName() + " value:\n-> ");
            String value = sc.nextLine();
            params[i] = convertValue(value, paramTypes[i]);
        }
        Object result = method.invoke(object, params);
        if (method.getReturnType() != void.class) {
            System.out.println("Method returned:\n" + result);
        }
    }

    private static Object convertValue(String value, Class<?> type) {
        return switch (type.getTypeName()) {
            case "java.lang.String" -> value;
            case "boolean", "java.lang.Boolean" -> Boolean.parseBoolean(value);
            case "int", "java.lang.Integer" -> Integer.parseInt(value);
            case "long", "java.lang.Long" -> Long.parseLong(value);
            case "double", "java.lang.Double" -> Double.parseDouble(value);
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }
}
