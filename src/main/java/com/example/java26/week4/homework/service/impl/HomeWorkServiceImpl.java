package com.example.java26.week4.homework.service.impl;

import com.example.java26.week3.rest.homework.pojo.entity.Student;
import com.example.java26.week3.rest.homework.repository.StudentRepository;
import com.example.java26.week4.homework.config.RestTemplateConfig;
import com.example.java26.week4.homework.service.HomeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO.StudentDTO;

@Service
public class HomeWorkServiceImpl implements HomeWorkService {

    private final RestTemplate restTemplate;


    private final String url;

    @Autowired
    public HomeWorkServiceImpl(RestTemplate restTemplate, RestTemplateConfig restTemplateConfig) {
        this.restTemplate = restTemplate;
        this.url = restTemplateConfig.getUrl();
    }

//    @Override
//    public StudentDTO getById(int id) {
//        Student student = studentRepository.getById(id);
//        return new StudentDTO(student);
//    }
    @Override
    public Map findAllGroupByAge(){

        Map response = restTemplate.getForObject(url, Map.class);

        List<Map> data = (List<Map>) response.get("data");

        System.out.println(data);

        Map<Integer, List<Map>> res = new HashMap<>();

        for(Map i : data){
            int id = (int) i.get("id");
            if(res.containsKey(id)){
                res.get(id).add(i);
            }else{
                res.put(id, new ArrayList<>(Arrays.asList(i)));
            }
        }

        return new TreeMap<>(res);
    }

    @Override
    public Map getCondition(int age){

        Map response = restTemplate.getForObject(url, Map.class);

        List<Map> data = (List<Map>) response.get("data");

        Iterator<Map> itr = data.iterator();

        while(itr.hasNext()){
            if((int)itr.next().get("id") < age){
                itr.remove();
            }
        }

        Map<String, List<Map>> res = new HashMap<>();

        res.put("data", data);

        return res;
    }


}
