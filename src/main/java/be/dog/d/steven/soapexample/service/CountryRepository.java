package be.dog.d.steven.soapexample.service;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CountryRepository {

    private static final Map<String, Country> countries = new HashMap<>();

    public enum CountryDeleteStatus {
        SUCCESS, FAILURE;
    }

    @PostConstruct
    public void init() {

        Country bel = new Country();
        bel.setName("Belgium");
        bel.setCapital("Brussels");
        bel.setCurrency(Currency.EUR);
        bel.setPopulation(11460000);

        countries.put(bel.getName(), bel);

        Country uk = new Country();
        uk.setName("United Kingdom");
        uk.setCapital("London");
        uk.setCurrency(Currency.GBP);
        uk.setPopulation(55980000);

        countries.put(uk.getName(), uk);

        Country fr = new Country();
        fr.setName("France");
        fr.setCapital("Paris");
        fr.setCurrency(Currency.EUR);
        fr.setPopulation(66990000);

        countries.put(fr.getName(), fr);

        Country spain = new Country();
        spain.setName("Spain");
        spain.setCapital("Madrid");
        spain.setCurrency(Currency.EUR);
        spain.setPopulation(46704314);

        countries.put(spain.getName(), spain);

        Country poland = new Country();
        poland.setName("Poland");
        poland.setCapital("Warsaw");
        poland.setCurrency(Currency.PLN);
        poland.setPopulation(38186860);

        countries.put(poland.getName(), poland);
    }

    public Country findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }

    public Map<String, Country> findAllCountries() {
        return countries;
    }

    public CountryDeleteStatus deleteCountry(String name){
        Assert.notNull(name, "The country's name must not be null");
        if (countries.containsKey(name)){
            countries.remove(name);
            return CountryDeleteStatus.SUCCESS;
        }
        return CountryDeleteStatus.FAILURE;
    }
}