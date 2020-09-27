package be.dog.d.steven.soapexample.endpoint;

import be.dog.d.steven.soapexample.service.CountryRepository;
import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));

        if (countryRepository.findCountry(request.getName())==null){
            throw new RuntimeException("Error: This country is not currently in the db.");
        }

        return response;
    }

//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCountriesRequest")
//    @ResponsePayload
//    public GetAllCountriesResponse getAllCountriesResponse() {
//        GetAllCountriesResponse response = new GetAllCountriesResponse();
//
//        Map<String, Country> countries = countryRepository.findAllCountries();
//
//        for (Map.Entry<String,Country> entry : countries.entrySet()) {
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());
//            response.setMap(countryRepository.findAllCountries());
//        }
//        return response;
//    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCountryRequest")
    @ResponsePayload
    public DeleteCountryResponse deleteCountry(@RequestPayload DeleteCountryRequest request) {
        CountryRepository.Status status = countryRepository.deleteCountry(request.getName());

        DeleteCountryResponse response = new DeleteCountryResponse();
        response.setStatus(mapStatus(status));

        if (countryRepository.deleteCountry(request.getName())==null){
            throw new RuntimeException("Error: This country is not currently in the db.");
        }

        return response;
    }

    private Status mapStatus(CountryRepository.Status status) {
        if(status== CountryRepository.Status.FAILURE){
            return io.spring.guides.gs_producing_web_service.Status.FAILURE;
        }
        return io.spring.guides.gs_producing_web_service.Status.SUCCES;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCountryRequest")
    @ResponsePayload
    public AddCountryResponse addCountry(@RequestPayload AddCountryRequest request) {
        CountryRepository.Status status = countryRepository.addCountry(request.getCountry());

        countryRepository.addCountry(request.getCountry());

        AddCountryResponse response = new AddCountryResponse();
        response.setStatus(mapStatus(status));

        return response;
    }

}