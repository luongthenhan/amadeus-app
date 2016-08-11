package com.example.amadeus.app.controller;

import com.example.amadeus.app.entity.AgeRange;
import com.example.amadeus.app.entity.EntityList;
import com.example.amadeus.app.entity.Person;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A RESTful web service using Spring MVC.
 *
 * @author TheNhan
 */
@Controller
@RequestMapping("")
public class RestController {

    /**
     * A POST method for calculating the average age of people grouped by age
     * range.
     *
     * @param filePath a file path of a XML file. This is the input of web
     * service.
     * @param response the response of web service
     * @return a list of average ages of people grouped by age range in JSON
     * format.
     */
    @RequestMapping(value = "/average", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    List<AgeRange> calculateAverage(@RequestBody String filePath, final HttpServletResponse response) {
        EntityList<Person> peopleList = null;

        // Using JAXB for transforming an XML string to a list of Java object.
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EntityList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            peopleList = (EntityList<Person>) unmarshaller.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
        }

        if (peopleList != null) {
            // Using a HashMap with key is 0, 20, 40 and so on
            // value is an array of two integers:
            // the first integer is the number of age in the range, the second one is the sum of age in the range.
            Map<Integer, int[]> hm = new HashMap<>();
            for (Person p : peopleList.getItems()) {
                int age = p.getAge();
                int rangedAge = ((int) age / 20) * 20;
                if (hm.containsKey(rangedAge)) {
                    hm.get(rangedAge)[0]++;
                    hm.get(rangedAge)[1] += age;
                } else {
                    int[] val = new int[]{1, age};
                    hm.put(rangedAge, val);
                }
            }

            // Building the result
            List<AgeRange> res = new ArrayList<>();
            int i = 0;
            while (!hm.isEmpty()) {
                if (hm.containsKey(i)) {
                    res.add(new AgeRange(i + "-" + (i + 19), (float) hm.get(i)[1] / hm.get(i)[0]));
                    hm.remove(i);
                } else {
                    res.add(new AgeRange(i + "-" + (i + 19), 0f));
                }
                i += 20;
            }

            // return 200
            response.setStatus(HttpServletResponse.SC_OK);
            return res;
        } else {
            // result can not found, return 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

    }

}
