package com.craft;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("beansConfig.xml")) {

            // 1. Setter injection + object and collection injection
            Student student = context.getBean("student", Student.class);
            System.out.println("=== Setter Injection ===");
            System.out.println(student);
            System.out.println("Address: " + student.getAddress());
            System.out.println("Subjects: " + student.getSubjects());

            // 2. Constructor injection
            Student constructorStudent =
                    context.getBean("studentConstructor", Student.class);
            System.out.println("\n=== Constructor Injection ===");
            System.out.println(constructorStudent);

            // 3. Singleton scope
            Student singleton1 = context.getBean("student", Student.class);
            Student singleton2 = context.getBean("student", Student.class);
            System.out.println("\n=== Singleton Scope ===");
            System.out.println(singleton1 == singleton2); // true

            // 4. Prototype scope
            Student prototype1 =
                    context.getBean("studentPrototype", Student.class);
            Student prototype2 =
                    context.getBean("studentPrototype", Student.class);
            System.out.println("\n=== Prototype Scope ===");
            System.out.println(prototype1 == prototype2); // false
        }
    }
}