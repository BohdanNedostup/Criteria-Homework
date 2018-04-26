package Homework;

import Homework.Entity.*;
import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App 
{
    public static void main( String[] args ) throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Database.showUsers(entityManager);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    static void fillRandomCountriesAndCities(EntityManager entityManager) throws IOException {
        BufferedReader countryReader = new BufferedReader(new FileReader("src/main/java/Homework/RandomData/countries.txt"));
        BufferedReader cityReader = new BufferedReader(new FileReader("src/main/java/Homework/RandomData/cities.txt"));

        String country;
        ArrayList<String> countries = new ArrayList<>();
        while ((country = countryReader.readLine()) != null){
            countries.add(country);
        }
        String city;
        ArrayList<String> cities = new ArrayList<>();
        while ((city = cityReader.readLine()) != null){
            cities.add(city);
        }
        countryReader.close();
        cityReader.close();

        List<Country> countryList = new ArrayList<>();
        countries.forEach(c -> {
            Country country1 = new Country();
            country1.setName(c);
            entityManager.persist(country1);
            countryList.add(country1);
        });

        List<City> cityList = new ArrayList<>();
        cities.forEach(c -> {
            City city1 = new City();
            city1.setName(c);
            entityManager.persist(city1);
            cityList.add(city1);
        });

        for (int i = 0, j = 0; i < cityList.size(); i+=3, j++){
            List<City> cityItems = new ArrayList<>();
            for (int t = i; t < i + 3 && t < cityList.size(); t++){
                City item = cityList.get(t);
                item.setCountry(countryList.get(j));
                cityItems.add(item);
            }
            countryList.get(j).setCities(cityItems);
        }

        countryList.forEach(c -> entityManager.persist(c));
        cityList.forEach(c -> entityManager.persist(c));

    }

    static void fillRandomUsers(EntityManager entityManager) throws IOException {
        BufferedReader userReader = new BufferedReader(new FileReader("src/main/java/Homework/RandomData/users.txt"));
        String user;
        ArrayList<String> users = new ArrayList<>();
        while ((user = userReader.readLine()) != null){
            users.add(user);
        }
        userReader.close();
        List<Country> countries = entityManager.createQuery("SELECT c FROM Country c", Country.class).getResultList();
        for (int i = 0; i < users.size(); i++){
            User u = new User();
            u.setFullName(users.get(i));
            u.setAge(15 + i);
            u.setCountry(countries.get(new Random().nextInt(20)));
            u.setCity(u.getCountry().getCities().get(new Random().nextInt(3)));
            entityManager.persist(u);
        }
    }



}
