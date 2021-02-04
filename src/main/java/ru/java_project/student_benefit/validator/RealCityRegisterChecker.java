package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.config.Config;
import ru.java_project.student_benefit.domain.register.CityRegisterRequest;
import ru.java_project.student_benefit.domain.register.CityRegisterResponse;
import ru.java_project.student_benefit.domain.person.Person;
import ru.java_project.student_benefit.exception.CityRegisterException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class RealCityRegisterChecker implements CityRegisterChecker {
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException {
        try {
            CityRegisterRequest request = new CityRegisterRequest(person);
            Client client = ClientBuilder.newClient();
            CityRegisterResponse response = client.target(Config.getProperty(Config.CR_URL))
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON))
                    .readEntity(CityRegisterResponse.class);
            return response;
        } catch (Exception e) {
            throw new CityRegisterException("1", e.getMessage(), e);
        }
    }
}
