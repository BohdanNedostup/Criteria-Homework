package Homework;

import Homework.Entity.*;
import javax.persistence.*;

public class Database {

    static void showUsers(EntityManager entityManager){

        entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList().forEach(System.out::println);
    }

    static void showCountriesInReverseOrder(EntityManager entityManager){

        entityManager.createQuery("SELECT c FROM Country c ORDER BY c.name DESC", Country.class)
                .getResultList().forEach(System.out::println);
    }

    static void showCities(EntityManager entityManager){

        entityManager.createQuery("SELECT c FROM City c ORDER BY c.name", City.class)
                .getResultList().forEach(System.out::println);
    }

    static void showUsersInReverseOrder(EntityManager entityManager){

        entityManager.createQuery("SELECT u FROM User u ORDER BY u.fullName DESC", User.class)
                .getResultList().forEach(System.out::println);

    }

    static void showCountriesWithFirstA(EntityManager entityManager){

        entityManager.createQuery("SELECT c FROM Country c WHERE c.name LIKE 'A%' OR c.name LIKE 'a%'", Country.class)
                .getResultList().forEach(System.out::println);
    }

    static void showCitiesWithPrelastNOrR(EntityManager entityManager){

        entityManager.createQuery("SELECT c FROM City c WHERE c.name LIKE '%n_' OR c.name LIKE '%r_'", City.class)
                .getResultList().forEach(System.out::println);
    }

    static void showUsersWithMinAge(EntityManager entityManager){
        entityManager.createQuery("SELECT u FROM User u WHERE u.age = ?1", User.class)
                .setParameter(1, entityManager.createQuery("SELECT MIN(u.age) FROM User u").getSingleResult())
                .getResultList().forEach(System.out::println);
    }

    static void averageUsersAge(EntityManager entityManager){
        System.out.println(entityManager.createQuery("SELECT AVG(u.age) FROM User u").getSingleResult());
    }

    static void showUserAndUserCity(EntityManager entityManager){
        entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.city", User.class).getResultList().forEach(u -> {
            System.out.println(u + " " + u.getCity().toString());
        });
    }

    static void showUserAndUserCityWhereUserIdNotIn(EntityManager entityManager){
        entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.city WHERE u.id NOT IN (2, 5, 9, 12 , 13, 16)", User.class)
                .getResultList().forEach(u -> System.out.println(u + " " + u.getCity().toString()));
    }

    static void showAllUsersData(EntityManager entityManager){
        entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.city JOIN FETCH u.country", User.class)
                .getResultList().forEach(u -> System.out.println(u + " " + u.getCity() + " " + u.getCountry()));
    }


}
