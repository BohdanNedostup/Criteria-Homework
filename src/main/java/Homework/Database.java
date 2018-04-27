package Homework;

import Homework.Entity.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.Arrays;

public class Database {

    static void showUsers(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT u FROM User u", User.class)
//                .getResultList().forEach(System.out::println);

        //criteria
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        entityManager.createQuery(query).getResultList().forEach(System.out::println);
    }

    static void showCountriesInReverseOrder(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT c FROM Country c ORDER BY c.name DESC", Country.class)
//                .getResultList().forEach(System.out::println);

        //criteria
        CriteriaQuery<Country> query = criteriaBuilder.createQuery(Country.class);
        Root<Country> root = query.from(Country.class);
        query.select(root);

        Expression<String> expression = root.get("name");
        query.orderBy(criteriaBuilder.desc(expression));
        entityManager.createQuery(query).getResultList().forEach(System.out::println);
    }

    static void showCities(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT c FROM City c ORDER BY c.name", City.class)
//                .getResultList().forEach(System.out::println);

        //criteria
        CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);
        Root<City> root = criteriaQuery.from(City.class);
        criteriaQuery.select(root);

        Expression<String> expression = root.get("name");
        criteriaQuery.orderBy(criteriaBuilder.asc(expression));
        entityManager.createQuery(criteriaQuery).getResultList().forEach(System.out::println);
    }

    static void showUsersInReverseOrder(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT u FROM User u ORDER BY u.fullName DESC", User.class)
//                .getResultList().forEach(System.out::println);

        //criteria
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        Expression<String> expression = root.get("fullName");
        criteriaQuery.orderBy(criteriaBuilder.desc(expression));
        entityManager.createQuery(criteriaQuery).getResultList().forEach(System.out::println);

    }

    static void showCountriesWithFirstA(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT c FROM Country c WHERE c.name LIKE 'A%' OR c.name LIKE 'a%'", Country.class)
//                .getResultList().forEach(System.out::println);

        //criteria
        CriteriaQuery<Country> countryCriteriaQuery = criteriaBuilder.createQuery(Country.class);
        Root<Country> root = countryCriteriaQuery.from(Country.class);
        countryCriteriaQuery.select(root);

        Expression<String> expression = root.get("name");
        Predicate predicateFirst = criteriaBuilder.like(expression, "a%");
        Predicate predicateSecond = criteriaBuilder.like(expression, "A%");
        Predicate predicate = criteriaBuilder.or(predicateFirst, predicateSecond);
        countryCriteriaQuery.where(predicate);
        entityManager.createQuery(countryCriteriaQuery).getResultList().forEach(System.out::println);
    }

    static void showCitiesWithPrelastNOrR(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT c FROM City c WHERE c.name LIKE '%n_' OR c.name LIKE '%r_'", City.class)
//                .getResultList().forEach(System.out::println);

        //criteria
        CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);
        Root<City> root = criteriaQuery.from(City.class);
        criteriaQuery.select(root);

        Expression<String> expression = root.get("name");
        Predicate predicateFirst = criteriaBuilder.like(expression, "%n_");
        Predicate predicateSecond = criteriaBuilder.like(expression, "%r_");
        Predicate predicate = criteriaBuilder.or(predicateFirst, predicateSecond);
        criteriaQuery.where(predicate);
        entityManager.createQuery(criteriaQuery).getResultList().forEach(System.out::println);

    }

    static void showUsersWithMinAge(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT u FROM User u WHERE u.age = ?1", User.class)
//                .setParameter(1, entityManager.createQuery("SELECT MIN(u.age) FROM User u").getSingleResult())
//                .getResultList().forEach(System.out::println);

        //criteria
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Root<User> userRoot = userCriteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.min(root.get("age")));
        userCriteriaQuery.select(userRoot);
        int min = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.min(root.get("age")))).getSingleResult();
        Expression<Integer> expression = userRoot.get("age");
        Predicate predicate = criteriaBuilder.equal(expression, min);
        userCriteriaQuery.where(predicate);
        entityManager.createQuery(userCriteriaQuery).getResultList().forEach(System.out::println);

    }

    static void averageUsersAge(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        System.out.println(entityManager.createQuery("SELECT AVG(u.age) FROM User u").getSingleResult());

        //criteria
        CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.avg(root.get("age")));
        System.out.println(entityManager.createQuery(criteriaQuery).getSingleResult());
    }

    static void showUserAndUserCity(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.city", User.class).getResultList().forEach(u -> {
//            System.out.println(u + " " + u.getCity().toString());
//        });

        //criteria
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Join<User, City> userCityJoin = root.join("city");
        root.fetch("city");
        entityManager.createQuery(criteriaQuery).getResultList().forEach(user -> {
            System.out.println(user);
            System.out.println(user.getCity());
        });
    }

    static void showUserAndUserCityWhereUserIdNotIn(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.city WHERE u.id NOT IN (2, 5, 9, 12 , 13, 16)", User.class)
//                .getResultList().forEach(u -> System.out.println(u + " " + u.getCity().toString()));

        //criteria
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Join<User, City> userCityJoin = root.join("city");
        root.fetch("city");
        Expression<Integer> expression = root.get("id");
        Predicate predicate = expression.in(Arrays.asList(2, 5, 9, 12, 13, 16));
        Predicate predicate1 = criteriaBuilder.not(predicate);
        criteriaQuery.where(predicate1);
        entityManager.createQuery(criteriaQuery).getResultList().forEach(user -> {
            System.out.println(user);
            System.out.println(user.getCity());
        });

    }

    static void showAllUsersData(CriteriaBuilder criteriaBuilder, EntityManager entityManager){

        //jpql
//        entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.city JOIN FETCH u.country", User.class)
//                .getResultList().forEach(u -> System.out.println(u + " " + u.getCity() + " " + u.getCountry()));

        //criteria
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Join<User, City> userCityJoin = root.join("city").join("country");
        root.fetch("city").fetch("country");

        entityManager.createQuery(criteriaQuery).getResultList().forEach(user -> {
            System.out.println(user);
            System.out.println(user.getCity());
            System.out.println(user.getCity().getCountry());
        });
    }

}
