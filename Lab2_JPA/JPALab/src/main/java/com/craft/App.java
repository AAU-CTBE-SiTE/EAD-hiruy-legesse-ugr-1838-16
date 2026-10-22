package com.craft;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class App {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studentPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // ==========================================
    // Modular CRUD Operations (EntityManager API)
    // ==========================================

    public static void createStudent(Student student) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(student);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static Student findStudentById(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(Student.class, id);
        } finally {
            entityManager.close();
        }
    }

    public static void updateStudentName(String newName, int id) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                student.setName(newName);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void updateStudentNameAndTech(String newName, String newTech, int id) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                student.setName(newName);
                student.setTech(newTech);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void removeStudentById(int id) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                entityManager.remove(student);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    // ==========================================
    // 30 JPQL Queries
    // ==========================================

    // 1. Select all students
    public static List<Student> getAllStudents() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 2. Select student by ID
    public static Student getStudentById(int id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.id = :id", Student.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    // 3. Select students by name (exact match)
    public static List<Student> getStudentsByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.name = :name", Student.class);
            query.setParameter("name", name);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 4. Select students by name (case-insensitive)
    public static List<Student> getStudentsByNameCaseInsensitive(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE LOWER(s.name) = LOWER(:name)",
                    Student.class);
            query.setParameter("name", name);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 5. Select students by technology
    public static List<Student> getStudentsByTech(String tech) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.tech = :tech", Student.class);
            query.setParameter("tech", tech);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 6. Select students with names starting with a letter
    public static List<Student> getStudentsByNameStartingWith(String letter) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.name LIKE :pattern",
                    Student.class);
            query.setParameter("pattern", letter + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 7. Select students with names containing a substring
    public static List<Student> getStudentsByNameContaining(String substring) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.name LIKE :pattern",
                    Student.class);
            query.setParameter("pattern", "%" + substring + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 8. Select students with ID greater than a value
    public static List<Student> getStudentsWithIdGreaterThan(int minId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.id > :minId", Student.class);
            query.setParameter("minId", minId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 9. Select students with ID in a range
    public static List<Student> getStudentsWithIdBetween(int startId, int endId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.id BETWEEN :startId AND :endId",
                    Student.class);
            query.setParameter("startId", startId);
            query.setParameter("endId", endId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 10. Select students with specific IDs (IN clause)
    public static List<Student> getStudentsByIdList(List<Integer> idList) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.id IN :idList", Student.class);
            query.setParameter("idList", idList);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 11. Select all students ordered by name (ascending)
    public static List<Student> getAllStudentsOrderByNameAsc() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s ORDER BY s.name ASC", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 12. Select all students ordered by name (descending)
    public static List<Student> getAllStudentsOrderByNameDesc() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s ORDER BY s.name DESC", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 13. Select first N students (pagination)
    public static List<Student> getFirstNStudents(int n) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s ORDER BY s.id ASC", Student.class);
            query.setMaxResults(n);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 14. Select students with pagination (offset)
    public static List<Student> getStudentsWithPagination(int offset, int limit) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s ORDER BY s.id ASC", Student.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 15. Count all students
    public static long countAllStudents() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(s) FROM Student s");
            return (long) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    // 16. Count students by technology
    @SuppressWarnings("unchecked")
    public static List<Object[]> countStudentsByTech() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT s.tech, COUNT(s) FROM Student s GROUP BY s.tech");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 17. Find distinct technologies
    public static List<String> getDistinctTechs() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<String> query = em.createQuery("SELECT DISTINCT s.tech FROM Student s", String.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 18. Find average ID
    public static double getAverageId() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT AVG(s.id) FROM Student s");
            Object result = query.getSingleResult();
            return result != null ? ((Number) result).doubleValue() : 0.0;
        } finally {
            em.close();
        }
    }

    // 19. Find maximum ID
    public static int getMaxId() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT MAX(s.id) FROM Student s");
            Object result = query.getSingleResult();
            return result != null ? ((Number) result).intValue() : 0;
        } finally {
            em.close();
        }
    }

    // 20. Find students with the longest names
    public static List<Student> getStudentsWithLongestNames() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE LENGTH(s.name) = " +
                            "(SELECT MAX(LENGTH(s2.name)) FROM Student s2)", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 21. Select students with a specific technology and name pattern
    public static List<Student> getStudentsByTechAndNamePattern(String tech, String pattern) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.tech = :tech AND s.name LIKE :pattern", Student.class);
            query.setParameter("tech", tech);
            query.setParameter("pattern", "%" + pattern + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 22. Select students with NULL technology
    public static List<Student> getStudentsWithNullTech() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.tech IS NULL", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 23. Select students with non-NULL technology
    public static List<Student> getStudentsWithNonNullTech() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.tech IS NOT NULL", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 24. Update student's technology
    public static int updateStudentTech(int id, String newTech) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query query = em.createQuery("UPDATE Student s SET s.tech = :newTech WHERE s.id = :id");
            query.setParameter("newTech", newTech);
            query.setParameter("id", id);
            int updatedCount = query.executeUpdate();
            tx.commit();
            return updatedCount;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // 25. Update all students' technology by pattern
    public static int updateTechByPattern(String oldPattern, String newTech) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query query = em.createQuery(
                    "UPDATE Student s SET s.tech = :newTech WHERE s.tech LIKE :oldPattern");
            query.setParameter("newTech", newTech);
            query.setParameter("oldPattern", "%" + oldPattern + "%");
            int updatedCount = query.executeUpdate();
            tx.commit();
            return updatedCount;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // 26. Delete student by ID
    public static int deleteStudentById(int id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query query = em.createQuery("DELETE FROM Student s WHERE s.id = :id");
            query.setParameter("id", id);
            int deletedCount = query.executeUpdate();
            tx.commit();
            return deletedCount;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // 27. Delete students by technology
    public static int deleteStudentsByTech(String tech) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query query = em.createQuery("DELETE FROM Student s WHERE s.tech = :tech");
            query.setParameter("tech", tech);
            int deletedCount = query.executeUpdate();
            tx.commit();
            return deletedCount;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // 28. Select students with the most common technology
    public static List<Student> getStudentsWithMostCommonTech() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.tech = (" +
                            "SELECT s2.tech FROM Student s2 GROUP BY s2.tech " +
                            "ORDER BY COUNT(s2) DESC)", Student.class);
            query.setMaxResults(1); // Get just the most common one
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 29. Select students with unique technologies
    public static List<Student> getStudentsWithUniqueTechs() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.tech IN (" +
                            "SELECT s2.tech FROM Student s2 GROUP BY s2.tech HAVING COUNT(s2) = 1)", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 30. Select students with name length greater than average
    public static List<Student> getStudentsWithLongerThanAverageNames() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE LENGTH(s.name) > " +
                            "(SELECT AVG(LENGTH(s2.name)) FROM Student s2)", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ==========================================
    // Main Method Demo
    // ==========================================
    public static void main(String[] args) {
        try {
            // Example usage:
            List<Student> allStudents = getAllStudents();
            System.out.println("All students count: " + allStudents.size());

            List<Student> javaStudents = getStudentsByTech("Java");
            System.out.println("Java students: " + javaStudents.size());

            int updated = updateStudentTech(1, "Python");
            System.out.println("Updated " + updated + " student(s)");
        } catch (Exception e) {
            System.err.println("Database operation failed: " + e.getMessage());
        } finally {
            // Close the EntityManagerFactory when done
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}